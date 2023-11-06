package seedu.spendnsplit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_TIMESTAMP;
import static seedu.spendnsplit.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.spendnsplit.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.model.transaction.Timestamp;

/**
 * Contains unit tests for {@code SettlePersonCommand}.
 */
public class SettlePersonCommandTest {

    private static final Timestamp TIME = new Timestamp(VALID_TIMESTAMP);

    @Test
    public void equals() {
        SettlePersonCommand settleFirstCommand = new SettlePersonCommand(INDEX_FIRST_ELEMENT, TIME);
        SettlePersonCommand settleSecondCommand = new SettlePersonCommand(INDEX_SECOND_ELEMENT, TIME);

        // same object -> returns true
        assertEquals(settleFirstCommand, settleFirstCommand);

        // same values -> returns true
        SettlePersonCommand settleFirstCommandCopy = new SettlePersonCommand(INDEX_FIRST_ELEMENT, TIME);
        assertEquals(settleFirstCommand, settleFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, settleFirstCommand);

        // null -> returns false
        assertNotEquals(null, settleFirstCommand);

        // different person -> returns false
        assertNotEquals(settleFirstCommand, settleSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        SettlePersonCommand settleCommand = new SettlePersonCommand(targetIndex, TIME);
        String expected = SettlePersonCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", time=" + TIME + "}";
        assertEquals(expected, settleCommand.toString());
    }
}
