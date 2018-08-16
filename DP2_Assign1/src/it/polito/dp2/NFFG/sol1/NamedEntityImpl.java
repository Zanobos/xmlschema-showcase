/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol1;

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
