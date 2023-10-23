package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.numbers.fraction.BigFraction;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Timestamp;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.model.transaction.expense.Weight;

/**
 * Edits the expense details of an existing transaction in the transaction list.
 */
public class UpdateExpenseCommand extends Command {

    public static final String COMMAND_WORD = "updateExpense";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Edits the expense details of the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_WEIGHT + "WEIGHT] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_WEIGHT + "65";

    // TODO: Message should also include details about the transaction
    public static final String MESSAGE_UPDATE_EXPENSE_SUCCESS = "Updated Expense: %1$s";
    public static final String MESSAGE_DELETE_EXPENSE_SUCCESS = "Deleted Expense: %1$s";
    public static final String MESSAGE_ADD_EXPENSE_SUCCESS = "Added Expense: %1$s";

    // TODO: add message for invalid pair of name and weight
    public static final String MESSAGE_EXPENSE_NOT_UPDATED = "At least one field must be provided.";

    private final Index index;

    private final UpdateExpenseDescriptor updateExpenseDescriptor;

    public UpdateExpenseCommand(Index index, UpdateExpenseDescriptor updateExpenseDescriptor) {
        requireNonNull(index);
        requireNonNull(updateExpenseDescriptor);

        this.index = index;
        this.updateExpenseDescriptor = new UpdateExpenseDescriptor(updateExpenseDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownTransactionList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownTransactionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToEdit = lastShownTransactionList.get(index.getZeroBased());
        Transaction transactionWithUpdatedExpenses =
                createTransactionWithUpdatedExpenses(transactionToEdit, updateExpenseDescriptor);

        model.setTransaction(transactionToEdit, transactionWithUpdatedExpenses);
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(
                String.format(MESSAGE_UPDATE_EXPENSE_SUCCESS, Messages.format(transactionWithUpdatedExpenses)));
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code updateExpenseDescriptor}.
     */
    private static Transaction createTransactionWithUpdatedExpenses(Transaction transactionToEdit,
            UpdateExpenseDescriptor updateExpenseDescriptor) {
        assert transactionToEdit != null;

        Amount existingAmount = transactionToEdit.getAmount();
        Description existingDescription = transactionToEdit.getDescription();
        Name existingPayeeName = transactionToEdit.getPayeeName();
        Timestamp existingTimestamp = transactionToEdit.getTimestamp();

        Set<Expense> newExpenses = transactionToEdit.getExpensesCopy();
        Name personName = updateExpenseDescriptor.getPersonName();
        Weight updatedWeight = updateExpenseDescriptor.getWeight();

        // remove existing expense, if exists
        newExpenses.removeIf(expense -> expense.getPersonName().equals(personName));

        if (!updatedWeight.value.equals(BigFraction.ZERO)) {
            newExpenses.add(new Expense(personName, updatedWeight));
        }

        return new Transaction(existingAmount, existingDescription, existingPayeeName, newExpenses, existingTimestamp);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateExpenseCommand)) {
            return false;
        }

        UpdateExpenseCommand otherUpdateExpenseCommand = (UpdateExpenseCommand) other;
        return index.equals(otherUpdateExpenseCommand.index)
                && updateExpenseDescriptor.equals(otherUpdateExpenseCommand.updateExpenseDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("updateExpenseDescriptor", updateExpenseDescriptor)
                .toString();
    }

    /**
     * Stores the details to update the expense with.
     * All fields are mandatory.
     */
    public static class UpdateExpenseDescriptor {
        private Name personName;
        private Weight weight;

        public UpdateExpenseDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code expenses} is used internally.
         */
        public UpdateExpenseDescriptor(UpdateExpenseDescriptor toCopy) {
            setPersonName(toCopy.personName);
            setWeight(toCopy.weight);
        }

        public void setPersonName(Name personName) {
            this.personName = personName;
        }

        public Name getPersonName() {
            return personName;
        }

        public void setWeight(Weight weight) {
            this.weight = weight;
        }

        public Weight getWeight() {
            return weight;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateExpenseDescriptor)) {
                return false;
            }

            UpdateExpenseDescriptor otherUpdateExpenseDescriptor = (UpdateExpenseDescriptor) other;
            return Objects.equals(personName, otherUpdateExpenseDescriptor.personName)
                    && Objects.equals(weight, otherUpdateExpenseDescriptor.weight);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("personName", personName)
                    .add("weight", weight)
                    .toString();
        }
    }
}
