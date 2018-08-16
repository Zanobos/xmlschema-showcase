/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.client2.util;

import it.polito.dp2.NFFG.NffgReader;
import it.polito.dp2.NFFG.NodeReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author a.zanotti
 */
public class NffgImpl extends NamedEntityImpl implements NffgReader{

    private Calendar updateTime;
    private Map<String,NodeReader> nodeMap;
    
    @Override
    public Calendar getUpdateTime() {
        return updateTime;
    }

    @Override
    public Set<NodeReader> getNodes() {
        Set<NodeReader> nodes = null;
        if(nodeMap!= null) {
            nodes = new HashSet<NodeReader>(nodeMap.values());
        }
        return nodes;
    }

    @Override
    public NodeReader getNode(String name) {
        return nodeMap.get(name);
    }
    
    void setUpdateTime(Calendar updateTime){
        this.updateTime = updateTime;
    }
    
    void addNode(NodeReader nodeReader) {
        if(nodeMap == null) {
            nodeMap = new HashMap<String, NodeReader>();
        }
        nodeMap.put(nodeReader.getName(), nodeReader);
    }
    
}
