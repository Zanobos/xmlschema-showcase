
package it.polito.dp2.NFFG.sol3.client1.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per FuncType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FuncType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="func" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FuncType")
public class FuncType {

    @XmlAttribute(name = "func", required = true)
    protected String func;

    /**
     * Recupera il valore della proprietà func.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunc() {
        return func;
    }

    /**
     * Imposta il valore della proprietà func.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunc(String value) {
        this.func = value;
    }

}
