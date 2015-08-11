package ro.digidata.esop.input.commands;

import java.util.Map;
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

    public UserCommand( ) {
    }

    public abstract void execute( String[] parameters ); /** {
        try {
            //validate the command parameters
            Map<String, Object> parametersMap = validate( parameters );

            //execute the command
            execute( parametersMap );
        } catch (InvalidCommandException exICE) {
            //show a message and re run the hadnleInput method
            logger.error(exICE.getMessage());
        }
    }

    protected abstract Map<String,Object> validate( String[] parameters );

    protected abstract void execute( Map<String, Object> parametersMap );**/
}
