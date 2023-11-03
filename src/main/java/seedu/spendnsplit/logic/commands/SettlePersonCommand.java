package seedu.spendnsplit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.List;
import java.util.Set;

import org.apache.commons.numbers.fraction.BigFraction;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.transaction.Amount;
import seedu.spendnsplit.model.transaction.Description;
import seedu.spendnsplit.model.transaction.Timestamp;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.model.transaction.portion.Portion;
import seedu.spendnsplit.model.transaction.portion.Weight;

/**
 * Settles any outstanding balance with a person.
 */
public class SettlePersonCommand extends Command {
    public static final String COMMAND_WORD = "settlePerson";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Settle any outstanding balance with another person.\n"
            + "If a timestamp is provided, the balance at the instant before the timestamp is used. "
            + "Else, the balance at the instant before the current system time is used.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TIMESTAMP + "TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIMESTAMP + "10/10/2020 12:00";

    public static final String MESSAGE_SETTLE_PERSON_SUCCESS = "Balance settled: %1$s";

    public static final String MESSAGE_DUPLICATE_TRANSACTION = "This transaction already exists in the address book";
    public static final String MESSAGE_NO_OUTSTANDING_BALANCE =
            "There is no outstanding balance with %1$s before %2$s.";

    public static final String SETTLE_TRANSACTION_DESCRIPTION = "Settle balance with %1$s";

    private final Index targetIndex;
    private final Timestamp time;

    /**
     * Constructs a settle person command.
     */
    public SettlePersonCommand(Index targetIndex, Timestamp time) {
        this.targetIndex = targetIndex;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToSettle = lastShownList.get(targetIndex.getZeroBased());

        // total money the person owes the user
        BigFraction balance = model.getBalance(personToSettle.getName(), time);
        if (balance.equals(BigFraction.ZERO)) {
            throw new CommandException(String.format(MESSAGE_NO_OUTSTANDING_BALANCE,
                    personToSettle.getName(), time));
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

        Transaction settleTransaction = new Transaction(
                new Amount(balance.abs().toString()), description, name, portions, time);

        if (model.hasTransaction(settleTransaction)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSACTION);
        }

        // create transaction to cancel out outstanding balance
        model.addTransaction(settleTransaction);
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
        return targetIndex.equals(otherSettlePersonCommand.targetIndex)
                && time.equals(otherSettlePersonCommand.time);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("time", time)
                .toString();
    }
}
