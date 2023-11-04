package seedu.spendnsplit.model.transaction.portion;

import java.util.Objects;

import seedu.spendnsplit.model.person.Name;

/**
 * Represents a Portion in a transaction.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Portion {
    // Identity fields
    private final Name personName;
    private final Weight weight;

    /**
     * Every field must be present and not null.
     */
    public Portion(Name personName, Weight weight) {
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
     * Returns true if both portions have the same name and weight.
     */
    public boolean isSamePortion(Portion otherPortion) {
        if (otherPortion == this) {
            return true;
        }

        return otherPortion != null
                && otherPortion.getPersonName().equals(getPersonName())
                && otherPortion.getWeight().equals(getWeight());
    }

    /**
     * Returns true if both portions have the same identity fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Portion)) {
            return false;
        }

        Portion otherPortion = (Portion) other;
        return personName.equals(otherPortion.personName)
                && weight.equals(otherPortion.weight);
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
