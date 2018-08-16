/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol2;

import com.sun.jersey.api.client.ClientResponse;
import it.polito.dp2.NFFG.LinkReader;
import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.lab2.NoGraphException;
import it.polito.dp2.NFFG.lab2.ReachabilityTester;
import it.polito.dp2.NFFG.lab2.ServiceException;
import it.polito.dp2.NFFG.lab2.UnknownNameException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author a.zanotti
 */
public class ReachabilityTesterImpl implements ReachabilityTester {

    private static final String TYPE = "Link";
    
    private NffgVerifier monitor;
    private NffgReader actualNffg;
    
    private Map<String, String> idmaps;
    private Set<String> relationShipIds;
    private it.polito.dp2.NFFG.sol2.ObjectFactory objectFactory;
    private Localhost_Neo4JXMLRest.Resource resource;

    public ReachabilityTesterImpl(NffgVerifier monitor, URI baseUri) {
        this.monitor=monitor;
        resource = Localhost_Neo4JXMLRest.resource(Localhost_Neo4JXMLRest.createClient(), baseUri);
        actualNffg = null;
        idmaps = new HashMap<String, String>();
        relationShipIds = new HashSet<String>();
        objectFactory = new ObjectFactory();
    }

    @Override
    public void loadNFFG(String name) throws UnknownNameException, ServiceException {

        NffgReader nffg = monitor.getNffg(name);
        if (nffg == null) {
            throw new UnknownNameException("Not found a Nffg with " + name + " as name");
        }

        try {
            //I clear all the nodes and relationships
            resource.nodes().deleteAsXml(ClientResponse.class);
            if (relationShipIds.size() > 0) {
                for (String relationShipId : relationShipIds) {
                    resource.relationshipRelationshipid(relationShipId).deleteAsXml(ClientResponse.class);
                }
            }
            relationShipIds.clear();
            idmaps.clear();

            if(nffg.getNodes()!=null){
                for (NodeReader nodeReader : nffg.getNodes()) {

                    //I create the node
                    Node node = objectFactory.createNode();
                    Property property = objectFactory.createProperty();
                    property.setName("name");
                    property.setValue(nodeReader.getName());
                    node.getProperty().add(property);
                    // I upload the node
                    Node nodePosted = resource.node().postXmlAsNode(node);
                    idmaps.put(nodeReader.getName(), nodePosted.getId());
                }

                for (NodeReader nodeReader : nffg.getNodes()) {
                    Set<LinkReader> links = nodeReader.getLinks();
                    for (LinkReader linkReader : links) {
                        Relationship relationship = objectFactory.createRelationship();
                        relationship.setDstNode(idmaps.get(linkReader.getDestinationNode().getName()));
                        relationship.setType(TYPE);
                        Relationship relationshipPosted = resource.nodeNodeidRelationship(idmaps.get(linkReader.getSourceNode().getName())).postXmlAsRelationship(relationship);
                        relationShipIds.add(relationshipPosted.getId());
                    }
                }
            }
            // Even if my nffg has no nodes, I loaded it
            actualNffg = nffg;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean testReachability(String srcName, String destName) throws UnknownNameException, ServiceException, NoGraphException {

        boolean result = false;

        if (actualNffg == null || idmaps.isEmpty()) {
            throw new NoGraphException("No graph loaded");
        }

        String srcid = idmaps.get(srcName);
        String destid = idmaps.get(destName);
        if (srcid == null) {
            throw new UnknownNameException(srcName + " not found in the graph");
        }
        if (destid == null) {
            throw new UnknownNameException(destName + " not found in the graph");
        }

        try {
            Paths paths = resource.nodeNodeidPaths(srcid).getAsPaths(destid);
            if (paths.getPath() != null && paths.getPath().size() > 0) {
                result = true;
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return result;
    }

    @Override
    public String getCurrentGraphName() {
        String actualGraphName = null;
        if (actualNffg != null) {
            actualGraphName = actualNffg.getName();
        }
        return actualGraphName;
    }

}
