package seedu.address.model.transaction.expense;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;

/**
 * Represents an Expense in a transaction.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense {
    // Identity fields
    private final Name personName;
    private final Weight weight;

    /**
     * Every field must be present and not null.
     */
    public Expense(Name personName, Weight weight) {
        this.personName = personName;
        this.weight = weight;
    }

    public Name getPersonName() {
        return personName;
    }

    public Weight getWeight() {
        return weight;
    }

    /**
     * Returns true if both expenses have the same name and weight.
     */
    public boolean isSameExpense(Expense otherExpense) {
        if (otherExpense == this) {
            return true;
        }

        return otherExpense != null
                && otherExpense.getPersonName().equals(getPersonName())
                && otherExpense.getWeight().equals(getWeight());
    }

    /**
     * Returns true if both expenses have the same identity fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Expense)) {
            return false;
        }

        Expense otherExpense = (Expense) other;
        return personName.equals(otherExpense.personName)
                && weight.equals(otherExpense.weight);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(personName, weight);
    }

    @Override
    public String toString() {
        return "[name: " + personName + ", weight: " + weight + "]";
    }
}
