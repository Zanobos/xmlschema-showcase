
package it.polito.dp2.NFFG.sol3.client2.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TraversalPolicyType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TraversalPolicyType">
 *   &lt;complexContent>
 *     &lt;extension base="{}ReachibilityPolicyType">
 *       &lt;sequence>
 *         &lt;element name="Functionality" type="{}FuncType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TraversalPolicyType", propOrder = {
    "functionality"
})
public class TraversalPolicyType
    extends ReachibilityPolicyType
{

    @XmlElement(name = "Functionality", required = true)
    protected List<FuncType> functionality;

    /**
     * Gets the value of the functionality property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the functionality property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFunctionality().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FuncType }
     * 
     * 
     */
    public List<FuncType> getFunctionality() {
        if (functionality == null) {
            functionality = new ArrayList<FuncType>();
        }
        return this.functionality;
    }

}
