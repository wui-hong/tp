package seedu.spendnsplit.logic.descriptors;

import java.util.Objects;

import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.portion.Weight;

/**
 * Stores the details to update the portion with.
 * All fields are mandatory.
 */
public class PortionDescriptor {
    private Name personName;
    private Weight weight;

    public PortionDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code PortionDescriptor} is used internally.
     */
    public PortionDescriptor(PortionDescriptor toCopy) {
        setPersonName(toCopy.personName);
        setWeight(toCopy.weight);
    }

    public void setPersonName(Name personName) {
        this.personName = personName;
    }

    public Name getPersonName() {
        return personName;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public Weight getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PortionDescriptor)) {
            return false;
        }

        PortionDescriptor otherPortionDescriptor = (PortionDescriptor) other;
        return Objects.equals(personName, otherPortionDescriptor.personName)
            && Objects.equals(weight, otherPortionDescriptor.weight);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("personName", personName)
            .add("weight", weight)
            .toString();
    }
}
