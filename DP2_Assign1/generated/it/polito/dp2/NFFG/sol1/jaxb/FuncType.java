//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2016.12.31 alle 07:24:28 PM CET 
//


package it.polito.dp2.NFFG.sol1.jaxb;

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
 *       &lt;attribute name="func" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="CACHE"/>
 *             &lt;enumeration value="DPI"/>
 *             &lt;enumeration value="FW"/>
 *             &lt;enumeration value="MAIL_CLIENT"/>
 *             &lt;enumeration value="MAIL_SERVER"/>
 *             &lt;enumeration value="NAT"/>
 *             &lt;enumeration value="SPAM"/>
 *             &lt;enumeration value="VPN"/>
 *             &lt;enumeration value="WEB_CLIENT"/>
 *             &lt;enumeration value="WEB_SERVER"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
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
