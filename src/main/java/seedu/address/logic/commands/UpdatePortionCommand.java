package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

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
import seedu.address.model.transaction.portion.Portion;
import seedu.address.model.transaction.portion.Weight;

/**
 * Edits the portion details of an existing transaction in the transaction list.
 */
public class UpdatePortionCommand extends Command {

    public static final String COMMAND_WORD = "updatePortion";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Edits the portion details of the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_WEIGHT + "WEIGHT] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_WEIGHT + "1 / 2";

    // TODO: Message should also include details about the transaction
    public static final String MESSAGE_UPDATE_PORTION_SUCCESS = "Updated Portion: %1$s";

    public static final String MESSAGE_DELETE_ONLY_PORTION_FAILURE = "Cannot delete the only portion in a transaction.";

    // TODO: add message for invalid pair of name and weight
    public static final String MESSAGE_PORTION_NOT_UPDATED = "At least one field must be provided.";

    private final Index index;

    private final UpdatePortionDescriptor updatePortionDescriptor;

    /**
     * Edits the portion details of an existing transaction in the transaction list.
     */
    public UpdatePortionCommand(Index index, UpdatePortionDescriptor updatePortionDescriptor) {
        requireNonNull(index);
        requireAllNonNull(updatePortionDescriptor);

        this.index = index;
        this.updatePortionDescriptor = new UpdatePortionDescriptor(updatePortionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownTransactionList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownTransactionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToEdit = lastShownTransactionList.get(index.getZeroBased());
        Transaction transactionWithUpdatedPortions =
                createTransactionWithUpdatedPortions(transactionToEdit, updatePortionDescriptor);

        model.setTransaction(transactionToEdit, transactionWithUpdatedPortions);
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(
                String.format(MESSAGE_UPDATE_PORTION_SUCCESS, Messages.format(transactionWithUpdatedPortions)));
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code updatePortionDescriptor}.
     */
    private static Transaction createTransactionWithUpdatedPortions(Transaction transactionToEdit,
                                                                    UpdatePortionDescriptor updatePortionDescriptor)
            throws CommandException {
        assert transactionToEdit != null;

        Amount existingAmount = transactionToEdit.getAmount();
        Description existingDescription = transactionToEdit.getDescription();
        Name existingPayeeName = transactionToEdit.getPayeeName();
        Timestamp existingTimestamp = transactionToEdit.getTimestamp();

        Set<Portion> newPortions = transactionToEdit.getPortionsCopy();
        Name personName = updatePortionDescriptor.getPersonName();
        Weight updatedWeight = updatePortionDescriptor.getWeight();

        // remove existing portion, if exists
        newPortions.removeIf(portion -> portion.getPersonName().equals(personName));

        if (updatedWeight.value.equals(BigFraction.ZERO)) {
            // delete portion
            if (newPortions.isEmpty()) {
                throw new CommandException(MESSAGE_DELETE_ONLY_PORTION_FAILURE);
            }
        } else {
            newPortions.add(new Portion(personName, updatedWeight));
        }

        return new Transaction(existingAmount, existingDescription, existingPayeeName, newPortions, existingTimestamp);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdatePortionCommand)) {
            return false;
        }

        UpdatePortionCommand otherUpdatePortionCommand = (UpdatePortionCommand) other;
        return index.equals(otherUpdatePortionCommand.index)
                && updatePortionDescriptor.equals(otherUpdatePortionCommand.updatePortionDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("updatePortionDescriptor", updatePortionDescriptor)
                .toString();
    }

    /**
     * Stores the details to update the portion with.
     * All fields are mandatory.
     */
    public static class UpdatePortionDescriptor {
        private Name personName;
        private Weight weight;

        public UpdatePortionDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code UpdatePortionDescriptor} is used internally.
         */
        public UpdatePortionDescriptor(UpdatePortionDescriptor toCopy) {
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
            if (!(other instanceof UpdatePortionDescriptor)) {
                return false;
            }

            UpdatePortionDescriptor otherUpdatePortionDescriptor = (UpdatePortionDescriptor) other;
            return Objects.equals(personName, otherUpdatePortionDescriptor.personName)
                    && Objects.equals(weight, otherUpdatePortionDescriptor.weight);
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
