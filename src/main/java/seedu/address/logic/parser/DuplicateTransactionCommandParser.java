package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DuplicateTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DuplicateCommand object.
 */
public class DuplicateTransactionCommandParser implements Parser<DuplicateTransactionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DuplicateCommand
     * and returns a DuplicateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DuplicateTransactionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DuplicateTransactionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DuplicateTransactionCommand.MESSAGE_USAGE), pe);
        }
    }

}
