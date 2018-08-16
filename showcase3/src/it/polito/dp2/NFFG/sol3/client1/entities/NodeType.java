
package it.polito.dp2.NFFG.sol3.client1.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NodeType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NodeType">
 *   &lt;complexContent>
 *     &lt;extension base="{}NamedEntityType">
 *       &lt;sequence>
 *         &lt;element name="Functionality" type="{}FuncType"/>
 *         &lt;element name="Link" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodeType", propOrder = {
    "functionality",
    "link"
})
public class NodeType
    extends NamedEntityType
{

    @XmlElement(name = "Functionality", required = true)
    protected FuncType functionality;
    @XmlElement(name = "Link")
    protected List<String> link;

    /**
     * Recupera il valore della proprietà functionality.
     * 
     * @return
     *     possible object is
     *     {@link FuncType }
     *     
     */
    public FuncType getFunctionality() {
        return functionality;
    }

    /**
     * Imposta il valore della proprietà functionality.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncType }
     *     
     */
    public void setFunctionality(FuncType value) {
        this.functionality = value;
    }

    /**
     * Gets the value of the link property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the link property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLink() {
        if (link == null) {
            link = new ArrayList<String>();
        }
        return this.link;
    }

}
