package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Transaction;

/**
 * Duplicates a transaction identified using it's displayed index from the address book.
 */
public class DuplicateTransactionCommand extends Command {

    public static final String COMMAND_WORD = "duplicateTransaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Duplicates the transaction identified by the index number used in the displayed transaction list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DUPLICATE_TRANSACTION_SUCCESS = "Duplicated Transaction: %1$s";

    private final Index targetIndex;

    public DuplicateTransactionCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transaction = lastShownList.get(targetIndex.getZeroBased());

        // Use the same information as the original transaction, except for the timestamp to be the current time
        Transaction transactionToDuplicate = new Transaction(transaction.getAmount(), transaction.getDescription(),
            transaction.getPayeeName(), transaction.getPortions());

        model.addTransaction(transactionToDuplicate);
        return new CommandResult(
            String.format(MESSAGE_DUPLICATE_TRANSACTION_SUCCESS, Messages.format(transactionToDuplicate, false)));
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
        return targetIndex.equals(otherDuplicateTransactionCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndex", targetIndex)
            .toString();
    }
}
