
package it.polito.dp2.NFFG.sol3.client1.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ReachibilityPolicyType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ReachibilityPolicyType">
 *   &lt;complexContent>
 *     &lt;extension base="{}PolicyType">
 *       &lt;sequence>
 *         &lt;element name="SourceNode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DestinationNode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReachibilityPolicyType", propOrder = {
    "sourceNode",
    "destinationNode"
})
@XmlSeeAlso({
    TraversalPolicyType.class
})
public class ReachibilityPolicyType
    extends PolicyType
{

    @XmlElement(name = "SourceNode", required = true)
    protected String sourceNode;
    @XmlElement(name = "DestinationNode", required = true)
    protected String destinationNode;

    /**
     * Recupera il valore della proprietÓ sourceNode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceNode() {
        return sourceNode;
    }

    /**
     * Imposta il valore della proprietÓ sourceNode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceNode(String value) {
        this.sourceNode = value;
    }

    /**
     * Recupera il valore della proprietÓ destinationNode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationNode() {
        return destinationNode;
    }

    /**
     * Imposta il valore della proprietÓ destinationNode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationNode(String value) {
        this.destinationNode = value;
    }

}
