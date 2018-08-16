/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.service.util;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author a.zanotti
 */
class DataSourceFactory {

    static PolicyDataSource createPolicyDataSource() {
        return EmbeddedPolicyDataSource.getInstance();
    }

    static NffgDataSource createNffgDataSource(){

        String uri = System.getProperty("it.polito.dp2.NFFG.lab3.NEO4JURL");
        if (uri == null || uri.isEmpty()) {
            //Default value
            uri = "http://localhost:8080/Neo4JXML/rest";
        }

        URI baseUri = null;
        try {
            baseUri = new URI(uri);
        } catch (URISyntaxException ex) {
            System.err.println("The Uri is not correct: " + ex.getMessage());
        }
        return Neo4jDataSource.getInstance(baseUri);
    }

}
