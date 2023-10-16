package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;

/**
 * Contains unit tests for {@code SettlePersonCommand}.
 */
public class SettlePersonCommandTest {
    @Test
    public void equals() {
        SettlePersonCommand settleFirstCommand = new SettlePersonCommand(INDEX_FIRST_ELEMENT);
        SettlePersonCommand settleSecondCommand = new SettlePersonCommand(INDEX_SECOND_ELEMENT);

        // same object -> returns true
        assertEquals(settleFirstCommand, settleFirstCommand);

        // same values -> returns true
        SettlePersonCommand settleFirstCommandCopy = new SettlePersonCommand(INDEX_FIRST_ELEMENT);
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
        SettlePersonCommand settleCommand = new SettlePersonCommand(targetIndex);
        String expected = SettlePersonCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, settleCommand.toString());
    }
}
