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
public class QuitCommand extends NoParameterCommand {
    
    @Override
    protected void doExecute() {
        throw new QuitException( );
    }  
}
