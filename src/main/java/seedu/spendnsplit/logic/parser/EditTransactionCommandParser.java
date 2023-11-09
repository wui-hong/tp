package seedu.spendnsplit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.spendnsplit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.logic.commands.EditTransactionCommand;
import seedu.spendnsplit.logic.descriptors.TransactionDescriptor;
import seedu.spendnsplit.logic.parser.exceptions.ParseException;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COST, PREFIX_DESCRIPTION, PREFIX_NAME, PREFIX_TIMESTAMP);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE), pe);
        }

        TransactionDescriptor transactionDescriptor = getEditTransactionDescriptor(argMultimap);

        if (!transactionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTransactionCommand.MESSAGE_TRANSACTION_NOT_EDITED);
        }

        return new EditTransactionCommand(index, transactionDescriptor);
    }

    public static TransactionDescriptor getEditTransactionDescriptor(
        ArgumentMultimap argMultimap) throws ParseException {
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COST, PREFIX_DESCRIPTION, PREFIX_NAME, PREFIX_TIMESTAMP);

        TransactionDescriptor transactionDescriptor = new TransactionDescriptor();

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            transactionDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            transactionDescriptor.setAmount(
                    ParserUtil.parseAmount(argMultimap.getValue(PREFIX_COST).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            transactionDescriptor.setPayeeName(
                    ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_TIMESTAMP).isPresent()) {
            transactionDescriptor.setTimestamp(
                    ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_TIMESTAMP).get()));
        }
        return transactionDescriptor;
    }
}
