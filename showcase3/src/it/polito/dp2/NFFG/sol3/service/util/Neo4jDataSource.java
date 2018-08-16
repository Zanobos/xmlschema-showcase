/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.service.util;

import com.sun.jersey.api.client.ClientResponse;
import it.polito.dp2.NFFG.sol3.service.entities.FuncType;
import it.polito.dp2.NFFG.sol3.service.entities.LinkType;
import it.polito.dp2.NFFG.sol3.service.entities.NffgType;
import it.polito.dp2.NFFG.sol3.service.entities.NffgsType;
import it.polito.dp2.NFFG.sol3.service.entities.NodeType;
import it.polito.dp2.NFFG.sol3.service.entities.ReachibilityPolicyType;
import it.polito.dp2.NFFG.sol3.service.entities.ReferenceByIDType;
import it.polito.dp2.NFFG.sol3.service.entities.TraversalPolicyType;
import it.polito.dp2.NFFG.sol3.service.entities.VerificationResultType;
import it.polito.dp2.NFFG.sol3.service.exception.AlreadyLoadedException;
import it.polito.dp2.NFFG.sol3.service.exception.ServiceException;
import it.polito.dp2.NFFG.sol3.service.exception.UnknownNameException;
import it.polito.dp2.NFFG.sol3.service.neo4j.Labels;
import it.polito.dp2.NFFG.sol3.service.neo4j.Localhost_Neo4JXMLRest;
import it.polito.dp2.NFFG.sol3.service.neo4j.Node;
import it.polito.dp2.NFFG.sol3.service.neo4j.Path;
import it.polito.dp2.NFFG.sol3.service.neo4j.Paths;
import it.polito.dp2.NFFG.sol3.service.neo4j.Property;
import it.polito.dp2.NFFG.sol3.service.neo4j.Relationship;
import java.net.URI;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author a.zanotti
 */
public class Neo4jDataSource implements NffgDataSource {

    private static Neo4jDataSource instance;

    public static Neo4jDataSource getInstance(URI baseUri) {
        if (instance == null) {
            instance = new Neo4jDataSource(baseUri);
        }
        return instance;
    }

    private static final String LINK_TYPE = "Link";
    private static final String BELONGS_TYPE = "Belongs";

    private final it.polito.dp2.NFFG.sol3.service.entities.ObjectFactory serviceFactory;
    private final it.polito.dp2.NFFG.sol3.service.neo4j.ObjectFactory neo4jFactory;

    //I use a composite map to keep track of the IDs
    //          NFFG       NODE     ID
    private Map<String, Map<String, String>> nffgNodeMap;
    //Same for the relationships
    //          NFFG       LINK         ID
    private Map<String, Map<String, String>> nffgRelationshipMap;
    //I use a NffgsType type to store data locally
    private NffgsType nffgsDataSource;
    private Localhost_Neo4JXMLRest.Resource resource;

    private Neo4jDataSource(URI baseUri) {
        this.serviceFactory = new it.polito.dp2.NFFG.sol3.service.entities.ObjectFactory();
        this.neo4jFactory = new it.polito.dp2.NFFG.sol3.service.neo4j.ObjectFactory();
        this.nffgNodeMap = new ConcurrentHashMap<String, Map<String, String>>();
        this.nffgRelationshipMap = new ConcurrentHashMap<String, Map<String, String>>();
        this.nffgsDataSource = serviceFactory.createNffgsType();
        this.nffgsDataSource.getNffg(); // To initialize the inner list
        this.resource = Localhost_Neo4JXMLRest.resource(Localhost_Neo4JXMLRest.createClient(), baseUri);
        //At startup, I clear all the nodes
        resource.nodes().deleteAsXml(ClientResponse.class);
    }

    public NffgsType load() {
        NffgsType nffgs = this.nffgsDataSource;
        return nffgs;
    }

