//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.10 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2017.01.01 alle 11:21:21 AM CET 
//


package it.polito.dp2.NFFG.sol3.service.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per VerificationResultType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VerificationResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VerificationResult" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="VerificationResultMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VerificationTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="policy" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerificationResultType", propOrder = {
    "verificationResult",
    "verificationResultMsg",
    "verificationTime"
})
@XmlRootElement(name = "VerificationResult")
public class VerificationResultType {

    @XmlElement(name = "VerificationResult")
    protected Boolean verificationResult;
    @XmlElement(name = "VerificationResultMsg")
    protected String verificationResultMsg;
    @XmlElement(name = "VerificationTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar verificationTime;
    @XmlAttribute(name = "policy", required = true)
    protected String policy;

    /**
     * Recupera il valore della proprietà verificationResult.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVerificationResult() {
        return verificationResult;
    }

    /**
     * Imposta il valore della proprietà verificationResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVerificationResult(Boolean value) {
        this.verificationResult = value;
    }

    /**
     * Recupera il valore della proprietà verificationResultMsg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerificationResultMsg() {
        return verificationResultMsg;
    }

    /**
     * Imposta il valore della proprietà verificationResultMsg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerificationResultMsg(String value) {
        this.verificationResultMsg = value;
    }

    /**
     * Recupera il valore della proprietà verificationTime.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVerificationTime() {
        return verificationTime;
    }

    /**
     * Imposta il valore della proprietà verificationTime.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVerificationTime(XMLGregorianCalendar value) {
        this.verificationTime = value;
    }

    /**
     * Recupera il valore della proprietà policy.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicy() {
        return policy;
    }

    /**
     * Imposta il valore della proprietà policy.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicy(String value) {
        this.policy = value;
    }

}
