/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol3.service.util;

import it.polito.dp2.NFFG.sol3.service.entities.PoliciesType;
import it.polito.dp2.NFFG.sol3.service.entities.PolicyType;
import it.polito.dp2.NFFG.sol3.service.entities.ReferenceByIDType;
import it.polito.dp2.NFFG.sol3.service.exception.UnknownNameException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author a.zanotti
 */
public class EmbeddedPolicyDataSource implements PolicyDataSource {

    private static EmbeddedPolicyDataSource instance;
    
    public static EmbeddedPolicyDataSource getInstance() {
        if(instance == null){
            instance = new EmbeddedPolicyDataSource();
        }
        return instance;
        
    }
    
    private final Map<String, PolicyType> dataSource;
    private final it.polito.dp2.NFFG.sol3.service.entities.ObjectFactory factory;

    private EmbeddedPolicyDataSource() {
        dataSource = new ConcurrentHashMap<String, PolicyType>();
        factory = new it.polito.dp2.NFFG.sol3.service.entities.ObjectFactory();
    }

    public PolicyType load(String id) throws UnknownNameException {
        PolicyType policy = dataSource.get(id);
        if (policy == null) {
            throw new UnknownNameException("Not found a policy with " + id + " as name");
        }
        return policy;
    }

    public PoliciesType load(ReferenceByIDType policies) throws UnknownNameException {
        PoliciesType policiesLoaded = factory.createPoliciesType();
        for (String id : policies.getReferenceID()) {
            PolicyType policy = load(id);
            policiesLoaded.getPolicy().add(policy);
        }

        return policiesLoaded;
    }

    public PoliciesType load() {
        PoliciesType policies = factory.createPoliciesType();
        policies.getPolicy().addAll(new ArrayList<PolicyType>(dataSource.values()));
        return policies;
    }

    public String save(PolicyType policy) {
        String id = policy.getName();

        dataSource.put(id, policy);

        return id;
    }

    public ReferenceByIDType save(PoliciesType policies) {

        ReferenceByIDType references = factory.createReferenceByIDType();

        for (PolicyType policy : policies.getPolicy()) {
            String id = save(policy);
            references.getReferenceID().add(id);
        }

        return references;
    }

    public String delete(String id) throws UnknownNameException {
        PolicyType policy = dataSource.remove(id);
        if (policy == null) {
            throw new UnknownNameException("Not found a policy with " + id + " as name");
        }
        return policy.getName();
    }

    public ReferenceByIDType delete(ReferenceByIDType policies) throws UnknownNameException {

        ReferenceByIDType references = factory.createReferenceByIDType();

        for (String policy : policies.getReferenceID()) {
            String id = delete(policy);
            references.getReferenceID().add(id);
        }

        return references;

    }

    public ReferenceByIDType deleteFromNffg(String nffgName) {

        ReferenceByIDType references = factory.createReferenceByIDType();

        ReferenceByIDType referencesToDelete = factory.createReferenceByIDType();

        for (PolicyType policy : dataSource.values()) {
            if (policy.getNffg().equals(nffgName)) {
                referencesToDelete.getReferenceID().add(policy.getName());
            }
        }

        try {
            references = delete(referencesToDelete);
        } catch (UnknownNameException ex) {
            //Nothing happen
        }

        return references;
    }

    public PoliciesType loadFromNffg(String nffgName) {
        PoliciesType policiesType = factory.createPoliciesType();

        ReferenceByIDType references = factory.createReferenceByIDType();

        for (PolicyType policy : dataSource.values()) {
            if (policy.getNffg().equals(nffgName)) {
                references.getReferenceID().add(policy.getName());
            }
        }
        try {
            policiesType = load(references);
        } catch (UnknownNameException ex) {
            //Nothing happen
        }
        return policiesType;
    }

}
