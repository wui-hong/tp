package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SettlePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SettlePersonCommand object
 */
public class SettlePersonCommandParser implements Parser<SettlePersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SettlePersonCommand
     * and returns a SettlePersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SettlePersonCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SettlePersonCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettlePersonCommand.MESSAGE_USAGE), pe);
        }
    }

}
