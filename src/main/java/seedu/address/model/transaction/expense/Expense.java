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
    private final Name name;
    private final Weight weight;

    /**
     * Every field must be present and not null.
     */
    public Expense(Name name, Weight weight) {
        this.name = name;
        this.weight = weight;
    }

    public Name getName() {
        return name;
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
                && otherExpense.getName().equals(getName())
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
        return name.equals(otherExpense.name)
                && weight.equals(otherExpense.weight);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, weight);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("weight", weight)
                .toString();
    }
}
