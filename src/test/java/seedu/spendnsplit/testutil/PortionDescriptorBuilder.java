package seedu.spendnsplit.testutil;

import seedu.spendnsplit.logic.descriptors.PortionDescriptor;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.portion.Portion;
import seedu.spendnsplit.model.transaction.portion.Weight;

/**
 * A utility class to help with building PortionDescriptor objects.
 */
public class PortionDescriptorBuilder {

    private PortionDescriptor descriptor;

    public PortionDescriptorBuilder() {
        descriptor = new PortionDescriptor();
    }

    public PortionDescriptorBuilder(PortionDescriptor descriptor) {
        this.descriptor = new PortionDescriptor(descriptor);
    }

    /**
     * Returns an {@code PortionDescriptor} with fields containing {@code portion}'s details.
     */
    public PortionDescriptorBuilder(Portion portion) {
        descriptor = new PortionDescriptor();
        descriptor.setPersonName(portion.getPersonName());
        descriptor.setWeight(portion.getWeight());
    }

    /**
     * Sets the {@code Name} of the {@code PortionDescriptor} that we are building.
     */
    public PortionDescriptorBuilder withPersonName(String name) {
        descriptor.setPersonName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code PortionDescriptor} that we are building.
     */
    public PortionDescriptorBuilder withWeight(String weight) {
        descriptor.setWeight(new Weight(weight));
        return this;
    }

    public PortionDescriptor build() {
        return descriptor;
    }
}
