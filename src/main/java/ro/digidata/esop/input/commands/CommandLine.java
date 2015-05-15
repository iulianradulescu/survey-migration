/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.digidata.esop.input.commands;

import java.util.Arrays;

/**
 *
 * @author radulescu
 */
public class CommandLine {
    private String command;
    
    private String[] parameters;
    
    public CommandLine( String commandLine ) {
	String[] elements = commandLine.split(" ");
	
	this.command = elements[0];
	if ( elements.length > 1 ) {
	    parameters = Arrays.copyOfRange(elements, 1, elements.length);
	}
    }
    
    public String getCommand( ) {
	return this.command;
    }
    public String[] getParameters( ) {
	return this.parameters;
    }
}
