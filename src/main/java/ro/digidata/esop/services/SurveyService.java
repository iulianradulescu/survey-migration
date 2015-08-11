/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.services;

import java.io.StringReader;
import org.w3c.dom.Element;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ro.digidata.esop.domain.Survey;
import ro.digidata.esop.repositories.SurveyRepository;
import ro.digidata.esop.services.model.SurveyClass;
import ro.digidata.esop.services.model.SurveyInfo;

/**
 *
 * @author iulian.radulescu
 */
@Service
public class SurveyService {
    
    @Autowired
    private SurveyRepository surveyRepository;
    
    public SurveyInfo surveyInfo( Long survey ) {
        
        Survey s =surveyRepository.findOne( survey );
        
        if ( s== null ) {
            //invalid survey id
        }
        
        String mnemonic = s.getTemplate().getMnemonic();
        SurveyInfo sInfo = new SurveyInfo(survey, SurveyClass.classOf( mnemonic ), mnemonic );
        //read the questionnaires and get their titles
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList nodes = (NodeList) xpath.evaluate(String.format("//questionnaires/questionnaire"), new InputSource(new StringReader(s.getScript())), XPathConstants.NODESET);

            for (int index = 0; index < nodes.getLength(); index++) {
                Element elem = (Element) nodes.item(index);
                //each element is a questionnaire; seach within the child nodes, one called title
                NodeList children = elem.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    if (children.item(i).getNodeName().equals("title")) {
                        sInfo.addQuestionnaire(elem.getAttribute("mnemonic"), ((Element) children.item(i)).getTextContent());
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sInfo;
    }
}
