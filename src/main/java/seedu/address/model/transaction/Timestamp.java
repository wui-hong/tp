package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Represents a Transaction's timestamp in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimestamp(String)}
 */
public class Timestamp {
    public static final String MESSAGE_CONSTRAINTS = "Timestamp must be in a valid ISO datetime format";

    public final LocalDateTime value;

    /**
     * Constructs an {@code Timestamp}.
     *
     * @param timestamp A valid timestamp.
     */
    public Timestamp(String timestamp) {
        requireNonNull(timestamp);
        checkArgument(isValidTimestamp(timestamp), MESSAGE_CONSTRAINTS);
        value = LocalDateTime.parse(timestamp);
    }


    /**
     * Returns the timestamp for the current time.
     */
    public static Timestamp now() {
        return new Timestamp(LocalDateTime.now().toString());
    }

    /**
     * Returns true if a given string is a valid timestamp.
     */
    public static boolean isValidTimestamp(String test) {
        try {
            LocalDateTime.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return DateTimeFormatter.ofPattern("dd MMM yyyy HH:MM:SS").format(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Timestamp)) {
            return false;
        }

        Timestamp otherTimestamp = (Timestamp) other;
        return value.equals(otherTimestamp.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

