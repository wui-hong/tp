package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTransactionCommand;
import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTransactionCommand object
 */
public class EditTransactionCommandParser implements Parser<EditTransactionCommand> {

    /**
    * Parses the given {@code String} of arguments in the context of the EditTransactionCommand
    * and returns an EditTransactionCommand object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public EditTransactionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        // TODO: tokenize prefix for portion names and weights for v1.2b
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COST, PREFIX_DESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COST, PREFIX_DESCRIPTION);

        EditTransactionDescriptor editTransactionDescriptor = new EditTransactionDescriptor();

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editTransactionDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            editTransactionDescriptor.setAmount(
                    ParserUtil.parseAmount(argMultimap.getValue(PREFIX_COST).get()));
        }
        // TODO: parseExpensesForEdit

        if (!editTransactionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTransactionCommand.MESSAGE_TRANSACTION_NOT_EDITED);
        }

        return new EditTransactionCommand(index, editTransactionDescriptor);
    }
}

// TODO: parseExpensesForEdit
