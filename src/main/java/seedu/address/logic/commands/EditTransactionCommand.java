package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.CollectionUtil.requireNonEmptyCollection;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
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

/**
 * Edits the details of an existing transaction in the transaction list.
 * i.e. cost, description, payeeName
 * Note: Editing of expenses is done via the {@code EditExpenseCommand}.
 */
public class EditTransactionCommand extends Command {

    public static final String COMMAND_WORD = "editTransaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_COST + "COST] "
            + "[" + PREFIX_DESCRIPTION + "DETAILS] "
            + "[" + PREFIX_NAME + "PAYEE NAME] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_COST + "10.00 "
            + PREFIX_DESCRIPTION + "Bought a book"
            + PREFIX_NAME + "John Doe";

    public static final String MESSAGE_EDIT_TRANSACTION_SUCCESS = "Edited Transaction: %1$s";

    public static final String MESSAGE_TRANSACTION_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;

    private final EditTransactionDescriptor editTransactionDescriptor;

    /**
     * @param index of the transaction in the filtered transaction list to edit
     * @param editTransactionDescriptor details to edit the transaction with
     */
    public EditTransactionCommand(Index index, EditTransactionDescriptor editTransactionDescriptor) {
        requireNonNull(index);
        requireNonNull(editTransactionDescriptor);

        this.index = index;
        this.editTransactionDescriptor = new EditTransactionDescriptor(editTransactionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownTransactionList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownTransactionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToEdit = lastShownTransactionList.get(index.getZeroBased());
        Transaction editedTransaction = createEditedTransaction(transactionToEdit, editTransactionDescriptor);

        model.setTransaction(transactionToEdit, editedTransaction);
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(
                String.format(MESSAGE_EDIT_TRANSACTION_SUCCESS, Messages.format(editedTransaction)));

    }
    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code editTransactionDescriptor}.
     */
    private static Transaction createEditedTransaction(Transaction transactionToEdit, EditTransactionDescriptor
            editTransactionDescriptor) {
        assert transactionToEdit != null;

        Amount updatedAmount = editTransactionDescriptor.getAmount().orElse(transactionToEdit.getAmount());
        Description updatedDescription = editTransactionDescriptor.getDescription().orElse(transactionToEdit
                .getDescription());
        Name updatedPayeeName = editTransactionDescriptor.getPayeeName().orElse(transactionToEdit.getPayeeName());
        Set<Expense> updatedExpenses = editTransactionDescriptor.getExpenses().orElse(transactionToEdit
                .getExpenses());

        // Timestamp is edited here for testing purposes
        Timestamp updatedTimestamp = editTransactionDescriptor.getTimestamp().orElse(transactionToEdit
                .getTimestamp());

        return new Transaction(updatedAmount, updatedDescription, updatedPayeeName, updatedExpenses, updatedTimestamp);
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
                && editTransactionDescriptor.equals(otherEditTransactionCommand.editTransactionDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editTransactionDescriptor", editTransactionDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     * Note that "cost" is represented by {@code Amount} named {@code amount} in the model.
     */
    public static class EditTransactionDescriptor {
        private Amount amount;
        private Description description;
        private Name payeeName;
        private Set<Expense> expenses;
        private Timestamp timestamp;

        public EditTransactionDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code expenses} is used internally.
         */
        public EditTransactionDescriptor(EditTransactionDescriptor toCopy) {
            setAmount(toCopy.amount);
            setDescription(toCopy.description);
            setPayeeName(toCopy.payeeName);
            setTimestamp(toCopy.timestamp);

            if (toCopy.expenses != null) {
                setExpenses(toCopy.expenses);
            }
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(amount, description, payeeName, timestamp, expenses);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setPayeeName(Name payeeName) {
            this.payeeName = payeeName;
        }

        public Optional<Name> getPayeeName() {
            return Optional.ofNullable(payeeName);
        }

        /**
         * Sets {@code expenses} to this object's {@code expenses}.
         * A defensive copy of {@code expenses} is used internally.
         */
        public void setExpenses(Set<Expense> expenses) {
            if (!Objects.isNull(expenses)) {
                requireNonEmptyCollection(expenses);
                requireAllNonNull(expenses);
                this.expenses = new HashSet<>(expenses);
            }
            this.expenses = null;
        }

        /**
         * Returns an unmodifiable expense set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         */
        public Optional<Set<Expense>> getExpenses() {
            return (expenses != null) ? Optional.of(Collections.unmodifiableSet(expenses)) : Optional.empty();
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public Optional<Timestamp> getTimestamp() {
            return Optional.ofNullable(timestamp);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTransactionDescriptor)) {
                return false;
            }

            EditTransactionDescriptor otherEditTransactionDescriptor = (EditTransactionDescriptor) other;
            return Objects.equals(amount, otherEditTransactionDescriptor.amount)
                    && Objects.equals(description, otherEditTransactionDescriptor.description)
                    && Objects.equals(payeeName, otherEditTransactionDescriptor.payeeName)
                    && Objects.equals(expenses, otherEditTransactionDescriptor.expenses)
                    && Objects.equals(timestamp, otherEditTransactionDescriptor.timestamp);
        }

        /**
         * Note that "cost" is represented by {@code Amount} named {@code amount} in the model.
         */
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("timestamp", timestamp)
                    .add("cost", amount)
                    .add("description", description)
                    .add("payeeName", payeeName)
                    .add("expenses", expenses)
                    .toString();
        }
    }
}
