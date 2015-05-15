/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.input.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import ro.digidata.esop.input.exceptions.InvalidCommandException;

/**
 *
 * @author iulian.radulescu
 */
@Component
public class UserCommandFactory {
    
    @Resource
    private Map<String,UserCommand> commands = new HashMap<>( );
    
    public UserCommand getCommand( String commandName ) {
	UserCommand command = commands.get( commandName );
	if ( command == null ) {
	    throw new InvalidCommandException(String.format("Commanda [%s] este invalida!", commandName ));
	}
	
	return command;
    }
    
    public Set<String> getCommandSet( ) {
	return commands.keySet();
    }
}
