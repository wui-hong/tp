package seedu.spendnsplit.logic.parser;

import static seedu.spendnsplit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.spendnsplit.logic.commands.SortPersonCommand;
import seedu.spendnsplit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortPersonCommand object
 */
public class SortPersonCommandParser implements Parser<SortPersonCommand> {

    @Override
    public SortPersonCommand parse(String args) throws ParseException {
        switch (args.trim()) {
        case "+":
            return new SortPersonCommand(true);
        case "-":
            return new SortPersonCommand(false);
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE));
        }
    }
}
