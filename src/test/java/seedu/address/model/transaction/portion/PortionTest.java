package seedu.address.model.transaction.portion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PortionBuilder;

class PortionTest {
    @Test
    public void isSamePortion() {
        Portion portion = new PortionBuilder().build();

        // same object -> returns true
        assertTrue(portion.isSamePortion(new PortionBuilder().build()));

        // null -> returns false
        assertFalse(portion.isSamePortion(null));

        // different attributes -> returns false
        String editedName = "Daniel";
        String editedWeight = "10";
        assertFalse(portion.isSamePortion(new PortionBuilder()
                .withName(editedName).withWeight(editedWeight).build()));
        assertFalse(portion.isSamePortion(new PortionBuilder().withName(editedName).build()));
        assertFalse(portion.isSamePortion(new PortionBuilder().withWeight(editedWeight).build()));
    }

    @Test
    public void equals() {
        String name = "Daniel";
        String weight = "10";
        Portion portion = new PortionBuilder()
                .withName(name).withWeight(weight).build();

        assertEquals(portion, portion);
        assertEquals(portion, new PortionBuilder()
                .withName(name).withWeight(weight).build());

        String otherName = "Elle";
        String otherWeight = "20.0";
        Portion otherPortion = new PortionBuilder()
                .withName(otherName).withWeight(otherWeight).build();

        assertNotEquals(portion, otherPortion);
        assertNotEquals(portion, null);
    }

    @Test
    public void hashcode() {
        String name = "Daniel";
        String weight = "10";
        Portion portion = new PortionBuilder()
                .withName(name).withWeight(weight).build();
        Portion otherPortion = new PortionBuilder()
                .withName(name).withWeight(weight).build();

        assertEquals(portion, otherPortion);
    }
}
