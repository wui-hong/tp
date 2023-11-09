package seedu.spendnsplit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.List;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.logic.descriptors.TransactionDescriptor;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.Transaction;

/**
 * Duplicates a transaction identified using its displayed index from the app.
 * Optional edits can be made to the duplicated transaction.
 * Timestamp will be set to the current time if not specified.
 */
public class DuplicateTransactionCommand extends Command {

    public static final String COMMAND_WORD = "duplicateTransaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Duplicates the transaction identified by the index number with optional edits. "
        + "Timestamp will be set to the current time if not specified.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_COST + "COST] "
        + "[" + PREFIX_DESCRIPTION + "DETAILS] "
        + "[" + PREFIX_NAME + "PAYEE NAME] "
        + "[" + PREFIX_TIMESTAMP + "TIMESTAMP]\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_COST + "10.00 "
        + PREFIX_DESCRIPTION + "Bought a book "
        + PREFIX_NAME + "John Doe "
        + PREFIX_TIMESTAMP + "2020-01-01 12:00";

    public static final String MESSAGE_DUPLICATE_TRANSACTION_SUCCESS = "New duplicated transaction added: %1$s";

    public static final String MESSAGE_DUPLICATE_TRANSACTION =
            "The updated transaction with the same timestamp already exists in the app";
    public static final String MESSAGE_UNKNOWN_PAYEE = "The payee must either be you or someone in the person list";
    public static final String MESSAGE_TRANSACTION_NOT_RELEVANT =
            "The duplicated transaction does not affect your balances";

    private final Index targetIndex;
    private final TransactionDescriptor transactionDescriptor;

    /**
     * @param targetIndex                    of the transaction in the filtered transaction list to duplicate
     * @param transactionDescriptor          details to edit the duplicated transaction with
     */
    public DuplicateTransactionCommand(Index targetIndex, TransactionDescriptor transactionDescriptor) {
        this.targetIndex = targetIndex;
        this.transactionDescriptor = transactionDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownTransactionList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownTransactionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToDuplicate = lastShownTransactionList.get(targetIndex.getZeroBased());
        Transaction duplicateTransaction = EditTransactionCommand.createEditedTransaction(
            transactionToDuplicate, transactionDescriptor);

        if (!duplicateTransaction.isRelevant()) {
            throw new CommandException(MESSAGE_TRANSACTION_NOT_RELEVANT);
        }

        if (!(duplicateTransaction.getPayeeName().equals(Name.SELF)
                || model.getAllNames().contains(duplicateTransaction.getPayeeName()))) {
            throw new CommandException(MESSAGE_UNKNOWN_PAYEE);
        }

        if (model.hasTransaction(duplicateTransaction)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSACTION);
        }

        model.addTransaction(duplicateTransaction);
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(
            String.format(MESSAGE_DUPLICATE_TRANSACTION_SUCCESS, Messages.format(duplicateTransaction)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DuplicateTransactionCommand)) {
            return false;
        }

        DuplicateTransactionCommand otherDuplicateTransactionCommand = (DuplicateTransactionCommand) other;
        return targetIndex.equals(otherDuplicateTransactionCommand.targetIndex)
            && transactionDescriptor.equals(otherDuplicateTransactionCommand.transactionDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndex", targetIndex)
            .add("transactionDescriptor", transactionDescriptor)
            .toString();
    }
}
