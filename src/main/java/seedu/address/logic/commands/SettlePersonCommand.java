package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;

import java.util.List;
import java.util.Set;

import org.apache.commons.numbers.fraction.BigFraction;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.portion.Portion;
import seedu.address.model.transaction.portion.Weight;

/**
 * Settles any outstanding balance with a person.
 */
public class SettlePersonCommand extends Command {
    public static final String COMMAND_WORD = "settlePerson";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Settle any outstanding balance with another person.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SETTLE_PERSON_SUCCESS = "Balance settled: %1$s";

    public static final String MESSAGE_NO_OUTSTANDING_BALANCE = "There is no outstanding balance with this person.";

    public static final String SETTLE_TRANSACTION_DESCRIPTION = "Settle balance with %1$s";

    private final Index targetIndex;

    public SettlePersonCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToSettle = lastShownList.get(targetIndex.getZeroBased());

        model.updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);

        // total money the person owes the user
        BigFraction balance = model.getBalance(personToSettle.getName());
        if (balance.equals(BigFraction.ZERO)) {
            throw new CommandException(MESSAGE_NO_OUTSTANDING_BALANCE);
        }

        Description description = new Description(String.format(
                SETTLE_TRANSACTION_DESCRIPTION, personToSettle.getName()));
        Weight weight = new Weight(BigFraction.ONE.toString());
        Name name;
        Set<Portion> portions;

        if (balance.signum() > 0) {
            // if the balance is positive, the person owes the user money
            // we create a transaction where the person pays the user back
            name = personToSettle.getName();
            portions = Set.of(new Portion(Name.SELF, weight));
        } else {
            // if the balance is negative, the user owes the person money
            // we create a transaction where the user pays the person back
            name = Name.SELF;
            portions = Set.of(new Portion(personToSettle.getName(), weight));
        }

        // create transaction to cancel out outstanding balance
        model.addTransaction(new Transaction(
                new Amount(balance.abs().toString()), description, name, portions));
        return new CommandResult(String.format(MESSAGE_SETTLE_PERSON_SUCCESS, personToSettle.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SettlePersonCommand)) {
            return false;
        }

        SettlePersonCommand otherSettlePersonCommand = (SettlePersonCommand) other;
        return targetIndex.equals(otherSettlePersonCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
