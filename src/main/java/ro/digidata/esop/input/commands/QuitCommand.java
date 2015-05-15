/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.input.commands;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import ro.digidata.esop.input.exceptions.QuitException;

/**
 *
 * @author iulian.radulescu
 */
@Component("quit")
public class QuitCommand extends UserCommand {

    @Override
    protected Map<String, Object> validate(String[] parameters) {
	//just ignore anuy text written after the command
	return new HashMap<>( );
    }

    @Override
    protected void execute(Map<String, Object> parametersMap) {
	throw new QuitException( );
    }
    
}
