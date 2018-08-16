/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client3;

import it.polito.dp2.NFFG.sol3.client2.entities.Localhost_NffgServiceRest;
import java.net.URI;

/**
 *
 * @author a.zanotti
 */
public class NffgClient3Impl {

    private final Localhost_NffgServiceRest.Other otherResource;
    
    NffgClient3Impl(URI baseUri) {
        this.otherResource = Localhost_NffgServiceRest.other(Localhost_NffgServiceRest.createClient(), baseUri);
    }
    
    public String getProva() {
        String asXml = otherResource.cachePolicy().getAsXml(String.class);
        return asXml;
    }

    
}
