package seedu.spendnsplit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in app whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 * If no keywords are specified, all persons will be listed.
 */
public class ListPersonCommand extends Command {

    public static final String COMMAND_WORD = "listPerson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "If no keywords are specified, all persons will be listed.\n"
            + "Parameters: [KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public ListPersonCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // handles nulls
        if (!(other instanceof ListPersonCommand)) {
            return false;
        }

        ListPersonCommand otherFindPersonCommand = (ListPersonCommand) other;
        return predicate.equals(otherFindPersonCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
