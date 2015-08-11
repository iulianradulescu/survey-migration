/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.processors;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ro.digidata.esop.domain.Questionnaire;
import ro.digidata.esop.domain.ReportingUnitUser;
import ro.digidata.esop.domain.SMicrodata;
import ro.digidata.esop.domain.TMicrodata;
import ro.digidata.esop.domain.enums.QuestionnaireStatus;
import ro.digidata.esop.jobs.steps.model.MicrodataMigrationProcessorOutput;
import ro.digidata.esop.jobs.steps.model.extkey.ExtKey;
import ro.digidata.esop.jobs.steps.model.extkey.ExtendedKeyUtils;
import ro.digidata.esop.repositories.SMicrodataRepository;

/**
 *
 * @author iulian.radulescu
 */
public class S3MicrodataMigrationProcessor extends MicrodataMigrationProcessor {

    @Autowired
    private SMicrodataRepository smicrodataRepository;
    
    public S3MicrodataMigrationProcessor(Long survey) {
        super(survey);
    }
    
    public MicrodataMigrationProcessorOutput process(SMicrodata record) throws Exception {
        MicrodataMigrationProcessorOutput output = super.process( record );
        
        List<SMicrodata> s3AnexaForms = smicrodataRepository.findBySampleIdAndQuestTypeAndInstanceId(record.getSample().getId( ), "S3_ANEXA", record.getInstance( ).getId( ) );

        TMicrodata s3Microdata = output.getMicrodata().get( 0 );
        for (SMicrodata s3AnexaForm : s3AnexaForms ) {
            //now use the code to create the extended key
            ExtKey extKey = new ExtKey();
            extKey.addAttribute("VJUDET", s3AnexaForm.getQuestInst( ) );

            //marshall it
            String extendedKey = ExtendedKeyUtils.marshallExtendedKey(extKey);

            //now create a questionnaire with it; there is no extra check needed, there is only one instance for S3 so we insert it directly
            Questionnaire quest = new Questionnaire( );

            quest.setQuestType("S3_ANEXA");
            quest.setSample( s3Microdata.getQuestionnaire().getSample( ) );
            quest.setExtendedKey( extendedKey );
            quest.setTitle(sInfo.getQuestTitle("S3_ANEXA"));
            quest.setStatus( QuestionnaireStatus.ACTIV );

            //now handle authorization for this questionnaire
            //make a Record object to benefit from the already existing implementation
            if (s3AnexaForm.getRespondent() != null) {
                ReportingUnitUser ruu = processAuthorization(quest, s3AnexaForm.getRespondent( ) );
                if ( ruu != null ) {
                    output.addReportingUnitUser( ruu );
                }
            }
            
            TMicrodata s3AnexaMicrodata = processMicrodata(record, quest);
            output.addMicrodata( s3AnexaMicrodata );
        }
        return output;
    }
    
}
