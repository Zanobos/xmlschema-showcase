
package it.polito.dp2.NFFG.sol3.client2.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element ref="{}Nffgs" minOccurs="0"/>
 *         &lt;element ref="{}Policies" minOccurs="0"/>
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
public class NffgVerifierType {

    @XmlElement(name = "Nffgs")
    protected NffgsType nffgs;
    @XmlElement(name = "Policies")
    protected PoliciesType policies;

    /**
     * Recupera il valore della proprietà nffgs.
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
     * Imposta il valore della proprietà nffgs.
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
     * Recupera il valore della proprietà policies.
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
     * Imposta il valore della proprietà policies.
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
