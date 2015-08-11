/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.input.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author radulescu
 */
@Component("list")
public class ListCommand extends NoParameterCommand {

    @Autowired
    private UserCommandFactory factory;

    @Override
    protected void doExecute() {
        //command execution consists in printing the list of available commands
        Set<String> commands = factory.getCommandSet();

        StringBuilder builder = new StringBuilder("Comenzi disponibile:");

        for (String command : commands) {
            builder.append("\n\t").append(command);
        }

        System.out.println(builder.toString());
    }
}
