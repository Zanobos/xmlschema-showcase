
package it.polito.dp2.NFFG.sol3.client1.entities;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.polito.dp2.NFFG.sol3.client1.entities package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Policy_QNAME = new QName("", "Policy");
    private final static QName _Nffgs_QNAME = new QName("", "Nffgs");
    private final static QName _Policies_QNAME = new QName("", "Policies");
    private final static QName _VerificationResult_QNAME = new QName("", "VerificationResult");
    private final static QName _NffgVerifier_QNAME = new QName("", "NffgVerifier");
    private final static QName _Nffg_QNAME = new QName("", "Nffg");
    private final static QName _TraversalPolicy_QNAME = new QName("", "TraversalPolicy");
    private final static QName _ReferenceByID_QNAME = new QName("", "ReferenceByID");
    private final static QName _ReachibilityPolicy_QNAME = new QName("", "ReachibilityPolicy");
    private final static QName _VerificationResults_QNAME = new QName("", "VerificationResults");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.polito.dp2.NFFG.sol3.client1.entities
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PolicyType }
     * 
     */
    public PolicyType createPolicyType() {
        return new PolicyType();
    }

    /**
     * Create an instance of {@link NffgsType }
     * 
     */
    public NffgsType createNffgsType() {
        return new NffgsType();
    }

    /**
     * Create an instance of {@link PoliciesType }
     * 
     */
    public PoliciesType createPoliciesType() {
        return new PoliciesType();
    }

    /**
     * Create an instance of {@link VerificationResultType }
     * 
     */
    public VerificationResultType createVerificationResultType() {
        return new VerificationResultType();
    }

    /**
     * Create an instance of {@link NffgVerifierType }
     * 
     */
    public NffgVerifierType createNffgVerifierType() {
        return new NffgVerifierType();
    }

    /**
     * Create an instance of {@link NffgType }
     * 
     */
    public NffgType createNffgType() {
        return new NffgType();
    }

    /**
     * Create an instance of {@link TraversalPolicyType }
     * 
     */
    public TraversalPolicyType createTraversalPolicyType() {
        return new TraversalPolicyType();
    }

    /**
     * Create an instance of {@link ReferenceByIDType }
     * 
     */
    public ReferenceByIDType createReferenceByIDType() {
        return new ReferenceByIDType();
    }

    /**
     * Create an instance of {@link ReachibilityPolicyType }
     * 
     */
    public ReachibilityPolicyType createReachibilityPolicyType() {
        return new ReachibilityPolicyType();
    }

    /**
     * Create an instance of {@link VerificationResultsType }
     * 
     */
    public VerificationResultsType createVerificationResultsType() {
        return new VerificationResultsType();
    }

    /**
     * Create an instance of {@link FuncType }
     * 
     */
    public FuncType createFuncType() {
        return new FuncType();
    }

    /**
     * Create an instance of {@link NamedEntityType }
     * 
     */
    public NamedEntityType createNamedEntityType() {
        return new NamedEntityType();
    }

    /**
     * Create an instance of {@link NodeType }
     * 
     */
    public NodeType createNodeType() {
        return new NodeType();
    }

    /**
     * Create an instance of {@link LinkType }
     * 
     */
    public LinkType createLinkType() {
        return new LinkType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PolicyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Policy")
    public JAXBElement<PolicyType> createPolicy(PolicyType value) {
        return new JAXBElement<PolicyType>(_Policy_QNAME, PolicyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NffgsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Nffgs")
    public JAXBElement<NffgsType> createNffgs(NffgsType value) {
        return new JAXBElement<NffgsType>(_Nffgs_QNAME, NffgsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PoliciesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Policies")
    public JAXBElement<PoliciesType> createPolicies(PoliciesType value) {
        return new JAXBElement<PoliciesType>(_Policies_QNAME, PoliciesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerificationResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "VerificationResult")
    public JAXBElement<VerificationResultType> createVerificationResult(VerificationResultType value) {
        return new JAXBElement<VerificationResultType>(_VerificationResult_QNAME, VerificationResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NffgVerifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NffgVerifier")
    public JAXBElement<NffgVerifierType> createNffgVerifier(NffgVerifierType value) {
        return new JAXBElement<NffgVerifierType>(_NffgVerifier_QNAME, NffgVerifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NffgType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Nffg")
    public JAXBElement<NffgType> createNffg(NffgType value) {
        return new JAXBElement<NffgType>(_Nffg_QNAME, NffgType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TraversalPolicyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "TraversalPolicy")
    public JAXBElement<TraversalPolicyType> createTraversalPolicy(TraversalPolicyType value) {
        return new JAXBElement<TraversalPolicyType>(_TraversalPolicy_QNAME, TraversalPolicyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceByIDType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ReferenceByID")
    public JAXBElement<ReferenceByIDType> createReferenceByID(ReferenceByIDType value) {
        return new JAXBElement<ReferenceByIDType>(_ReferenceByID_QNAME, ReferenceByIDType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReachibilityPolicyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ReachibilityPolicy")
    public JAXBElement<ReachibilityPolicyType> createReachibilityPolicy(ReachibilityPolicyType value) {
        return new JAXBElement<ReachibilityPolicyType>(_ReachibilityPolicy_QNAME, ReachibilityPolicyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerificationResultsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "VerificationResults")
    public JAXBElement<VerificationResultsType> createVerificationResults(VerificationResultsType value) {
        return new JAXBElement<VerificationResultsType>(_VerificationResults_QNAME, VerificationResultsType.class, null, value);
    }

}
