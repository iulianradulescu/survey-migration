/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.services.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author iulian.radulescu
 */
public class SurveyInfo {

    private long id;
    private SurveyClass sclass;
    private String mnemonic;

    private Map<String,String> questionnaires;

    public SurveyInfo(long id, SurveyClass sclass, String mnemonic) {
        this.id = id;
        this.sclass = sclass == null ? SurveyClass.SIMPLE : sclass;
        this.mnemonic = mnemonic;
        
        this.questionnaires=new HashMap<String,String>( );
    }

    public long id() {
        return id;
    }

    public SurveyClass sClass() {
        return sclass;
    }

    public String mnemonic() {
        return mnemonic;
    }
    
    public void addQuestionnaire( String mnemonic, String title ) {
        questionnaires.put(mnemonic, title);
    }
    
    public String getQuestTitle( String mnemonic ) {
        return questionnaires.get( mnemonic );
    }
    
    public int getNoOfQuestionnaires( ) {
        return questionnaires.keySet().size();
    }
    
    public Set<String> getQuestionnaires( ) {
        return questionnaires.keySet( );
    }
}
