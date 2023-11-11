package seedu.spendnsplit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.spendnsplit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.stream.Stream;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.logic.commands.UpdatePortionCommand;
import seedu.spendnsplit.logic.descriptors.PortionDescriptor;
import seedu.spendnsplit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdatePortionCommand object
 */
public class UpdatePortionCommandParser implements Parser<UpdatePortionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdatePortionCommand
     * and returns an UpdatePortionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdatePortionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_WEIGHT);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_WEIGHT);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_WEIGHT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdatePortionCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, UpdatePortionCommand.MESSAGE_USAGE), pe);
        }


        PortionDescriptor portionDescriptor = new PortionDescriptor();
        portionDescriptor.setPersonName(
                ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get()));
        portionDescriptor.setWeight(
                ParserUtil.parseZeroableWeight(argMultimap.getValue(CliSyntax.PREFIX_WEIGHT).get()));

        return new UpdatePortionCommand(index, portionDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
