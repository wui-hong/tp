package seedu.address.testutil;

import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Transaction;

/**
 * A utility class to help with building EditTransactionDescriptor objects.
 */
public class EditTransactionDescriptorBuilder {

    private EditTransactionDescriptor descriptor;

    public EditTransactionDescriptorBuilder() {
        descriptor = new EditTransactionDescriptor();
    }

    public EditTransactionDescriptorBuilder(EditTransactionDescriptor descriptor) {
        this.descriptor = new EditTransactionDescriptor(descriptor);
    }

    public EditTransactionDescriptorBuilder(Transaction transaction) {
        descriptor = new EditTransactionDescriptor();
        descriptor.setAmount(transaction.getAmount());
        descriptor.setDescription(transaction.getDescription());
        descriptor.setPayeeName(transaction.getPayee());
        descriptor.setExpenses(transaction.getExpenses());
    }

    /**
     * Sets the {@code Amount} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }

    /**
     * Sets the {@code Payee} of the {@code EditTransactionDescriptor} that we are building.
     */
}
