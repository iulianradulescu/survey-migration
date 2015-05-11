package ro.digidata.esop.input.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.digidata.esop.input.exceptions.InvalidCommandException;

/**
 * Base class for user commands
 *
 * @author radulescu
 */
public abstract class UserCommand {

    protected Logger logger = LoggerFactory.getLogger(UserCommand.class);

    public UserCommand() {
    }

    public void execute(String[] parameters) {
        try {
            //validate the command
            doValidate( );

            //execute the command
            doExecute();
        } catch (InvalidCommandException exICE) {
            //show a message and re run the hadnleInput method
            logger.error(exICE.getMessage());
        }
    }

    protected abstract void doValidate();

    protected abstract void doExecute();
}
