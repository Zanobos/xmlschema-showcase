/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client2.util;

import it.polito.dp2.NFFG.NamedEntityReader;

/**
 *
 * @author a.zanotti
 */
public class NamedEntityImpl implements NamedEntityReader{
    
    private String name;

    @Override
    public String getName() {
        return name;
    }
    
    void setName(String name) {
        this.name = name;
    }
    
}
