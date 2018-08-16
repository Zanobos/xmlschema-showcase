//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.10 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2017.01.01 alle 11:21:21 AM CET 
//


package it.polito.dp2.NFFG.sol3.service.entities;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.polito.dp2.NFFG.sol3.service.entities package. 
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

    private final static QName _NffgVerifier_QNAME = new QName("", "NffgVerifier");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.polito.dp2.NFFG.sol3.service.entities
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NffgVerifierType }
     * 
     */
    public NffgVerifierType createNffgVerifierType() {
        return new NffgVerifierType();
    }

    /**
     * Create an instance of {@link PolicyType }
     * 
     */
    public PolicyType createPolicyType() {
        return new PolicyType();
    }

    /**
     * Create an instance of {@link ReferenceByIDType }
     * 
     */
    public ReferenceByIDType createReferenceByIDType() {
        return new ReferenceByIDType();
    }

    /**
     * Create an instance of {@link VerificationResultType }
     * 
     */
    public VerificationResultType createVerificationResultType() {
        return new VerificationResultType();
    }

    /**
     * Create an instance of {@link FuncType }
     * 
     */
    public FuncType createFuncType() {
        return new FuncType();
    }

    /**
     * Create an instance of {@link VerificationResultsType }
     * 
     */
    public VerificationResultsType createVerificationResultsType() {
        return new VerificationResultsType();
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
     * Create an instance of {@link NamedEntityType }
     * 
     */
    public NamedEntityType createNamedEntityType() {
        return new NamedEntityType();
    }

    /**
     * Create an instance of {@link ReachibilityPolicyType }
     * 
     */
    public ReachibilityPolicyType createReachibilityPolicyType() {
        return new ReachibilityPolicyType();
    }

    /**
     * Create an instance of {@link NodeType }
     * 
     */
    public NodeType createNodeType() {
        return new NodeType();
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
     * Create an instance of {@link LinkType }
     * 
     */
    public LinkType createLinkType() {
        return new LinkType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NffgVerifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NffgVerifier")
    public JAXBElement<NffgVerifierType> createNffgVerifier(NffgVerifierType value) {
        return new JAXBElement<NffgVerifierType>(_NffgVerifier_QNAME, NffgVerifierType.class, null, value);
    }

}
