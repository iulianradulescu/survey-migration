/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ro.digidata.esop.domain.Questionnaire;
import ro.digidata.esop.domain.enums.QuestionnaireStatus;

/**
 *
 * @author radulescu
 */
public interface QuestionnaireRepository extends CrudRepository<Questionnaire, Long> {
    
    Questionnaire findBySampleIdAndQuestType(Long sample, String questType);
    
    Questionnaire findBySampleIdAndStatus( Long sample, QuestionnaireStatus status);
    
    Long countBySampleSurvey(Long surveyId);
    
    @Query("select count(q) from Questionnaire q where q.sample.survey=?1 and q.questType=?2")
    Long countQuestionnaires(Long surveyId, String questType);
}
