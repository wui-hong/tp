package seedu.spendnsplit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.transaction.TransactionContainsKeywordsAndPersonNamesPredicate;

/**
 * List all transactions that involve some specified persons, either as a payer or a payee.
 * If no persons are specified, list all transactions.
 */
public class ListTransactionCommand extends Command {

    public static final String COMMAND_WORD = "listTransaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all transactions with descriptions that "
            + "contain any of the keywords or that involve any of the specified persons, either as a payer or a "
            + " payee.\n If no persons are specified, filters only by keywords; if no keywords are specified, "
            + "filters only by names. If neither are specified, lists all transactions.\n"
            + "Parameters: "
            + "[KEYWORDS]... "
            + "[" + PREFIX_NAME + "NAME]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    private final TransactionContainsKeywordsAndPersonNamesPredicate predicate;

    public ListTransactionCommand(TransactionContainsKeywordsAndPersonNamesPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTransactionList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListTransactionCommand)) {
            return false;
        }

        ListTransactionCommand otherFindCommand = (ListTransactionCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("predicate", predicate)
            .toString();
    }
}
