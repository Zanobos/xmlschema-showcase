/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client2;

import it.polito.dp2.NFFG.sol3.client2.util.NffgClient2Converter;
import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.sol3.client2.entities.Localhost_NffgServiceRest;
import it.polito.dp2.NFFG.sol3.client2.entities.NffgType;
import it.polito.dp2.NFFG.sol3.client2.entities.NffgVerifierType;
import it.polito.dp2.NFFG.sol3.client2.entities.NffgsType;
import java.net.URI;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.ws.rs.WebApplicationException;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author a.zanotti
 */
public class NFFGClient2Impl implements NffgVerifier {

    private Localhost_NffgServiceRest.NffgVerifier nffgVerifierResource;
    private NffgClient2Converter converter;

    NFFGClient2Impl(URI baseUri) {
        this.nffgVerifierResource = Localhost_NffgServiceRest.nffgVerifier(Localhost_NffgServiceRest.createClient(), baseUri);
        this.converter = new NffgClient2Converter();
    }

    public Set<NffgReader> getNffgs() {
        Set<NffgReader> nffgReaderSet;
        try {
            NffgsType nffgs = nffgVerifierResource.nffgs().getAsNffgsType();
            nffgReaderSet = converter.convertNffgsType(nffgs);
        } catch (WebApplicationException exception) {
            //This method should always return a set
            nffgReaderSet = new LinkedHashSet<NffgReader>();
        }
        return nffgReaderSet;
    }

    public NffgReader getNffg(String nffgName) {
        NffgReader nffgReader;
        try {
            NffgType nffg = nffgVerifierResource.nffgsId(nffgName).getAsNffgType();
            nffgReader = converter.convertNffgType(nffg);
        } catch (WebApplicationException exception) {
            //If the service goes in exception, I return null as from interface description
            nffgReader = null;
        }
        return nffgReader;
    }

    public Set<PolicyReader> getPolicies() {
        Set<PolicyReader> policyReaderSet;
        try {
            NffgVerifierType nffgVerifierType = nffgVerifierResource.policiesAndReferencedNffgs().getAsNffgVerifierType();
            policyReaderSet = converter.convertPoliciesType(nffgVerifierType);
        } catch (WebApplicationException ex) {
            //This method should always return a set
            policyReaderSet = new LinkedHashSet<PolicyReader>();
        }
        return policyReaderSet;
    }

    public Set<PolicyReader> getPolicies(String nffgName) {
        Set<PolicyReader> policyReaderSet;
        try {
            NffgVerifierType nffgVerifierType = nffgVerifierResource.policiesAndReferencedNffgs().getAsNffgVerifierType(nffgName, null);
            policyReaderSet = converter.convertPoliciesType(nffgVerifierType);
        } catch (WebApplicationException ex) {
            policyReaderSet = null;
        }
        return policyReaderSet;
    }

    public Set<PolicyReader> getPolicies(Calendar clndr) {
        Set<PolicyReader> policyReaderSet;
        try {
            //From Calendar to XMLGregorianCalendar to String
            XMLGregorianCalendar verificationTime = converter.getXMLGregorianCalendarFromCalendar(clndr);
            NffgVerifierType nffgVerifierType = nffgVerifierResource.policiesAndReferencedNffgs().getAsNffgVerifierType(null, verificationTime.toString());
            policyReaderSet = converter.convertPoliciesType(nffgVerifierType);
        } catch (WebApplicationException ex) {
            policyReaderSet = null;
        }
        return policyReaderSet;
    }

}
