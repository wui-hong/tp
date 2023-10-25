package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TelegramHandleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TelegramHandle(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidTelegramHandle = "";
        assertThrows(IllegalArgumentException.class, () -> new TelegramHandle(invalidTelegramHandle));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> TelegramHandle.isValidTelegramHandle(null));

        // invalid addresses
        assertFalse(TelegramHandle.isValidTelegramHandle("")); // empty string
        assertFalse(TelegramHandle.isValidTelegramHandle("@ ")); // spaces only
        assertFalse(TelegramHandle.isValidTelegramHandle("@123 2312")); // contains space
        assertFalse(TelegramHandle.isValidTelegramHandle("@a12!d")); // contains special characters that are not _
        assertFalse(TelegramHandle.isValidTelegramHandle("@aasd")); // has less than 5 characters after @

        // valid addresses
        assertTrue(TelegramHandle.isValidTelegramHandle("@ryanlee"));
        assertTrue(TelegramHandle.isValidTelegramHandle("@1234z")); // 5 characters
        assertTrue(TelegramHandle.isValidTelegramHandle("@1892mjs_sdsed_223")); // long TelegramHandle
    }

    @Test
    public void equals() {
        TelegramHandle telegramHandle = new TelegramHandle("@valid_");

        // same values -> returns true
        assertTrue(telegramHandle.equals(new TelegramHandle("@valid_")));

        // same object -> returns true
        assertTrue(telegramHandle.equals(telegramHandle));

        // null -> returns false
        assertFalse(telegramHandle.equals(null));

        // different types -> returns false
        assertFalse(telegramHandle.equals(5.0f));

        // different values -> returns false
        assertFalse(telegramHandle.equals(new TelegramHandle("@notvalid_")));
    }
}
