/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.jobs.steps.tasklets;

import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author iulian.radulescu
 */
@Configuration
public class TaskletConfig {

    @Bean
    public Tasklet validateMigrationTasklet() {
        return new ValidateSurveyMigration();
    }
}
