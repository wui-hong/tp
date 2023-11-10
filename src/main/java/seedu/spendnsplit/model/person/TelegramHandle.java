package seedu.spendnsplit.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.spendnsplit.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's telegram handle.
 * Guarantees: immutable; is valid as declared in {@link #isValidTelegramHandle(String)}
 */
public class TelegramHandle {

    public static final String MESSAGE_CONSTRAINTS = "Telegram Handles adhere to the following constraints:\n"
            + "Begin with a @ and be of minimum length 5 characters, exclusive of @ symbol.\n"
            + "The characters after @ can only be alphanumeric or underscores.\n"
            + "Example: @123han_dle123";

    // begins with @, and only contains at least 5 alphanumeric and underscores after
    private static final String VALIDATION_REGEX = "^@[a-zA-Z0-9_]{5,}$";

    public final String value;

    /**
     * Constructs a {@code TelegramHandle}.
     *
     * @param telegramHandle A valid telegramHandle.
     */
    public TelegramHandle(String telegramHandle) {
        requireNonNull(telegramHandle);
        checkArgument(isValidTelegramHandle(telegramHandle), MESSAGE_CONSTRAINTS);
        value = telegramHandle;
    }

    /**
     * Returns true if a given string is a valid telegramHandle.
     */
    public static boolean isValidTelegramHandle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TelegramHandle)) {
            return false;
        }

        TelegramHandle otherTelegramHandle = (TelegramHandle) other;
        return value.equals(otherTelegramHandle.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
