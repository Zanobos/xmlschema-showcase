/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client2.util;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.LinkReader;
import it.polito.dp2.NFFG.NodeReader;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author a.zanotti
 */
public class NodeImpl extends NamedEntityImpl implements NodeReader {

    private FunctionalType functionalType;
    private Map<String, LinkReader> linkMap;

    @Override
    public FunctionalType getFuncType() {
        return functionalType;
    }

    @Override
    public Set<LinkReader> getLinks() {
        if (linkMap == null) {
            linkMap = new HashMap<String, LinkReader>();
        }
        return new LinkedHashSet(linkMap.values());
    }

    void setFunctionalType(FunctionalType functionalType) {
        this.functionalType = functionalType;
    }

    void addLink(LinkReader linkReader) {
        if (linkMap == null) {
            linkMap = new HashMap<String, LinkReader>();
        }
        linkMap.put(linkReader.getName(), linkReader);
    }

}
