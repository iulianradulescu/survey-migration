/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import ro.digidata.esop.domain.SMicrodata;

/**
 *
 * @author iulian.radulescu
 */
public interface SMicrodataRepository extends Repository<SMicrodata, Long> {

    public List<SMicrodata> findBySampleIdAndQuestTypeAndInstanceId(Long sample, String questType, Long instance);

    @Query("select count(distinct m.sample.id) from SMicrodata m where m.sample.survey.id=?1")
    public Long countAllQuestionnaires(Long surveyId);
    
    @Query("select count(distinct m.sample.id) from SMicrodata m where m.sample.survey.id=?1 and m.questType=?2")
    public Long countQuestionnaires(Long surveyId, String questType);
    
    @Query(value = "select count(*) from (select distinct sample_id, quest_inst_id from old_microdata m, "
            + "instance i where i.id=m.instance_id and i.survey_id=?1 and m.quest_type_id=?2)", nativeQuery=true)
    public Long countSubSampleQuestionnaires( Long surveyId, String questType );
    
    @Query("select count(m) from SMicrodata m where m.instance.survey.id=?1 and m.instance.period!=0")
    public Long countMicrodata(Long surveyId);
    
    public Long countByInstanceSurveyIdAndNonresponse(Long survey, int nonresponse );
    
    public Long countByInstanceSurveyIdAndNonresponseAndQuestType(Long survey, int nonresponse, String questType);
    
    @Query("select count(m) from SMicrodata m where m.instance.survey.id =?1 and (m.nonresponse=9 or (m.nonresponse is null and m.interview is null and CURRENT_DATE > m.instance.endDate))")
    public Long coundNonResponse9( Long survey );
    
    @Query("select count(m) from SMicrodata m where m.instance.survey.id =?1 and "
            + "(m.nonresponse=9 or (m.nonresponse is null and m.interview is null and m.questType='S3' and CURRENT_DATE > m.instance.endDate))")
    public Long coundNonResponse9S3( Long survey );
}