    public String save(NffgType nffg) throws AlreadyLoadedException, ServiceException {
        String nffgName = null;

        //First, local operations
        //Only save operations, no update of old informations
        if (exist(nffg.getName())) {
            throw new AlreadyLoadedException("Already existing a Nffg with " + nffgName + " as name");
        }
        XMLGregorianCalendar actualTime = null;
        try {
            actualTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
        } catch (DatatypeConfigurationException ex) {
            System.err.println("Error in converting the actual time");
        }
        nffg.setUpdateTime(actualTime);

        //Inserting the nffg
        nffgsDataSource.getNffg().add(nffg);
        nffgName = nffg.getName();
        try {
            //Then, neo4j operations
            //Inserting the nodes
            Map<String, String> nodeMap = new ConcurrentHashMap<String, String>();
            if (nffg.getNode() != null) {
                for (NodeType nodeType : nffg.getNode()) {
                    //I create the node
                    Node node = neo4jFactory.createNode();
                    Property nameProperty = neo4jFactory.createProperty();
                    nameProperty.setName("name");
                    nameProperty.setValue(nodeType.getName());
                    node.getProperty().add(nameProperty);
                    // I upload the node
                    Node nodePosted = resource.node().postXmlAsNode(node);
                    //Adding the label
                    Labels labels = neo4jFactory.createLabels();
                    labels.getValue().add(nodeType.getFunctionality().getFunc());
                    resource.nodeNodeidLabel(nodePosted.getId()).postXml(labels, ClientResponse.class);
                    nodeMap.put(nodeType.getName(), nodePosted.getId());
                }
            }
            //Saving what I have done
            nffgNodeMap.put(nffgName, nodeMap);

            //Inserting the links
            Map<String, String> relationshipMap = new ConcurrentHashMap<String, String>();
            if (nffg.getLink() != null) {
                for (LinkType linkType : nffg.getLink()) {
                    String sourceNode = nodeMap.get(linkType.getSourceNode());
                    String destinationNode = nodeMap.get(linkType.getDestinationNode());
                    Relationship relationship = neo4jFactory.createRelationship();
                    relationship.setDstNode(destinationNode);
                    relationship.setType(LINK_TYPE);
                    Relationship relationshipPosted = resource.nodeNodeidRelationship(sourceNode).postXmlAsRelationship(relationship);
                    relationshipMap.put(linkType.getName(), relationshipPosted.getId());
                }
            }
            //Saving the links
            nffgRelationshipMap.put(nffgName, relationshipMap);

            //Adding the node representing the nffg
            //I create the node
            Node nffgNode = neo4jFactory.createNode();
            Property property = neo4jFactory.createProperty();
            property.setName("name");
            property.setValue(nffgName);
            nffgNode.getProperty().add(property);
            // I upload the node
            Node nodePosted = resource.node().postXmlAsNode(nffgNode);
            //Adding the label
            Labels labels = neo4jFactory.createLabels();
            labels.getValue().add("NFFG");
            resource.nodeNodeidLabel(nodePosted.getId()).postXml(labels, ClientResponse.class);
            //I insert the nffg node in the map
            nodeMap.put(nffgName, nodePosted.getId());
            //I connect this nffg node to all the nodes
            for (String idNode : nodeMap.values()) {
                Relationship relationship = neo4jFactory.createRelationship();
                relationship.setDstNode(idNode);
                relationship.setType(BELONGS_TYPE);
                Relationship relationshipPosted = resource.nodeNodeidRelationship(nodePosted.getId()).postXmlAsRelationship(relationship);
                relationshipMap.put(idNode, relationshipPosted.getId());
            }
        } catch (Exception e) {
            //In case the upload fails, I revert what I have done locally
            nffgNodeMap.remove(nffgName);
            nffgRelationshipMap.remove(nffgName);
            nffgsDataSource.getNffg().remove(nffg);
            throw new ServiceException(e.getMessage());
        }

        return nffgName;
    }

    public ReferenceByIDType save(NffgsType nffgs) throws AlreadyLoadedException, ServiceException {
        ReferenceByIDType referenceSaved = serviceFactory.createReferenceByIDType();
        for (NffgType nffg : nffgs.getNffg()) {
            String nffgSaved = save(nffg);
            referenceSaved.getReferenceID().add(nffgSaved);
        }
        return referenceSaved;
    }

    public NffgType load(String nffgName) throws UnknownNameException {
        if (!exist(nffgName)) {
            throw new UnknownNameException("Not found a Nffg with " + nffgName + " as name");
        }
        return getNffg(nffgName);
    }

