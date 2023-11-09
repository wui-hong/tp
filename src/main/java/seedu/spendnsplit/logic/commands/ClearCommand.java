package seedu.spendnsplit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.SpendNSplit;

/**
 * Clears the spendnsplit book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "App data has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setSpendNSplitBook(new SpendNSplit());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
