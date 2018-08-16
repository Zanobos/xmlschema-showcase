/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol2;

import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;
import it.polito.dp2.NFFG.NffgVerifierFactory;
import it.polito.dp2.NFFG.lab2.ReachabilityTester;
import it.polito.dp2.NFFG.lab2.ReachabilityTesterException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author a.zanotti
 */
public class ReachabilityTesterFactory extends it.polito.dp2.NFFG.lab2.ReachabilityTesterFactory
{

    @Override
    public ReachabilityTester newReachabilityTester() throws ReachabilityTesterException {
        
        NffgVerifier monitor = null;
        NffgVerifierFactory factory = NffgVerifierFactory.newInstance();
        try {
            monitor = factory.newNffgVerifier();
        } catch (NffgVerifierException ex) {
            throw new ReachabilityTesterException(ex, "Cannot instantiate random generator");
        }
        String uri = System.getProperty("it.polito.dp2.NFFG.lab2.URL");
        if (uri == null || uri.isEmpty()) {
            throw new ReachabilityTesterException("The property \"it.polito.dp2.NFFG.lab2.URL\" must be valorized");
        }
        
        URI baseUri = null;
        try {
            baseUri = new URI(uri);
        } catch (URISyntaxException ex) {
            throw new ReachabilityTesterException(ex,"The property \"it.polito.dp2.NFFG.lab2.URL\" must be in a correct form");
        }
        ReachabilityTester reachabilityTester = new ReachabilityTesterImpl(monitor, baseUri);
        
        return reachabilityTester;
    }
    
}
