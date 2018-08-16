//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.10 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2017.01.01 alle 11:21:21 AM CET 
//


package it.polito.dp2.NFFG.sol3.service.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per VerificationResultsType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="VerificationResultsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VerificationResult" type="{}VerificationResultType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VerificationResultsType", propOrder = {
    "verificationResult"
})
@XmlRootElement(name = "VerificationResults")
public class VerificationResultsType {

    @XmlElement(name = "VerificationResult", required = true)
    protected List<VerificationResultType> verificationResult;

    /**
     * Gets the value of the verificationResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the verificationResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVerificationResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VerificationResultType }
     * 
     * 
     */
    public List<VerificationResultType> getVerificationResult() {
        if (verificationResult == null) {
            verificationResult = new ArrayList<VerificationResultType>();
        }
        return this.verificationResult;
    }

}
