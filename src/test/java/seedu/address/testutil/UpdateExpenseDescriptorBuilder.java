package seedu.address.testutil;

import seedu.address.logic.commands.UpdateExpenseCommand.UpdateExpenseDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.model.transaction.expense.Weight;

/**
 * A utility class to help with building UpdateExpenseDescriptor objects.
 */
public class UpdateExpenseDescriptorBuilder {

    private UpdateExpenseDescriptor descriptor;

    public UpdateExpenseDescriptorBuilder() {
        descriptor = new UpdateExpenseDescriptor();
    }

    public UpdateExpenseDescriptorBuilder(UpdateExpenseDescriptor descriptor) {
        this.descriptor = new UpdateExpenseDescriptor(descriptor);
    }

    public UpdateExpenseDescriptorBuilder(Expense expense) {
        descriptor = new UpdateExpenseDescriptor();
        descriptor.setPersonName(expense.getPersonName());
        descriptor.setWeight(expense.getWeight());
    }

    /**
     * Sets the {@code Name} of the {@code UpdateExpenseDescriptor} that we are building.
     */
    public UpdateExpenseDescriptorBuilder withPersonName(String name) {
        descriptor.setPersonName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code UpdateExpenseDescriptor} that we are building.
     */
    public UpdateExpenseDescriptorBuilder withWeight(String weight) {
        descriptor.setWeight(new Weight(weight));
        return this;
    }

    public UpdateExpenseDescriptor build() {
        return descriptor;
    }
}
