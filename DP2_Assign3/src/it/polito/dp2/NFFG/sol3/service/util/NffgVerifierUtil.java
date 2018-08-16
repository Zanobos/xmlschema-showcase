/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.service.util;

import it.polito.dp2.NFFG.sol3.service.entities.NffgType;
import it.polito.dp2.NFFG.sol3.service.entities.NffgVerifierType;
import it.polito.dp2.NFFG.sol3.service.entities.NffgsType;
import it.polito.dp2.NFFG.sol3.service.entities.PoliciesType;
import it.polito.dp2.NFFG.sol3.service.entities.PolicyType;
import it.polito.dp2.NFFG.sol3.service.entities.ReachibilityPolicyType;
import it.polito.dp2.NFFG.sol3.service.entities.ReferenceByIDType;
import it.polito.dp2.NFFG.sol3.service.entities.TraversalPolicyType;
import it.polito.dp2.NFFG.sol3.service.entities.VerificationResultType;
import it.polito.dp2.NFFG.sol3.service.entities.VerificationResultsType;
import it.polito.dp2.NFFG.sol3.service.exception.AlreadyLoadedException;
import it.polito.dp2.NFFG.sol3.service.exception.ServiceException;
import it.polito.dp2.NFFG.sol3.service.exception.UnknownNameException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.ForbiddenException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author a.zanotti
 */
public class NffgVerifierUtil {

    private static final NffgDataSource nffgDataSource;
    private static final PolicyDataSource policyDataSource;
    private static final it.polito.dp2.NFFG.sol3.service.entities.ObjectFactory factory;

    static {
        //init
        nffgDataSource = DataSourceFactory.createNffgDataSource();
        policyDataSource = DataSourceFactory.createPolicyDataSource();
        factory = new it.polito.dp2.NFFG.sol3.service.entities.ObjectFactory();
    }

    public static NffgVerifierType getNffgVerifier() {
        synchronized (nffgDataSource) {
            NffgVerifierType nffgVerifierType = factory.createNffgVerifierType();
            NffgsType nffgs = nffgDataSource.load();
            nffgVerifierType.setNffgs(nffgs);
            PoliciesType policies = policyDataSource.load();
            nffgVerifierType.setPolicies(policies);
            return nffgVerifierType;
        }
    }

    public static NffgsType getNffgs() {
        synchronized (nffgDataSource) {
            NffgsType nffgs = nffgDataSource.load();
            return nffgs;
        }
    }

    public static ReferenceByIDType uploadNffgs(NffgsType nffgs) throws AlreadyLoadedException, ServiceException {
        synchronized (nffgDataSource) {
            ReferenceByIDType reference = nffgDataSource.save(nffgs);
            return reference;
        }
    }

    public static ReferenceByIDType deleteNffgs(ReferenceByIDType nffgs, boolean removeAssociatedPolicies) throws UnknownNameException, ForbiddenException, ServiceException {
        synchronized (nffgDataSource) {
            ReferenceByIDType referenceDeleted = factory.createReferenceByIDType();
            if (removeAssociatedPolicies) {
                for (String nffgName : nffgs.getReferenceID()) {
                    policyDataSource.deleteFromNffg(nffgName);
                    String reference = nffgDataSource.delete(nffgName);
                    referenceDeleted.getReferenceID().add(reference);
                }
            } else {
                for (String nffgName : nffgs.getReferenceID()) {
                    PoliciesType policies = policyDataSource.loadFromNffg(nffgName);
                    if (policies.getPolicy().isEmpty()) {
                        String reference = nffgDataSource.delete(nffgName);
                        referenceDeleted.getReferenceID().add(reference);
                    } else {
                        throw new ForbiddenException("Policies exists for nffg " + nffgName);
                    }
                }
            }
            return referenceDeleted;
        }
    }

    public static NffgType getNffg(String id) throws UnknownNameException {
        synchronized (nffgDataSource) {
            NffgType nffg = nffgDataSource.load(id);
            return nffg;
        }
    }

    public static PoliciesType getPolicies(String nffgName) throws UnknownNameException {
        synchronized (nffgDataSource) {
            PoliciesType policies;
            if (nffgName == null) {
                policies = policyDataSource.load();
            } else {
                policies = policyDataSource.loadFromNffg(nffgName);
            }
            return policies;
        }
    }

    public static ReferenceByIDType uploadPolicies(PoliciesType policies) throws UnknownNameException {
        synchronized (nffgDataSource) {
            ReferenceByIDType references = factory.createReferenceByIDType();
            for (PolicyType policy : policies.getPolicy()) {
                if (nffgDataSource.exist(policy.getNffg())) {
                    String reference = policyDataSource.save(policy);
                    references.getReferenceID().add(reference);
                } else {
                    throw new UnknownNameException("Can not save a policy referencing a non existing NFFG: " + policy.getNffg());
                }
            }
            return references;
        }
    }

