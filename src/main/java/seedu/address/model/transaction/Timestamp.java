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
    public static final String MESSAGE_CONSTRAINTS = "Date must be in DD/MM/YYYY format "
            + "and time must be in HH:MM format; date should come before time if both are provided";

    public static final String DATE_VALIDATION = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
    public static final String TIME_VALIDATION = "[0-9]{2}:[0-9]{2}";

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_FORMAT + " " + TIME_FORMAT);

    public final LocalDateTime value;

    /**
     * Constructs a {@code Timestamp}.
     *
     * @param timestamp A valid timestamp.
     */
    public Timestamp(String timestamp) {
        requireNonNull(timestamp);
        checkArgument(isValidTimestamp(timestamp), MESSAGE_CONSTRAINTS);
        value = parse(timestamp);
    }

    private static LocalDateTime parse(String timestamp) {
        if (timestamp.matches(DATE_VALIDATION + " " + TIME_VALIDATION)) {
            return LocalDateTime.parse(timestamp, DATETIME_FORMATTER);
        }
        if (timestamp.matches(DATE_VALIDATION)) {
            return LocalDateTime.parse(timestamp + " 00:00", DATE_FORMATTER);
        }
        if (timestamp.matches(TIME_VALIDATION)) {
            String date = DATE_FORMATTER.format(LocalDateTime.now());
            return LocalDateTime.parse(date + " " + timestamp, DATETIME_FORMATTER);
        }
        return null;
    }

    /**
     * Returns the timestamp for the current time.
     */
    public static Timestamp now() {
        return new Timestamp(DATETIME_FORMATTER.format(LocalDateTime.now()));
    }

    /**
     * Returns true if a given string is a valid timestamp.
     */
    public static boolean isValidTimestamp(String test) {
        try {
            return parse(test) != null;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return DATETIME_FORMATTER.format(value);
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

