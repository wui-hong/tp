package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TimestampTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Timestamp((String) null));
    }

    @Test
    public void constructor_invalidTimestamp_throwsIllegalArgumentException() {
        String invalidTimestamp = "";
        assertThrows(IllegalArgumentException.class, () -> new Timestamp(invalidTimestamp));
    }

    @Test
    public void isValidTimestamp() {
        // null timestamp
        assertThrows(NullPointerException.class, () -> Timestamp.isValidTimestamp(null));

        // invalid timestamps
        assertFalse(Timestamp.isValidTimestamp("")); // empty string
        assertFalse(Timestamp.isValidTimestamp(" ")); // spaces only
        assertFalse(Timestamp.isValidTimestamp("2023-10-13")); // date only
        assertFalse(Timestamp.isValidTimestamp("2023-32-32T12:34:56.789")); // invalid date
        assertFalse(Timestamp.isValidTimestamp(" 2023-10-13T12:34:56")); // extra space

        // valid timestamps
        assertTrue(Timestamp.isValidTimestamp("2023-10-13T12:34:56"));
        assertTrue(Timestamp.isValidTimestamp("2023-10-13T12:34:56.789"));

    }

    @Test
    public void hashcode() {
        Timestamp timestamp = new Timestamp("2023-10-13T12:34:56");
        Timestamp otherTimestamp = new Timestamp("2023-10-13T12:34:56");
        assertEquals(timestamp.hashCode(), otherTimestamp.hashCode());
    }

    @Test
    public void equals() {
        Timestamp timestamp = new Timestamp("2023-10-13T12:34:56");

        // same values -> returns true
        assertEquals(timestamp, new Timestamp("2023-10-13T12:34:56"));

        // same object -> returns true
        assertEquals(timestamp, timestamp);

        // null -> returns false
        assertNotEquals(null, timestamp);

        // different types -> returns false
        assertFalse(timestamp.equals(5.0f));

        // different values -> returns false
        assertNotEquals(timestamp, new Timestamp("2023-10-12T12:34:56"));
    }
}
