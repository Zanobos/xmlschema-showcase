/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.service.util;

import it.polito.dp2.NFFG.sol3.service.entities.NffgType;
import it.polito.dp2.NFFG.sol3.service.entities.NffgsType;
import it.polito.dp2.NFFG.sol3.service.entities.ReachibilityPolicyType;
import it.polito.dp2.NFFG.sol3.service.entities.ReferenceByIDType;
import it.polito.dp2.NFFG.sol3.service.entities.TraversalPolicyType;
import it.polito.dp2.NFFG.sol3.service.entities.VerificationResultType;
import it.polito.dp2.NFFG.sol3.service.exception.AlreadyLoadedException;
import it.polito.dp2.NFFG.sol3.service.exception.ServiceException;
import it.polito.dp2.NFFG.sol3.service.exception.UnknownNameException;

/**
 *
 * @author a.zanotti
 */
interface NffgDataSource {

    public String save(NffgType nffg) throws AlreadyLoadedException, ServiceException;

    public ReferenceByIDType save(NffgsType nffgs) throws AlreadyLoadedException, ServiceException;

    public NffgsType load();

    public NffgType load(String id) throws UnknownNameException;

    public VerificationResultType checkTraversal(TraversalPolicyType traversalPolicy) throws UnknownNameException, ServiceException;

    public VerificationResultType checkReachability(ReachibilityPolicyType reachabilityPolicy) throws UnknownNameException, ServiceException;

    public boolean exist(String nffg);

    public String delete(String nffgName) throws UnknownNameException, ServiceException;

}
