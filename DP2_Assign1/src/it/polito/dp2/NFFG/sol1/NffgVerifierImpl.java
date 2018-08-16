/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.VerificationResultReader;
import it.polito.dp2.NFFG.sol1.jaxb.FuncType;
import it.polito.dp2.NFFG.sol1.jaxb.LinkType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.NffgVerifierType;
import it.polito.dp2.NFFG.sol1.jaxb.NodeType;
import it.polito.dp2.NFFG.sol1.jaxb.PolicyType;
import it.polito.dp2.NFFG.sol1.jaxb.TraversalPolicyType;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author a.zanotti
 */
public class NffgVerifierImpl implements NffgVerifier {

    private Map<String, NffgReader> nffgMap;
    private Map<String, PolicyReader> policyMap;

    //Main constructor, from xml to interfaces
    NffgVerifierImpl(NffgVerifierType nffgVerifierType) {

        nffgMap = new HashMap<String, NffgReader>();
        policyMap = new HashMap<String, PolicyReader>();

        //I create the nffg
        if(nffgVerifierType.getNffgs()!=null){
            for (NffgType nffgType : nffgVerifierType.getNffgs().getNffg()) {

                Map<String, LinkImpl> linkTempMap = new HashMap<String, LinkImpl>();

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
    //            destNode.addLink(linkImpl);

                    linkTempMap.put(linkImpl.getName(), linkImpl);
                }

                nffgMap.put(nffgImpl.getName(), nffgImpl);
            }
        }

        //I create the policies
        if(nffgVerifierType.getPolicies()!= null){
            for (PolicyType policyType : nffgVerifierType.getPolicies().getPolicy()) {

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
                policyMap.put(policyImpl.getName(), policyImpl);
            }
        }
    }

    @Override
    public Set<NffgReader> getNffgs() {
        Set<NffgReader> nffgs = new HashSet<NffgReader>(nffgMap.values());
        return nffgs;
    }

    @Override
    public NffgReader getNffg(String name) {
        return nffgMap.get(name);
    }

    @Override
    public Set<PolicyReader> getPolicies() {
        Set<PolicyReader> policies = new HashSet<PolicyReader>(policyMap.values());
        return policies;
    }

    @Override
    public Set<PolicyReader> getPolicies(String namenffg) {
        Set<PolicyReader> policies = null;
        if (policyMap != null) {
            policies = new HashSet<PolicyReader>();
            for (PolicyReader policy : policyMap.values()) {
                if (policy.getNffg().getName().equalsIgnoreCase(namenffg)) {
                    policies.add(policy);
                }
            }
        }
        return policies;
    }

    @Override
    public Set<PolicyReader> getPolicies(Calendar verificationtime) {

        Set<PolicyReader> policies = null;
        if (policyMap != null) {
            policies = new HashSet<PolicyReader>();
            for (PolicyReader policy : policyMap.values()) {
                VerificationResultReader verificationResult = policy.getResult();
                if ((verificationResult != null) && (verificationResult.getVerificationTime().after(verificationtime))) {
                    policies.add(policy);
                }
            }
        }
        return policies;
    }

}
