package it.polito.dp2.NFFG.sol3.client1.util;

/*
 *  author a.zanotti
 */
import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.LinkReader;
import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.ReachabilityPolicyReader;
import it.polito.dp2.NFFG.TraversalPolicyReader;
import it.polito.dp2.NFFG.sol3.client1.entities.FuncType;
import it.polito.dp2.NFFG.sol3.client1.entities.LinkType;
import it.polito.dp2.NFFG.sol3.client1.entities.NffgType;
import it.polito.dp2.NFFG.sol3.client1.entities.NffgVerifierType;
import it.polito.dp2.NFFG.sol3.client1.entities.NffgsType;
import it.polito.dp2.NFFG.sol3.client1.entities.NodeType;
import it.polito.dp2.NFFG.sol3.client1.entities.ObjectFactory;
import it.polito.dp2.NFFG.sol3.client1.entities.PoliciesType;
import it.polito.dp2.NFFG.sol3.client1.entities.PolicyType;
import it.polito.dp2.NFFG.sol3.client1.entities.ReachibilityPolicyType;
import it.polito.dp2.NFFG.sol3.client1.entities.TraversalPolicyType;
import it.polito.dp2.NFFG.sol3.client1.entities.VerificationResultType;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author a.zanotti
 */
public class NffgClient1Converter {

    private it.polito.dp2.NFFG.sol3.client1.entities.ObjectFactory factory;
    private NffgVerifierType nffgVerifier;

    public NffgClient1Converter(ObjectFactory factory, NffgVerifier monitor) {
        this.factory = factory;
        this.nffgVerifier = initNffg(monitor);
    }

    private NffgVerifierType initNffg(NffgVerifier monitor) {

        //Temporary maps
        Map<String, NffgType> nffgTypeMap = new HashMap<String, NffgType>();
        Map<String, PolicyType> policyTypeMap = new HashMap<String, PolicyType>();

        //Set of nffg
        Set<NffgReader> nffgs = monitor.getNffgs();
        if (nffgs != null) {
            for (NffgReader nffgReader : nffgs) {

                Map<String, LinkType> linkTypeMap = new HashMap<String, LinkType>();

                NffgType nffgType = factory.createNffgType();
                nffgType.setName(nffgReader.getName());

                nffgType.setUpdateTime(getXMLGregorianCalendarFromCalendar(nffgReader.getUpdateTime()));

                Set<NodeReader> nodes = nffgReader.getNodes();
                for (NodeReader nodeReader : nodes) {

                    NodeType nodeType = factory.createNodeType();
                    nodeType.setName(nodeReader.getName());

                    FunctionalType funcType = nodeReader.getFuncType();
                    FuncType func = factory.createFuncType();
                    func.setFunc(funcType.name());
                    nodeType.setFunctionality(func);

                    Set<LinkReader> links = nodeReader.getLinks();
                    for (LinkReader link : links) {
                        //Node references links
                        nodeType.getLink().add(link.getName());

                        LinkType linkType = factory.createLinkType();
                        linkType.setName(link.getName());
                        linkType.setSourceNode(link.getSourceNode().getName());
                        linkType.setDestinationNode(link.getDestinationNode().getName());

                        //Adding in the map
                        linkTypeMap.put(linkType.getName(), linkType);
                    }
                    nffgType.getNode().add(nodeType);
                }

                for (LinkType linkType : linkTypeMap.values()) {
                    nffgType.getLink().add(linkType);
                }
                //Adding in the map
                nffgTypeMap.put(nffgType.getName(), nffgType);
            }
        }

        //Set of policies
        Set<PolicyReader> policies = monitor.getPolicies();

        if (policies != null) {
            for (PolicyReader policy : policies) {

                PolicyType policyType;

                if (policy instanceof TraversalPolicyReader) {
                    policyType = factory.createTraversalPolicyType();

                    for (FunctionalType func : ((TraversalPolicyReader) policy).getTraversedFuctionalTypes()) {
                        FuncType funcType = factory.createFuncType();
                        funcType.setFunc(func.name());
                        ((TraversalPolicyType) policyType).getFunctionality().add(funcType);
                    }
                } else {
                    policyType = factory.createReachibilityPolicyType();
                }

                if(((ReachabilityPolicyReader) policy).getDestinationNode() != null &&
                        ((ReachabilityPolicyReader) policy).getSourceNode()!= null) {
                    ((ReachibilityPolicyType) policyType).setDestinationNode(((ReachabilityPolicyReader) policy).getDestinationNode().getName());
                    ((ReachibilityPolicyType) policyType).setSourceNode(((ReachabilityPolicyReader) policy).getSourceNode().getName());
                }

                policyType.setName(policy.getName());
                policyType.setNffg(policy.getNffg().getName());
                policyType.setPositive(policy.isPositive());

                if (policy.getResult() != null) {
                    VerificationResultType verificationResultType = factory.createVerificationResultType();
                    verificationResultType.setVerificationResult(policy.getResult().getVerificationResult());
                    verificationResultType.setVerificationResultMsg(policy.getResult().getVerificationResultMsg());
                    verificationResultType.setVerificationTime(getXMLGregorianCalendarFromCalendar(policy.getResult().getVerificationTime()));
                    policyType.setResult(verificationResultType);
                }

                //Adding in the map
                policyTypeMap.put(policyType.getName(), policyType);
            }
        }

        //Final output
        NffgVerifierType nffgVerifierType = factory.createNffgVerifierType();

        //Assigning the values in the map
        if (nffgTypeMap.size() > 0) {
            NffgsType nffgsType = new NffgsType();
            for (NffgType nffgType : nffgTypeMap.values()) {
                nffgsType.getNffg().add(nffgType);
            }
            nffgVerifierType.setNffgs(nffgsType);
        }

        if (policyTypeMap.size() > 0) {
            PoliciesType policiesType = new PoliciesType();
            for (PolicyType policyType : policyTypeMap.values()) {
                policiesType.getPolicy().add(policyType);
            }
            nffgVerifierType.setPolicies(policiesType);
        }

        return nffgVerifierType;
    }

    private XMLGregorianCalendar getXMLGregorianCalendarFromCalendar(Calendar calendarTime) {
        Date dateTime = calendarTime.getTime();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dateTime);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            System.err.println("Error in converting the updateTime");
            System.exit(4);
        }
        return xmlCalendar;
    }

    public NffgType getNffg(String name) {
        NffgType nffgResult = null;
        for (NffgType nffg : nffgVerifier.getNffgs().getNffg()) {
            if (nffg.getName().equals(name)) {
                nffgResult = nffg;
            }
        }
        return nffgResult;
    }

    public NffgVerifierType getNffgVerifier() {
        return this.nffgVerifier;
    }

}
