/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client2.util;

import it.polito.dp2.NFFG.NodeReader;
import it.polito.dp2.NFFG.ReachabilityPolicyReader;

/**
 *
 * @author a.zanotti
 */
public class ReachabilityPolicyImpl extends PolicyImpl implements ReachabilityPolicyReader{

    private NodeReader sourceNode;
    private NodeReader destinationNode;
    
    @Override
    public NodeReader getSourceNode() {
        return sourceNode;
    }

    @Override
    public NodeReader getDestinationNode() {
        return destinationNode;
    }
    
}
