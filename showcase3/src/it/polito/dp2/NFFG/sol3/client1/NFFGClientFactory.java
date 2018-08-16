/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client1;

import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;
import it.polito.dp2.NFFG.NffgVerifierFactory;
import it.polito.dp2.NFFG.lab3.NFFGClient;
import it.polito.dp2.NFFG.lab3.NFFGClientException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author a.zanotti
 */
public class NFFGClientFactory extends it.polito.dp2.NFFG.lab3.NFFGClientFactory{

    @Override
    public NFFGClient newNFFGClient() throws NFFGClientException {
        
        NffgVerifier monitor = null;
        NffgVerifierFactory factory = NffgVerifierFactory.newInstance();
        try {
            monitor = factory.newNffgVerifier();
        } catch (NffgVerifierException ex) {
            throw new NFFGClientException(ex, "Cannot instantiate random generator");
        }
        
        String uri = System.getProperty("it.polito.dp2.NFFG.lab3.URL");
        if (uri == null || uri.isEmpty()) {
            throw new NFFGClientException("The property \"it.polito.dp2.NFFG.lab3.URL\" must be valorized");
        }
        
        URI baseUri = null;
        try {
            baseUri = new URI(uri);
        } catch (URISyntaxException ex) {
            throw new NFFGClientException(ex,"The property \"it.polito.dp2.NFFG.lab3.URL\" must be in a correct form");
        }
        return new NFFGClient1Impl(monitor,baseUri);
    }
    
}