    public VerificationResultType checkTraversal(TraversalPolicyType traversalPolicy) throws UnknownNameException, ServiceException {

        boolean traversability = false;
        Paths paths = getPaths(traversalPolicy.getNffg(), traversalPolicy.getSourceNode(), traversalPolicy.getDestinationNode());

        if (paths.getPath() != null && paths.getPath().size() > 0) {
            //If it exists at least one path satisfying all the functionalities, the policy is satisfied
            for (Path path : paths.getPath()) {
                List<String> functionalitiesFound = new ArrayList<String>();
                for (String nodeId : path.getNode()) {
                    // I get the labels of the nodes of that path
                    Labels labels = resource.nodeNodeidLabel(nodeId).getAsLabels();
                    for (String label : labels.getValue()) {
                        functionalitiesFound.add(label);
                    }
                }
                //When I have all the functionalities between source and dest, I check if I satisfy the condition
                boolean pathSatisfyPolicy = true;
                for (FuncType functionality : traversalPolicy.getFunctionality()) {
                    if (!functionalitiesFound.contains(functionality.getFunc())) {
                        pathSatisfyPolicy = false;
                    }
                }
                if (pathSatisfyPolicy) {
                    traversability = true;
                }
            }
        }

        VerificationResultType result = createVerificationResult(traversability, traversalPolicy.isPositive(), traversalPolicy.getName());

        return result;

    }

    public VerificationResultType checkReachability(ReachibilityPolicyType reachabilityPolicy) throws UnknownNameException, ServiceException {

        boolean reachibility = false;
        Paths paths = getPaths(reachabilityPolicy.getNffg(), reachabilityPolicy.getSourceNode(), reachabilityPolicy.getDestinationNode());
        if (paths.getPath() != null && paths.getPath().size() > 0) {
            reachibility = true;
        }

        VerificationResultType result = createVerificationResult(reachibility, reachabilityPolicy.isPositive(), reachabilityPolicy.getName());

        return result;
    }

    public boolean exist(String nffgName) {
        return (getNffg(nffgName) != null);
    }

    public String delete(String nffgName) throws UnknownNameException, ServiceException {
        String nffgDeleted = null;
        if (!exist(nffgName)) {
            throw new UnknownNameException("Not found a Nffg with " + nffgName + " as name");
        }

        NffgType nffg = getNffg(nffgName);

        //First remote operations
        try {
            //I get all the nodes and relationships
            Map<String, String> nodeMapToDelete = nffgNodeMap.get(nffgName);
            Map<String, String> relationshipMapToDelete = nffgRelationshipMap.get(nffgName);
            if (relationshipMapToDelete != null && relationshipMapToDelete.size() > 0) {
                for (String relationship : relationshipMapToDelete.values()) {
                    resource.relationshipRelationshipid(relationship).deleteAsXml(ClientResponse.class);
                }
            }
            if (nodeMapToDelete != null && nodeMapToDelete.size() > 0) {
                for (String node : nodeMapToDelete.values()) {
                    resource.nodeNodeid(node).deleteAsXml(ClientResponse.class);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

        //If remote operations went well, I remove also locally
        boolean remove = nffgsDataSource.getNffg().remove(nffg);
        nffgNodeMap.remove(nffgName);
        nffgRelationshipMap.remove(nffgName);
        if (remove) {
            nffgDeleted = nffg.getName();
        }

        return nffgDeleted;
    }

    private NffgType getNffg(String nffgName) {
        NffgType nffg = null;
        for (NffgType nffgType : nffgsDataSource.getNffg()) {
            if (nffgType.getName().equalsIgnoreCase(nffgName)) {
                nffg = nffgType;
            }
        }
        return nffg;
    }

    private Paths getPaths(String nffgName, String sourceNode, String destinationNode) throws UnknownNameException, ServiceException {

        Paths paths = null;

        Map<String, String> nodeMap = nffgNodeMap.get(nffgName);
        if (nodeMap == null) {
            throw new UnknownNameException("Not found a Nffg with " + nffgName + " as name");
        }

        String srcid = nodeMap.get(sourceNode);
        String destid = nodeMap.get(destinationNode);
        if (srcid == null) {
            throw new UnknownNameException(sourceNode + " not found in the nffg " + nffgName);
        }
        if (destid == null) {
            throw new UnknownNameException(destinationNode + " not found in the nffg " + nffgName);
        }
        try {
            paths = resource.nodeNodeidPaths(srcid).getAsPaths(destid);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

        return paths;
    }

    private VerificationResultType createVerificationResult(boolean policySatisfied, boolean positive, String policyName) {
        VerificationResultType result = serviceFactory.createVerificationResultType();
        //Setting the time
        XMLGregorianCalendar verificationTime = null;
        try {
            verificationTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
        } catch (DatatypeConfigurationException ex) {
            System.err.println("Error in converting the actual time");
        }
        result.setVerificationTime(verificationTime);

        boolean verificationResult = policySatisfied == positive;
        result.setVerificationResult(verificationResult);

        String verificationMessage = "Policy verification result " + verificationResult;
        result.setVerificationResultMsg(verificationMessage);

        result.setPolicy(policyName);

        return result;
    }
}
