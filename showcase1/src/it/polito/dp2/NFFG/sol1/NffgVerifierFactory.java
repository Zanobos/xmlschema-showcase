/*
 *  author a.zanotti
 */
package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.NffgVerifier;
import it.polito.dp2.NFFG.NffgVerifierException;
import it.polito.dp2.NFFG.sol1.jaxb.NffgVerifierType;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author a.zanotti
 */
public class NffgVerifierFactory extends it.polito.dp2.NFFG.NffgVerifierFactory {

    @Override
    public NffgVerifier newNffgVerifier() throws NffgVerifierException {
        
        String inputFileString = System.getProperty("it.polito.dp2.NFFG.sol1.NffgInfo.file");
        
        if(inputFileString == null || inputFileString.isEmpty()) {
            throw new NffgVerifierException("Input file name null or empty");
        }
        
        File inputFile = null;
        try {
            inputFile = new File(inputFileString);
        } catch (NullPointerException e) {
            throw new NffgVerifierException(e, "Input file does not exist");
        }
            
        NffgVerifierType nffgVerifierType = null;
        try {
            JAXBContext jc = JAXBContext.newInstance("it.polito.dp2.NFFG.sol1.jaxb");
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement jAXBElement = (JAXBElement) unmarshaller.unmarshal(inputFile);
            nffgVerifierType = (NffgVerifierType) jAXBElement.getValue();

        } catch (JAXBException ex) {
            throw new NffgVerifierException(ex, "Error in unmarshalling");
        }

        return new NffgVerifierImpl(nffgVerifierType);
    }

}
