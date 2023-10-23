package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
