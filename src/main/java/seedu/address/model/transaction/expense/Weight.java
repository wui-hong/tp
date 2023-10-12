package seedu.address.model.transaction.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import org.apache.commons.numbers.fraction.BigFraction;

import seedu.address.commons.util.FractionUtil;

/**
 * Represents a Weight in an Expense.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeight(String)}
 */
public class Weight {
    public static final String MESSAGE_CONSTRAINTS =
            "Weight should only contain non-negative numbers.";
    public static final String VALIDATION_REGEX = "^\\d+(\\.\\d+)?$";

    public static final int DEFAULT_DECIMAL_PLACES = 2;

    public final BigFraction value;

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight.
     */
    public Weight(String weight) {
        requireNonNull(weight);
        checkArgument(isValidWeight(weight), MESSAGE_CONSTRAINTS);
        value = FractionUtil.parseFraction(weight);
    }

    /**
     * Returns true if a given string is a valid weight.
     */
    public static boolean isValidWeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return FractionUtil.toString(value, DEFAULT_DECIMAL_PLACES);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Weight)) {
            return false;
        }

        Weight otherWeight = (Weight) other;
        return value.equals(otherWeight.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
