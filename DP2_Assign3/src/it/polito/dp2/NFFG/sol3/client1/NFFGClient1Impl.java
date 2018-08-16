/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client1;

import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.lab3.AlreadyLoadedException;
import it.polito.dp2.NFFG.lab3.NFFGClient;
import it.polito.dp2.NFFG.lab3.ServiceException;
import it.polito.dp2.NFFG.lab3.UnknownNameException;
import it.polito.dp2.NFFG.sol3.client1.entities.Localhost_NffgServiceRest;
import it.polito.dp2.NFFG.sol3.client1.entities.NffgType;
import it.polito.dp2.NFFG.sol3.client1.entities.NffgVerifierType;
import it.polito.dp2.NFFG.sol3.client1.entities.NffgsType;
import it.polito.dp2.NFFG.sol3.client1.entities.PoliciesType;
import it.polito.dp2.NFFG.sol3.client1.entities.ReachibilityPolicyType;
import it.polito.dp2.NFFG.sol3.client1.entities.ReferenceByIDType;
import it.polito.dp2.NFFG.sol3.client1.entities.VerificationResultType;
import it.polito.dp2.NFFG.sol3.client1.entities.VerificationResultsType;
import it.polito.dp2.NFFG.sol3.client1.util.NffgClient1Converter;
import java.net.URI;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author a.zanotti
 */
public class NFFGClient1Impl implements NFFGClient {

    private it.polito.dp2.NFFG.sol3.client1.entities.ObjectFactory factory;
    private Localhost_NffgServiceRest.NffgVerifier nffgVerifierResource;
    private NffgClient1Converter converter;

    NFFGClient1Impl(NffgVerifier monitor, URI baseUri) {
        this.factory = new it.polito.dp2.NFFG.sol3.client1.entities.ObjectFactory();
        this.converter = new NffgClient1Converter(factory, monitor);
        this.nffgVerifierResource = Localhost_NffgServiceRest.nffgVerifier(Localhost_NffgServiceRest.createClient(), baseUri);
    }

    public void loadNFFG(String name) throws UnknownNameException, AlreadyLoadedException, ServiceException {

        NffgType nffg = converter.getNffg(name);
        if (nffg == null) {
            throw new UnknownNameException("Not found a Nffg with " + name + " as name");
        }
        NffgsType nffgs = factory.createNffgsType();
        nffgs.getNffg().add(nffg);
        try {
            nffgVerifierResource.nffgs().postXmlAsReferenceByIDType(nffgs);
        } catch (WebApplicationException exception) {
            Response response = exception.getResponse();
            if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
                throw new AlreadyLoadedException(exception.getMessage());
            } else {
                throw new ServiceException(exception.getMessage());
            }
        }
    }

    public void loadAll() throws AlreadyLoadedException, ServiceException {
        NffgVerifierType nffgVerifier = converter.getNffgVerifier();
        NffgsType nffgs = nffgVerifier.getNffgs();
        PoliciesType policies = nffgVerifier.getPolicies();
        try {
            nffgVerifierResource.nffgs().postXmlAsReferenceByIDType(nffgs);
            nffgVerifierResource.policies().postXmlAsReferenceByIDType(policies);
        } catch(WebApplicationException exception){
            Response response = exception.getResponse();
            if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
                throw new AlreadyLoadedException(exception.getMessage());
            } else {
                throw new ServiceException(exception.getMessage());
            }
        }
    }

    public void loadReachabilityPolicy(String name, String nffgName, boolean isPositive, String srcNodeName, String dstNodeName) throws UnknownNameException, ServiceException {
        PoliciesType policies = factory.createPoliciesType();
        ReachibilityPolicyType policy = factory.createReachibilityPolicyType();
        policy.setName(name);
        policy.setNffg(nffgName);
        policy.setPositive(isPositive);
        policy.setSourceNode(srcNodeName);
        policy.setDestinationNode(dstNodeName);
        policies.getPolicy().add(policy);
        try{
            nffgVerifierResource.policies().postXmlAsReferenceByIDType(policies);
        } catch (WebApplicationException exception){
            Response response = exception.getResponse();
            if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                throw new UnknownNameException(exception.getMessage());
            } else {
                throw new ServiceException(exception.getMessage());
            }
        }
    }

    public void unloadReachabilityPolicy(String name) throws UnknownNameException, ServiceException {
        ReferenceByIDType references = factory.createReferenceByIDType();
        references.getReferenceID().add(name);
        try {
            nffgVerifierResource.policies().deleteXmlAsReferenceByIDType(references);
        } catch(WebApplicationException exception){
            Response response = exception.getResponse();
            if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                throw new UnknownNameException(exception.getMessage());
            } else {
                throw new ServiceException(exception.getMessage());
            }
        }
    }

    public boolean testReachabilityPolicy(String name) throws UnknownNameException, ServiceException {
        ReferenceByIDType references = factory.createReferenceByIDType();
        references.getReferenceID().add(name);
        VerificationResultsType results = null;
        try {
            results = nffgVerifierResource.verifyStoredPolicies().postXmlAsVerificationResultsType(references);
            boolean result = false;
            for (VerificationResultType verificationResult : results.getVerificationResult()) {
                if (verificationResult.getPolicy().equalsIgnoreCase(name)) {
                    result = verificationResult.isVerificationResult();
                }
            }
            return result;
        } catch(WebApplicationException exception) {
            Response response = exception.getResponse();
            if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                throw new UnknownNameException(exception.getMessage());
            } else {
                throw new ServiceException(exception.getMessage());
            }
        }
    }

}
