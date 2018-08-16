/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.LinkReader;
import it.polito.dp2.NFFG.NodeReader;

/**
 *
 * @author a.zanotti
 */
public class LinkImpl extends NamedEntityImpl implements LinkReader {

    private NodeReader source;
    private NodeReader destination;

    @Override
    public NodeReader getSourceNode() {
        return source;
    }

    @Override
    public NodeReader getDestinationNode() {
        return destination;
    }

    void setSourceNode(NodeReader source) {
        this.source = source;
    }

    void setDestinationNode(NodeReader destination) {
        this.destination = destination;
    }

}
