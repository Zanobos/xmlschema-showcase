/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client2.util;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.sol3.client2.entities.FuncType;
import it.polito.dp2.NFFG.sol3.client2.entities.LinkType;
import it.polito.dp2.NFFG.sol3.client2.entities.NffgType;
import it.polito.dp2.NFFG.sol3.client2.entities.NffgVerifierType;
import it.polito.dp2.NFFG.sol3.client2.entities.NffgsType;
import it.polito.dp2.NFFG.sol3.client2.entities.NodeType;
import it.polito.dp2.NFFG.sol3.client2.entities.PoliciesType;
import it.polito.dp2.NFFG.sol3.client2.entities.PolicyType;
import it.polito.dp2.NFFG.sol3.client2.entities.TraversalPolicyType;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author a.zanotti
 */
public class NffgClient2Converter {

    private Map<String, NffgReader> convertNffgsTypeInMap(NffgsType nffgs) {
        Map<String, NffgReader> nffgMap = null;
        if (nffgs != null) {
            nffgMap = new HashMap<String, NffgReader>();
            for (NffgType nffgType : nffgs.getNffg()) {
                NffgReader nffg = convertNffgType(nffgType);
                nffgMap.put(nffg.getName(), nffg);
            }
        }
        return nffgMap;
    }

    public Set<NffgReader> convertNffgsType(NffgsType nffgs) {
        Set<NffgReader> nffgSet = new LinkedHashSet<NffgReader>();
        Map<String, NffgReader> nffgMap = convertNffgsTypeInMap(nffgs);
        if (nffgMap != null) {
            nffgSet = new LinkedHashSet<NffgReader>(nffgMap.values());
        }
        return nffgSet;
    }

    public NffgReader convertNffgType(NffgType nffgType) {

        NffgImpl nffgImpl = new NffgImpl();
        nffgImpl.setName(nffgType.getName());
        nffgImpl.setUpdateTime(nffgType.getUpdateTime().toGregorianCalendar());

        //I prepare all the nodes
        for (NodeType nodeType : nffgType.getNode()) {
            NodeImpl nodeImpl = new NodeImpl();
            nodeImpl.setName(nodeType.getName());
            nodeImpl.setFunctionalType(FunctionalType.valueOf(nodeType.getFunctionality().getFunc()));
            nffgImpl.addNode(nodeImpl);
        }

        //I prepare all the links, and add to the nodes the links
        for (LinkType linkType : nffgType.getLink()) {

            LinkImpl linkImpl = new LinkImpl();
            linkImpl.setName(linkType.getName());

            NodeImpl sourceNode = (NodeImpl) nffgImpl.getNode(linkType.getSourceNode());
            NodeReader destNode = nffgImpl.getNode(linkType.getDestinationNode());

            linkImpl.setSourceNode(sourceNode);
            linkImpl.setDestinationNode(destNode);

            sourceNode.addLink(linkImpl);

        }

        return nffgImpl;
    }

    private Map<String, PolicyReader> convertPoliciesTypeInMap(PoliciesType policies, Map<String, NffgReader> nffgMap) {

        Map<String, PolicyReader> policyReaderMap = null;
        if (policies != null) {
            policyReaderMap = new HashMap<String, PolicyReader>();
            for (PolicyType policyType : policies.getPolicy()) {

                PolicyImpl policyImpl;

                if (policyType instanceof TraversalPolicyType) {
                    policyImpl = new TraversalPolicyImpl();
                    for (FuncType funcType : ((TraversalPolicyType) policyType).getFunctionality()) {
                        ((TraversalPolicyImpl) policyImpl).addFunctionalType(FunctionalType.valueOf(funcType.getFunc().trim()));
                    }
                } else {
                    policyImpl = new ReachabilityPolicyImpl();
                }
                policyImpl.setName(policyType.getName());
                policyImpl.setPositive(policyType.isPositive());

                NffgReader nffg = nffgMap.get(policyType.getNffg());
                policyImpl.setNffg(nffg);

                if (policyType.getResult() != null) {
                    VerificationResultImpl result = new VerificationResultImpl();
                    result.setResult(policyType.getResult().isVerificationResult());
                    result.setMessage(policyType.getResult().getVerificationResultMsg());
                    result.setTime(policyType.getResult().getVerificationTime().toGregorianCalendar());
                    result.setPolicy(policyImpl);
                    policyImpl.setVerificationResult(result);
                }
                policyReaderMap.put(policyImpl.getName(), policyImpl);
            }
        }
        return policyReaderMap;
    }

    public Set<PolicyReader> convertPoliciesType(NffgVerifierType nffgVerifierType) {
        Set<PolicyReader> policyReaderSet = null;
        Map<String, NffgReader> nffgMap = convertNffgsTypeInMap(nffgVerifierType.getNffgs());
        Map<String, PolicyReader> policiesTypeInMap = convertPoliciesTypeInMap(nffgVerifierType.getPolicies(), nffgMap);
        if (policiesTypeInMap != null) {
            policyReaderSet = new LinkedHashSet<PolicyReader>(policiesTypeInMap.values());
        }
        return policyReaderSet;
    }

    public XMLGregorianCalendar getXMLGregorianCalendarFromCalendar(Calendar calendarTime) {
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
