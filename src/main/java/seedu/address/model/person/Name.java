package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name implements Comparable<Name> {

    public static final Name SELF = new Name("Self");
    public static final Name OTHERS = new Name("Others");
    public static final Set<Name> RESERVED_NAMES = Set.of(SELF, OTHERS);

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String RESERVED_CONSTRAINTS = "The name %s is reserved";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name.strip();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a reserved name.
     */
    public static boolean isReservedName(Name test) {
        return RESERVED_NAMES.contains(test);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equalsIgnoreCase(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.toUpperCase().hashCode();
    }

    @Override
    public int compareTo(Name other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.equals(Name.SELF)) {
            return -1;
        }
        if (other.equals(Name.SELF)) {
            return 1;
        }
        if (this.equals(Name.OTHERS)) {
            return 1;
        }
        if (other.equals(Name.OTHERS)) {
            return -1;
        }
        return this.fullName.toUpperCase().compareTo(other.fullName.toUpperCase());
    }

}
