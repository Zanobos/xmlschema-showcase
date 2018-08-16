/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client3;

import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author a.zanotti
 */
public class NffgVerifierFactory {

    public NffgClient3Impl newNffgClient3Impl() throws NffgVerifierException {
        
        String uri = System.getProperty("it.polito.dp2.NFFG.lab3.URL");
        if (uri == null || uri.isEmpty()) {
            throw new NffgVerifierException("The property \"it.polito.dp2.NFFG.lab3.URL\" must be valorized");
        }
        
        URI baseUri = null;
        try {
            baseUri = new URI(uri);
        } catch (URISyntaxException ex) {
            throw new NffgVerifierException(ex,"The property \"it.polito.dp2.NFFG.lab3.URL\" must be in a correct form");
        }
        NffgClient3Impl nffgClient = new NffgClient3Impl(baseUri);
        
        return nffgClient;
    }

    
}
