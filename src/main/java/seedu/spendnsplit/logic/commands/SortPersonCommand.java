package seedu.spendnsplit.logic.commands;

import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.model.Model;

/**
 * Sorts persons by balance.
 */
public class SortPersonCommand extends Command {
    public static final String COMMAND_WORD = "sortPerson";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort Person List by balance. "
            + "Parameters: SIGN (+ for post positive balance first; - for most negative balance first)\n"
            + "Example: " + COMMAND_WORD + " +";

    public static final String MESSAGE_SORT_PERSON_SUCCESS = "Person list sorted in %s order of balance";

    private final Boolean isDesc;

    public SortPersonCommand(boolean isDesc) {
        this.isDesc = isDesc;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (isDesc) {
            model.sortPersonDescending();
            return new CommandResult(String.format(MESSAGE_SORT_PERSON_SUCCESS, "descending"));
        } else {
            model.sortPersonAscending();
            return new CommandResult(String.format(MESSAGE_SORT_PERSON_SUCCESS, "ascending"));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortPersonCommand)) {
            return false;
        }

        SortPersonCommand otherSortPersonCommand = (SortPersonCommand) other;
        return isDesc.equals(otherSortPersonCommand.isDesc);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isDesc", isDesc)
                .toString();
    }
}
