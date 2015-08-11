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
public abstract class NoParameterCommand extends UserCommand {

    @Override
    public void execute(String[] parameters) {
       if ( parameters != null && parameters.length > 0 ) {
           throw  new InvalidCommandException(String.format("Invalid number of parameters. Expected 0 found %d", parameters.length));
       }
       
       doExecute( );
    }
    
    protected abstract void doExecute( );
}
