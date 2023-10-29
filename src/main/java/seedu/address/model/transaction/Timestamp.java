package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;


/**
 * Represents a Transaction's timestamp in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimestamp(String)}
 */
public class Timestamp implements Comparable<Timestamp> {
    public static final String MESSAGE_CONSTRAINTS = "Timestamp must be in a valid ISO datetime format";

    public final LocalDateTime value;

    /**
     * Constructs a {@code Timestamp}.
     *
     * @param timestamp A valid timestamp.
     */
    public Timestamp(String timestamp) {
        requireNonNull(timestamp);
        checkArgument(isValidTimestamp(timestamp), MESSAGE_CONSTRAINTS);
        value = LocalDateTime.parse(timestamp);
    }

    /**
     * Constructs a {@code Timestamp}.
     *
     * @param value A valid timestamp.
     */
    public Timestamp(LocalDateTime value) {
        this.value = value;
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
        return value.toString();
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
        // Uses string format to get rid of floating point errors
        return value.toString().equals(otherTimestamp.value.toString());
    }

    @Override
    public int compareTo(Timestamp other) {
        // Seconds are not compared
        if (this.equals(other)) {
            return 0;
        }
        return value.compareTo(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

