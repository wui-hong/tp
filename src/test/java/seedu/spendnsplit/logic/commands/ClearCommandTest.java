package seedu.spendnsplit.logic.commands;

import static seedu.spendnsplit.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.ModelManager;

public class ClearCommandTest {

    @Test
    public void execute_emptySpendNSplitBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
