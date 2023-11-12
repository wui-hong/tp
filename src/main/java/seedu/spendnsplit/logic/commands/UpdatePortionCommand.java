package seedu.spendnsplit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.spendnsplit.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.List;
import java.util.Set;

import org.apache.commons.numbers.fraction.BigFraction;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.logic.descriptors.PortionDescriptor;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.Amount;
import seedu.spendnsplit.model.transaction.Description;
import seedu.spendnsplit.model.transaction.Timestamp;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.model.transaction.portion.Portion;
import seedu.spendnsplit.model.transaction.portion.Weight;

/**
 * Edits the portion details of an existing transaction in the transaction list.
 */
public class UpdatePortionCommand extends Command {

    public static final String COMMAND_WORD = "updatePortion";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Edits the portion details of the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "Existing values will be overwritten by the input values. "
            + "Setting the weight of an existing portion will delete it.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "NAME "
            + PREFIX_WEIGHT + "WEIGHT\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_WEIGHT + "1 / 2";

    public static final String MESSAGE_DUPLICATE_TRANSACTION =
            "The updated transaction already exists in the app";

    public static final String MESSAGE_UPDATE_PORTION_SUCCESS = "Updated Portion: %1$s";

    public static final String MESSAGE_DELETE_ONLY_PORTION_FAILURE = "Cannot delete the only portion in a transaction.";

    public static final String MESSAGE_UNKNOWN_PARTY =
            "The portion involves unknown parties; please set them to 'Others'";

    public static final String MESSAGE_IRRELEVANT_UPDATED_TRANSACTION =
            "The updated transaction does not affect your balances. Please use the delete command instead.";

    public static final String MESSAGE_INVALID_PROPORTION = "The weight should be a proportion of the total sum; "
            + "it should be greater than or equal to 0 and less than 1.";

    public static final String MESSAGE_ONLY_PORTION =
            "There is only one portion; its weight can only be 1 and cannot be updated.";

    private final Index index;

    private final PortionDescriptor portionDescriptor;

    /**
     * Edits the portion details of an existing transaction in the transaction list.
     */
    public UpdatePortionCommand(Index index, PortionDescriptor portionDescriptor) {
        requireNonNull(index);
        requireAllNonNull(portionDescriptor);

        this.index = index;
        this.portionDescriptor = new PortionDescriptor(portionDescriptor);
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
                createTransactionWithUpdatedPortions(transactionToEdit, portionDescriptor);

        if (!transactionWithUpdatedPortions.isKnown(model.getAllNames())) {
            throw new CommandException(MESSAGE_UNKNOWN_PARTY);
        }

        if (!transactionWithUpdatedPortions.isRelevant()) {
            throw new CommandException(MESSAGE_IRRELEVANT_UPDATED_TRANSACTION);
        }

        if (model.hasTransaction(transactionWithUpdatedPortions)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSACTION);
        }

        model.setTransaction(transactionToEdit, transactionWithUpdatedPortions);
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(
                String.format(MESSAGE_UPDATE_PORTION_SUCCESS, Messages.format(transactionWithUpdatedPortions)));
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code portionDescriptor}.
     */
    private static Transaction createTransactionWithUpdatedPortions(Transaction transactionToEdit,
                                                                    PortionDescriptor portionDescriptor)
            throws CommandException {
        assert transactionToEdit != null;

        Amount existingAmount = transactionToEdit.getAmount();
        Description existingDescription = transactionToEdit.getDescription();
        Name existingPayeeName = transactionToEdit.getPayeeName();
        Timestamp existingTimestamp = transactionToEdit.getTimestamp();

        Set<Portion> newPortions = transactionToEdit.getPortionsCopy();
        Name personName = portionDescriptor.getPersonName();
        Weight updatedWeight = portionDescriptor.getWeight();

        if (updatedWeight.value.compareTo(BigFraction.ONE) >= 0) {
            throw new CommandException(MESSAGE_INVALID_PROPORTION);
        }

        // remove existing portion, if exists
        newPortions.removeIf(portion -> portion.getPersonName().equals(personName));

        if (updatedWeight.value.equals(BigFraction.ZERO)) {
            // delete portion
            if (newPortions.isEmpty()) {
                throw new CommandException(MESSAGE_DELETE_ONLY_PORTION_FAILURE);
            }
        } else {
            if (newPortions.isEmpty()) {
                throw new CommandException(MESSAGE_ONLY_PORTION);
            }
            BigFraction originalWeights = Transaction.sumWeights(newPortions);
            updatedWeight = new Weight(originalWeights.multiply(updatedWeight.value)
                    .divide(BigFraction.ONE.subtract(updatedWeight.value)));
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
                && portionDescriptor.equals(otherUpdatePortionCommand.portionDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("portionDescriptor", portionDescriptor)
                .toString();
    }
}
