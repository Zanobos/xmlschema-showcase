/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.service.util;

import it.polito.dp2.NFFG.sol3.service.entities.PoliciesType;
import it.polito.dp2.NFFG.sol3.service.entities.PolicyType;
import it.polito.dp2.NFFG.sol3.service.entities.ReferenceByIDType;
import it.polito.dp2.NFFG.sol3.service.exception.UnknownNameException;

/**
 *
 * @author a.zanotti
 */
interface PolicyDataSource {


    public PolicyType load(String id) throws UnknownNameException;
    
    public PoliciesType load(ReferenceByIDType policies) throws UnknownNameException;

    public PoliciesType load();
    
    public String save(PolicyType policy);

    public ReferenceByIDType save(PoliciesType policies);
    
    public String delete(String id) throws UnknownNameException;
    
    public ReferenceByIDType delete(ReferenceByIDType policies) throws UnknownNameException;

    public ReferenceByIDType deleteFromNffg(String nffgName) throws UnknownNameException;

    public PoliciesType loadFromNffg(String nffgName) throws UnknownNameException;
}
