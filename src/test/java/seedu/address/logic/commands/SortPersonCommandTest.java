package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


public class SortPersonCommandTest {
    @Test
    public void equals() {
        SortPersonCommand sortDescCommand = new SortPersonCommand(true);
        SortPersonCommand sortDescCommandCopy = new SortPersonCommand(true);
        SortPersonCommand sortAscCommand = new SortPersonCommand(false);

        // same object -> returns true
        assertEquals(sortDescCommand, sortDescCommand);

        // same values -> returns true;
        assertEquals(sortDescCommand, sortDescCommandCopy);

        // different types -> returns false
        assertNotEquals(1, sortDescCommand);

        // null -> returns false
        assertNotEquals(null, sortDescCommand);

        // different isDesc -> returns false
        assertNotEquals(sortDescCommand, sortAscCommand);
    }

    @Test
    public void toStringMethod() {
        SortPersonCommand sortPersonCommand = new SortPersonCommand(true);
        String expected = SortPersonCommand.class.getCanonicalName() + "{isDesc=true}";
        assertEquals(expected, sortPersonCommand.toString());
    }
}
