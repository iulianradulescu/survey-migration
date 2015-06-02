/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.model.authorization;

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
public final class AuthorizationUtils {

    private AuthorizationUtils() {
    }

    public static Authorization unmarshallAuthorization(String xml) {
        try {
            JAXBContext jc = JAXBContext.newInstance("ro.digidata.esop.model.authorization");
            Unmarshaller um = jc.createUnmarshaller();

            return (Authorization) um.unmarshal(new StringReader(xml));
        } catch (JAXBException exJAXBE) {
            System.out.println(String.format("ERROR: unable to unmarshall with the error %s", exJAXBE.getMessage()));
        }

        return null;
    }

    public static String marshallAuthorization(Authorization authorization) {
        if (authorization == null) {
            return null;
        }

        try {
            JAXBContext jc = JAXBContext.newInstance("ro.digidata.esop.model.authorization");
            Marshaller m = jc.createMarshaller();

            StringWriter writer = new StringWriter();
            m.marshal(authorization, writer);
            return writer.toString();
        } catch (JAXBException exJAXBE) {
            System.out.println(String.format("ERROR: unable to marshal with the error %s", exJAXBE.getMessage()));
        }

        return null;
    }

    public static boolean removeAuthorizationForSurvey(Authorization auth, int surveyId) {
        //to mark that the corresponding record has been updated
        boolean updated = false;
        Iterator<UnitType> unitIterator = auth.getUnit().iterator();

        while (unitIterator.hasNext()) {
            UnitType unit = unitIterator.next();
            Iterator<SurveyType> surveyIterator = unit.getSurvey().iterator();

            while (surveyIterator.hasNext()) {
                SurveyType survey = surveyIterator.next();

                if (survey.getId().intValue() == surveyId) {
                    updated = true;
                    surveyIterator.remove();
                }
            }

            //if there are no surveys,remove the unit also
            if (unit.getSurvey().isEmpty()) {
                unitIterator.remove();
            }
        }

        return updated;
    }
}
