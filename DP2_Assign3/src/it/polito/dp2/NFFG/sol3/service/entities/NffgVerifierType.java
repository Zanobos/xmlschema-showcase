//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.10 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2017.01.01 alle 11:21:21 AM CET 
//


package it.polito.dp2.NFFG.sol3.service.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NffgVerifierType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NffgVerifierType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Nffgs" type="{}NffgsType" minOccurs="0"/>
 *         &lt;element name="Policies" type="{}PoliciesType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NffgVerifierType", propOrder = {
    "nffgs",
    "policies"
})
@XmlRootElement(name = "NffgVerifier")
public class NffgVerifierType {

    @XmlElement(name = "Nffgs")
    protected NffgsType nffgs;
    @XmlElement(name = "Policies")
    protected PoliciesType policies;

    /**
     * Recupera il valore della propriet� nffgs.
     * 
     * @return
     *     possible object is
     *     {@link NffgsType }
     *     
     */
    public NffgsType getNffgs() {
        return nffgs;
    }

    /**
     * Imposta il valore della propriet� nffgs.
     * 
     * @param value
     *     allowed object is
     *     {@link NffgsType }
     *     
     */
    public void setNffgs(NffgsType value) {
        this.nffgs = value;
    }

    /**
     * Recupera il valore della propriet� policies.
     * 
     * @return
     *     possible object is
     *     {@link PoliciesType }
     *     
     */
    public PoliciesType getPolicies() {
        return policies;
    }

    /**
     * Imposta il valore della propriet� policies.
     * 
     * @param value
     *     allowed object is
     *     {@link PoliciesType }
     *     
     */
    public void setPolicies(PoliciesType value) {
        this.policies = value;
    }

}
