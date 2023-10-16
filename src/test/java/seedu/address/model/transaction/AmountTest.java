package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        // null amount
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount("1.2.3")); // contains multiple decimal points

        // valid amount
        assertTrue(Amount.isValidAmount("123")); // numbers without decimal point
        assertTrue(Amount.isValidAmount("123.45")); // numbers with decimal point
        assertTrue(Amount.isValidAmount(
            "123.4567890123456789012345678901234567890123456789012345678901234567890")); // long numbers
        assertTrue(Amount.isValidAmount("1/2")); // fractional form
        assertTrue(Amount.isValidAmount("1 / 2")); // fractional form with space
        assertTrue(Amount.isValidAmount("-1/2")); // fractional form with negative
        assertTrue(Amount.isValidAmount("1.0/2.0")); // fractional form with decimals
        assertTrue(Amount.isValidAmount("-123")); // negative numbers without decimal point
        assertTrue(Amount.isValidAmount("-123.45")); // negative numbers with decimal point
        assertTrue(Amount.isValidAmount("3.")); // contains no numbers after decimal point
        assertTrue(Amount.isValidAmount(".12")); // contains no numbers before decimal point
        assertTrue(Amount.isValidAmount("1.  2")); // contains spaces
    }

    @Test
    public void equals() {
        Amount amount = new Amount("1.23");

        // same values -> returns true
        assertEquals(amount, new Amount("1.23"));

        // same object -> returns true
        assertEquals(amount, amount);

        // null -> returns false
        assertNotEquals(null, amount);

        // different types -> returns false
        assertNotEquals("String", amount);

        // different values -> returns false
        assertNotEquals(amount, new Amount("2.34"));
    }
}
