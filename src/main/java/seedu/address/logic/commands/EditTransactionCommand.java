package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.expense.Expense;

/**
 * Edits the details of an existing transaction in the transaction list.
 */
public class EditTransactionCommand extends Command {

    public static final String COMMAND_WORD = "editTransaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_COST + "AMOUNT] "
            + "[" + PREFIX_DETAILS + "DETAILS] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_COST + "10.00 "
            + PREFIX_DETAILS + "Bought a book";

    public static final String MESSAGE_EDIT_TRANSACTION_SUCCESS = "Edited Transaction: %1$s";

    public static final String MESSAGE_TRANSACTION_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;

    private final EditTransactionDescriptor editTransactionDescriptor;


    /**
     * Stores the details to edit the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class EditTransactionDescriptor {
        private Amount amount;
        private String description;
        private Person payee;
        private Set<Expense> expenses = new HashSet<>();

        /**
         * Copy constructor.
         */
        public EditTransactionDescriptor(EditTransactionDescriptor toCopy) {
            setAmount(toCopy.amount);
            setDescription(toCopy.description);
            setPayee(toCopy.payee);
            setExpenses(toCopy.expenses);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(amount, description, payee, expenses);
        }
        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setPayee(Person payee) {
            this.payee = payee;
        }

        public Optional<Person> getPayee() {
            return Optional.ofNullable(payee);
        }

        /**
         * Sets {@code expenses} to this object's {@code expenses}.
         * A defensive copy of {@code expenses} is used internally.
         * Requires {@code expenses} to be non-null.
         */
        public void setExpenses(Set<Expense> expenses) {
            requireAllNonNull(expenses);
            this.expenses = (expenses != null) ? new HashSet<>(expenses) : null;
        }
    }
}
