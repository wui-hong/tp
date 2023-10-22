package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SetShorthandCommandTest {

    @Test
    public void equals() {
        SetShorthandCommand commandShorthandA = new SetShorthandCommand(SetShorthandCommand.COMMAND_WORD, "a");
        SetShorthandCommand commandShorthandB = new SetShorthandCommand(SetShorthandCommand.COMMAND_WORD, "b");
        SetShorthandCommand commandShorthandACopy = new SetShorthandCommand(SetShorthandCommand.COMMAND_WORD, "a");

        // same object -> returns true
        assertTrue(commandShorthandA.equals(commandShorthandA));

        // same values -> returns true
        assertTrue(commandShorthandA.equals(commandShorthandACopy));

        // different types -> returns false
        assertFalse(commandShorthandA.equals(1));

        // null -> returns false
        assertFalse(commandShorthandA.equals(null));

        // different person -> returns false
        assertFalse(commandShorthandA.equals(commandShorthandB));
    }

    @Test
    public void toStringMethod() {
        SetShorthandCommand commandShorthand = new SetShorthandCommand(SetShorthandCommand.COMMAND_WORD, "a");
        String expected = SetShorthandCommand.class.getCanonicalName()
                + "{original=" + SetShorthandCommand.COMMAND_WORD + ", " + "shorthand=" + "a" + "}";
        assertEquals(expected, commandShorthand.toString());
    }
}
