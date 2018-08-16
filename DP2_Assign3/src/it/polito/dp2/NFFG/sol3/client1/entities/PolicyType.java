
package it.polito.dp2.NFFG.sol3.client1.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PolicyType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PolicyType">
 *   &lt;complexContent>
 *     &lt;extension base="{}NamedEntityType">
 *       &lt;sequence>
 *         &lt;element name="Nffg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Result" type="{}VerificationResultType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="positive" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolicyType", propOrder = {
    "nffg",
    "result"
})
@XmlSeeAlso({
    ReachibilityPolicyType.class
})
public class PolicyType
    extends NamedEntityType
{

    @XmlElement(name = "Nffg", required = true)
    protected String nffg;
    @XmlElement(name = "Result")
    protected VerificationResultType result;
    @XmlAttribute(name = "positive", required = true)
    protected boolean positive;

    /**
     * Recupera il valore della proprietà nffg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNffg() {
        return nffg;
    }

    /**
     * Imposta il valore della proprietà nffg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNffg(String value) {
        this.nffg = value;
    }

    /**
     * Recupera il valore della proprietà result.
     * 
     * @return
     *     possible object is
     *     {@link VerificationResultType }
     *     
     */
    public VerificationResultType getResult() {
        return result;
    }

    /**
     * Imposta il valore della proprietà result.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificationResultType }
     *     
     */
    public void setResult(VerificationResultType value) {
        this.result = value;
    }

    /**
     * Recupera il valore della proprietà positive.
     * 
     */
    public boolean isPositive() {
        return positive;
    }

    /**
     * Imposta il valore della proprietà positive.
     * 
     */
    public void setPositive(boolean value) {
        this.positive = value;
    }

}
