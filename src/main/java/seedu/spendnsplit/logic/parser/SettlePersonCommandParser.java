package seedu.spendnsplit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.spendnsplit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.logic.commands.SettlePersonCommand;
import seedu.spendnsplit.logic.parser.exceptions.ParseException;
import seedu.spendnsplit.model.transaction.Timestamp;

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
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TIMESTAMP);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, SettlePersonCommand.MESSAGE_USAGE), pe);
        }

        Timestamp time = Timestamp.now();

        if (argMultimap.getValue(PREFIX_TIMESTAMP).isPresent()) {
            time = ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_TIMESTAMP).get(), "23:59");
        }

        return new SettlePersonCommand(index, time);

    }

}
