/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.input.commands;

import ro.digidata.esop.input.exceptions.InvalidCommandException;

/**
 *
 * @author iulian.radulescu
 */
public abstract class SurveyCommand extends UserCommand {

    public void execute(String[] parameters) {
        //it expects only one parameter, of type Long, as survey ID

        Long survey;
        if (parameters == null || parameters.length != 1) {
            throw new InvalidCommandException(String.format("Invalid number of parameters. Expected 1 found %d", parameters == null ? 0 : parameters.length));
        }

        try {
            survey = Long.parseLong(parameters[0]);
            if (survey <= 0) {
                throw new InvalidCommandException("Expected parameter should be a positive number!");
            }
        } catch (NumberFormatException exNFE) {
            throw new InvalidCommandException("Expected parameter should be a number!");
        }
        
        doExecute( survey );
    }
    
    protected abstract void doExecute( Long survey );
}
