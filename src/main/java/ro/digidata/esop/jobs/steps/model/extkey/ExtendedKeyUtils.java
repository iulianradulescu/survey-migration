/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.model.extkey;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author iulian.radulescu
 */
public final class ExtendedKeyUtils {

    private ExtendedKeyUtils() {
    }

    public static String marshallExtendedKey(ExtKey extendedKey) {
        if (extendedKey == null) {
            return null;
        }

        try {
            JAXBContext jc = JAXBContext.newInstance("ro.digidata.esop.jobs.steps.model.extkey");
            Marshaller m = jc.createMarshaller();

            StringWriter writer = new StringWriter();
            m.marshal(extendedKey, writer);
            return writer.toString();
        } catch (JAXBException exJAXBE) {
            System.out.println(String.format("ERROR: unable to marshal with the error %s", exJAXBE.getMessage()));
        }

        return null;
    }
    
     public static ExtKey unmarshallExtendedKey(String extendedKey) {
         try {
            JAXBContext jc = JAXBContext.newInstance("ro.digidata.esop.jobs.steps.model.extkeyd");
            Unmarshaller um = jc.createUnmarshaller();

            return (ExtKey) um.unmarshal(new StringReader(extendedKey));
        } catch (JAXBException exJAXBE) {
            System.out.println(String.format("ERROR: unable to unmarshall with the error %s", exJAXBE.getMessage()));
        }

        return null;
    }
}
