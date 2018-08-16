/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.LinkReader;
import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;
import it.polito.dp2.NFFG.NffgVerifierFactory;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.ReachabilityPolicyReader;
import it.polito.dp2.NFFG.TraversalPolicyReader;
import it.polito.dp2.NFFG.sol1.jaxb.FuncType;
import it.polito.dp2.NFFG.sol1.jaxb.LinkType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgVerifierType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgsType;
import it.polito.dp2.NFFG.sol1.jaxb.NodeType;
import it.polito.dp2.NFFG.sol1.jaxb.ObjectFactory;
import it.polito.dp2.NFFG.sol1.jaxb.PoliciesType;
import it.polito.dp2.NFFG.sol1.jaxb.PolicyType;
import it.polito.dp2.NFFG.sol1.jaxb.ReachibilityPolicyType;
import it.polito.dp2.NFFG.sol1.jaxb.TraversalPolicyType;
import it.polito.dp2.NFFG.sol1.jaxb.VerificationResultType;
import java.io.File;
import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author a.zanotti
 */
public class NffgInfoSerializer {

    private File output;
    private NffgVerifier monitor;
    ObjectFactory objectFactory;

    public NffgInfoSerializer(String args[]) throws ArrayIndexOutOfBoundsException, NffgVerifierException, IOException {
        output = new File(args[0]);
        if (!output.exists()) {
            output.createNewFile();
        }
        NffgVerifierFactory factory = NffgVerifierFactory.newInstance();
        monitor = factory.newNffgVerifier();
    }


    public static void main(String[] args) {
        NffgInfoSerializer serializer;

        try {
            serializer = new NffgInfoSerializer(args);
            serializer.serialize();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Missing the name of the output file. Exiting...");
            System.exit(1);
        } catch (NffgVerifierException ex) {
            System.err.println("Could not instantiate data generator.");
            System.exit(2);
        } catch (IOException ex) {
            System.err.println("Could not create the file.");
            System.exit(3);
        }
    }

    public void serialize() {
        //extracting the data from the monitor
        objectFactory = new ObjectFactory();
        NffgVerifierType nffgVerifierType = createNffgVerifierBinding(monitor, objectFactory);

        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance("it.polito.dp2.NFFG.sol1.jaxb");
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "xsd/nffgInfo.xsd");
            marshaller.marshal(objectFactory.createNffgVerifier(nffgVerifierType), output);
        } catch (JAXBException ex) {
            System.err.println("Error in marshalling");
            System.exit(3);
        }

    }

    private NffgVerifierType createNffgVerifierBinding(NffgVerifier monitor, ObjectFactory objectFactory) {

        //Temporary maps
        Map<String, NffgType> nffgTypeMap = new HashMap<String, NffgType>();
        Map<String, PolicyType> policyTypeMap = new HashMap<String, PolicyType>();

        //Set of nffg
        Set<NffgReader> nffgs = monitor.getNffgs();
        if(nffgs!=null){
            for (NffgReader nffgReader : nffgs) {

                Map<String, LinkType> linkTypeMap = new HashMap<String, LinkType>();

                NffgType nffgType = objectFactory.createNffgType();
                nffgType.setName(nffgReader.getName());

                nffgType.setUpdateTime(getXMLGregorianCalendarFromCalendar(nffgReader.getUpdateTime()));

                Set<NodeReader> nodes = nffgReader.getNodes();
                for (NodeReader nodeReader : nodes) {

                    NodeType nodeType = objectFactory.createNodeType();
                    nodeType.setName(nodeReader.getName());

                    it.polito.dp2.NFFG.FunctionalType funcType = nodeReader.getFuncType();
                    it.polito.dp2.NFFG.sol1.jaxb.FuncType func = objectFactory.createFuncType();
                    func.setFunc(funcType.name());
                    nodeType.setFunctionality(func);

                    Set<LinkReader> links = nodeReader.getLinks();
                    for (LinkReader link : links) {
                        //Node references links
                        nodeType.getLink().add(link.getName());

                        LinkType linkType = objectFactory.createLinkType();
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

        if(policies!=null){
            for (PolicyReader policy : policies) {

                PolicyType policyType;

                if (policy instanceof TraversalPolicyReader) {
                    policyType = objectFactory.createTraversalPolicyType();

                    for (FunctionalType func : ((TraversalPolicyReader) policy).getTraversedFuctionalTypes()) {
                        FuncType funcType = objectFactory.createFuncType();
                        funcType.setFunc(func.name());
                        ((TraversalPolicyType) policyType).getFunctionality().add(funcType);
                    }
                } else {
                    policyType = objectFactory.createReachibilityPolicyType();
                }

                ((ReachibilityPolicyType) policyType).setDestinationNode(((ReachabilityPolicyReader) policy).getDestinationNode().getName());
                ((ReachibilityPolicyType) policyType).setSourceNode(((ReachabilityPolicyReader) policy).getSourceNode().getName());

                policyType.setName(policy.getName());
                policyType.setNffg(policy.getNffg().getName());
                policyType.setPositive(policy.isPositive());

                if (policy.getResult() != null) {
                    VerificationResultType verificationResultType = objectFactory.createVerificationResultType();
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
        NffgVerifierType nffgVerifierType = objectFactory.createNffgVerifierType();
        
        //Assigning the values in the map
        if (nffgTypeMap.size() > 0) {
            NffgsType nffgsType = new NffgsType();
            for (NffgType nffgType : nffgTypeMap.values()) {
                nffgsType.getNffg().add(nffgType);
            }
            nffgVerifierType.setNffgs(nffgsType);
        }

        if(policyTypeMap.size()>0){
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

}