    public static ReferenceByIDType deletePolicies(ReferenceByIDType policies) throws UnknownNameException {
        synchronized (nffgDataSource) {
            ReferenceByIDType referenceDeleted = policyDataSource.delete(policies);
            return referenceDeleted;
        }
    }

    public static PolicyType getPolicy(String id) throws UnknownNameException {
        synchronized (nffgDataSource) {
            PolicyType policy = policyDataSource.load(id);
            return policy;
        }
    }

    public static VerificationResultsType verifyStoredPolicies(ReferenceByIDType policies) throws UnknownNameException, ServiceException {
        synchronized (nffgDataSource) {
            PoliciesType policiesLoaded = policyDataSource.load(policies);

            verifyPolicies(policiesLoaded);

            policyDataSource.save(policiesLoaded);

            VerificationResultsType results = extractResults(policiesLoaded);
            return results;
        }
    }

    public static VerificationResultsType verifyNewPolicies(PoliciesType policies) throws UnknownNameException, ServiceException {
        synchronized (nffgDataSource) {
            verifyPolicies(policies);

            VerificationResultsType results = extractResults(policies);
            return results;
        }
    }

    private static void verifyPolicies(PoliciesType policies) throws UnknownNameException, ServiceException {
        for (PolicyType policy : policies.getPolicy()) {
            VerificationResultType result;
            if (policy instanceof TraversalPolicyType) {
                TraversalPolicyType traversalPolicy = (TraversalPolicyType) policy;
                result = nffgDataSource.checkTraversal(traversalPolicy);
            } else {
                ReachibilityPolicyType reachibilityPolicy = (ReachibilityPolicyType) policy;
                result = nffgDataSource.checkReachability(reachibilityPolicy);
            }
            policy.setResult(result);
        }
    }

    private static VerificationResultsType extractResults(PoliciesType policies) {
        VerificationResultsType results = factory.createVerificationResultsType();
        for (PolicyType policy : policies.getPolicy()) {
            results.getVerificationResult().add(policy.getResult());
        }
        return results;
    }

    public static NffgVerifierType getPoliciesAndNffgs(String nffgName, String verificationTime) throws UnknownNameException, DatatypeConfigurationException {
        synchronized (nffgDataSource) {
            NffgVerifierType nffgVerifierType = factory.createNffgVerifierType();
            PoliciesType policies;
            NffgsType nffgs;
            //First I load nffgs and policies checking the nffgName parameter
            if (nffgName != null) {
                policies = policyDataSource.loadFromNffg(nffgName);
                NffgType nffg = nffgDataSource.load(nffgName);
                nffgs = factory.createNffgsType();
                nffgs.getNffg().add(nffg);
            } else {
                policies = policyDataSource.load();
                nffgs = nffgDataSource.load();
            }

            //Then if verificationtime is valorized, I filter
            if (verificationTime != null) {
                PoliciesType policiesFiltered = factory.createPoliciesType();
                Map<String, NffgType> nffgsFiltered = new HashMap<String, NffgType>();
                //If the query param has not been encoded, it breaks. I replace the space if it ir present
                //In order to avoid this condition and to help not experienced clients
                verificationTime = myFormatTime(verificationTime);
                XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(verificationTime);
                Instant verificationTimeInstant = xmlCalendar.toGregorianCalendar().toInstant();
                for (PolicyType policy : policies.getPolicy()) {
                    if (policy.getResult() != null && policy.getResult().getVerificationTime() != null) {
                        XMLGregorianCalendar policyVerificationTime = policy.getResult().getVerificationTime();
                        Instant policyVerificationTimeInstant = policyVerificationTime.toGregorianCalendar().toInstant();
                        if (policyVerificationTimeInstant.isAfter(verificationTimeInstant)) {
                            policiesFiltered.getPolicy().add(policy);
                            NffgType nffg = nffgDataSource.load(nffgName);
                            nffgsFiltered.putIfAbsent(nffgName, nffg);
                        }
                    }
                }
                nffgs = factory.createNffgsType();
                nffgs.getNffg().addAll(nffgsFiltered.values());
                policies = policiesFiltered;
            }

            nffgVerifierType.setNffgs(nffgs);
            nffgVerifierType.setPolicies(policies);

            return nffgVerifierType;
        }
    }

    private static String myFormatTime(String verificationTime) {
        String formattedTime = verificationTime.trim();
        formattedTime = formattedTime.replace(' ', '+');
        return formattedTime;
    }

}
