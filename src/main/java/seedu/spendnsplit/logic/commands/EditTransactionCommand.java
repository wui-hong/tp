package seedu.spendnsplit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.List;
import java.util.Set;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.logic.descriptors.TransactionDescriptor;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.Amount;
import seedu.spendnsplit.model.transaction.Description;
import seedu.spendnsplit.model.transaction.Timestamp;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.model.transaction.portion.Portion;

/**
 * Edits the details of an existing transaction in the transaction list.
 * i.e. cost, description, payeeName
 * Note: Editing of expenses is done via the {@code UpdatePortionCommand}.
 */
public class EditTransactionCommand extends Command {

    public static final String COMMAND_WORD = "editTransaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_COST + "COST] "
            + "[" + PREFIX_DESCRIPTION + "DETAILS] "
            + "[" + PREFIX_TIMESTAMP + "TIME] "
            + "[" + PREFIX_NAME + "PAYEE NAME] "
            + "[" + PREFIX_TIMESTAMP + "TIMESTAMP]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_COST + "10.00 "
            + PREFIX_DESCRIPTION + "Bought a book "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TIMESTAMP + "10/10/2020 12:00";

    public static final String MESSAGE_EDIT_TRANSACTION_SUCCESS = "Edited Transaction: %1$s";

    public static final String MESSAGE_TRANSACTION_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_DUPLICATE_TRANSACTION =
            "The edited transaction already exists in the app";
    public static final String MESSAGE_UNKNOWN_PAYEE = "The payee must either be you or someone in the person list";
    public static final String MESSAGE_TRANSACTION_NOT_RELEVANT =
            "The edited transaction does not affect your balances. Please use the delete command instead.";

    private final Index index;

    private final TransactionDescriptor transactionDescriptor;

    /**
     * @param index of the transaction in the filtered transaction list to edit
     * @param transactionDescriptor details to edit the transaction with
     */
    public EditTransactionCommand(Index index, TransactionDescriptor transactionDescriptor) {
        requireNonNull(index);
        requireNonNull(transactionDescriptor);

        this.index = index;
        this.transactionDescriptor = new TransactionDescriptor(transactionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownTransactionList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownTransactionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToEdit = lastShownTransactionList.get(index.getZeroBased());
        Transaction editedTransaction = createEditedTransaction(transactionToEdit, transactionDescriptor);

        if (!editedTransaction.isRelevant()) {
            throw new CommandException(MESSAGE_TRANSACTION_NOT_RELEVANT);
        }

        if (!(editedTransaction.getPayeeName().equals(Name.SELF)
                || model.getAllNames().contains(editedTransaction.getPayeeName()))) {
            throw new CommandException(MESSAGE_UNKNOWN_PAYEE);
        }

        if (model.hasTransaction(editedTransaction)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSACTION);
        }

        model.setTransaction(transactionToEdit, editedTransaction);
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(
                String.format(MESSAGE_EDIT_TRANSACTION_SUCCESS, Messages.format(editedTransaction)));

    }
    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code transactionDescriptor}.
     * Portions are not edited with this {@code EditTransactionCommand}
     */
    public static Transaction createEditedTransaction(Transaction transactionToEdit, TransactionDescriptor
            transactionDescriptor) {
        assert transactionToEdit != null;

        Amount updatedAmount = transactionDescriptor.getAmount().orElse(transactionToEdit.getAmount());
        Description updatedDescription = transactionDescriptor.getDescription().orElse(transactionToEdit
                .getDescription());
        Name updatedPayeeName = transactionDescriptor.getPayeeName().orElse(transactionToEdit.getPayeeName());
        Timestamp updatedTimestamp = transactionDescriptor.getTimestamp().orElse(transactionToEdit
                .getTimestamp());


        Set<Portion> existingPortions = transactionToEdit.getPortions();

        return new Transaction(updatedAmount, updatedDescription, updatedPayeeName, existingPortions, updatedTimestamp);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTransactionCommand)) {
            return false;
        }

        EditTransactionCommand otherEditTransactionCommand = (EditTransactionCommand) other;
        return index.equals(otherEditTransactionCommand.index)
                && transactionDescriptor.equals(otherEditTransactionCommand.transactionDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("transactionDescriptor", transactionDescriptor)
                .toString();
    }
}
