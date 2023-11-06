package seedu.address.testutil;

import seedu.address.logic.commands.UpdatePortionCommand.UpdatePortionDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.portion.Portion;
import seedu.address.model.transaction.portion.Weight;

/**
 * A utility class to help with building UpdatePortionDescriptor objects.
 */
public class UpdatePortionDescriptorBuilder {

    private UpdatePortionDescriptor descriptor;

    public UpdatePortionDescriptorBuilder() {
        descriptor = new UpdatePortionDescriptor();
    }

    public UpdatePortionDescriptorBuilder(UpdatePortionDescriptor descriptor) {
        this.descriptor = new UpdatePortionDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdatePortionDescriptor} with fields containing {@code portion}'s details.
     */
    public UpdatePortionDescriptorBuilder(Portion portion) {
        descriptor = new UpdatePortionDescriptor();
        descriptor.setPersonName(portion.getPersonName());
        descriptor.setWeight(portion.getWeight());
    }

    /**
     * Sets the {@code Name} of the {@code UpdatePortionDescriptor} that we are building.
     */
    public UpdatePortionDescriptorBuilder withPersonName(String name) {
        descriptor.setPersonName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code UpdatePortionDescriptor} that we are building.
     */
    public UpdatePortionDescriptorBuilder withWeight(String weight) {
        try {
            descriptor.setWeight(new Weight(weight));
        } catch (Exception e) {
            //
        }
        return this;
    }

    public UpdatePortionDescriptor build() {
        return descriptor;
    }
}
