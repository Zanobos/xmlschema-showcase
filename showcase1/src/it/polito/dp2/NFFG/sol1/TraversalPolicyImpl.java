/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.FunctionalType;
import it.polito.dp2.NFFG.TraversalPolicyReader;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author a.zanotti
 */
public class TraversalPolicyImpl extends ReachabilityPolicyImpl implements TraversalPolicyReader{

    private Set<FunctionalType> functionalTypeSet;
    
    @Override
    public Set<FunctionalType> getTraversedFuctionalTypes() {
        return functionalTypeSet;
    }
    
    void addFunctionalType(FunctionalType functionalType) {
        if(functionalTypeSet == null) {
            functionalTypeSet = new LinkedHashSet<FunctionalType>();
        }
        functionalTypeSet.add(functionalType);
    }
    
}
