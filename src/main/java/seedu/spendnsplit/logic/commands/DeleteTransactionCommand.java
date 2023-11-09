package seedu.spendnsplit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.transaction.Transaction;

/**
 * Deletes a transaction identified using it's displayed index from the app.
 */
public class DeleteTransactionCommand extends Command {

    public static final String COMMAND_WORD = "deleteTransaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the transaction identified by the index number used in the displayed transaction list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Deleted Transaction: %1$s";

    private final Index targetIndex;

    public DeleteTransactionCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTransaction(transactionToDelete);
        return new CommandResult(
            String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, Messages.format(transactionToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTransactionCommand)) {
            return false;
        }

        DeleteTransactionCommand otherDeleteTransactionCommand = (DeleteTransactionCommand) other;
        return targetIndex.equals(otherDeleteTransactionCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndex", targetIndex)
            .toString();
    }
}
