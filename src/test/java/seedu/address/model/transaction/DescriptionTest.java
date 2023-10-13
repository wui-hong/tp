package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("Dinner"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("Raffles Hall Cluster Event Logistics: Food,"
                + "Transportation and Miscellaneous Expenses")); // long description
    }

    @Test
    public void hashcode() {
        Description description = new Description("Dinner");
        Description otherDescription = new Description("Dinner");
        assertEquals(description.hashCode(), otherDescription.hashCode());
    }

    @Test
    public void equals() {
        Description description = new Description("Valid Description");

        // same values -> returns true
        assertEquals(description, new Description("Valid Description"));

        // same object -> returns true
        assertEquals(description, description);

        // null -> returns false
        assertNotEquals(null, description);

        // different types -> returns false
        assertFalse(description.equals(5.0f));

        // different values -> returns false
        assertNotEquals(description, new Description("Other Valid Description"));
    }
}
