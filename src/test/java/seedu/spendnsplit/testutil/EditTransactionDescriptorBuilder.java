package seedu.spendnsplit.testutil;

import seedu.spendnsplit.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.Amount;
import seedu.spendnsplit.model.transaction.Description;
import seedu.spendnsplit.model.transaction.Timestamp;
import seedu.spendnsplit.model.transaction.Transaction;

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

    /**
     * Returns an {@code EditTransactionDescriptor} with fields containing {@code transaction}'s details
     */
    public EditTransactionDescriptorBuilder(Transaction transaction) {
        descriptor = new EditTransactionDescriptor();
        descriptor.setAmount(transaction.getAmount());
        descriptor.setDescription(transaction.getDescription());
        descriptor.setPayeeName(transaction.getPayeeName());
        descriptor.setTimestamp(transaction.getTimestamp());
    }

    /**
     * Returns an {@code EditTransactionDescriptor} with fields {@code timestamp} set to null.
     * This is required for testing the parser, as the parser does not parse the timestamp.
     */
    public EditTransactionDescriptorBuilder withoutTimestamp() {
        descriptor.setTimestamp(null);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withAmount(String amount) {
        try {
            descriptor.setAmount(new Amount(amount));
        } catch (Exception e) {
            //
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code PayeeName} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withPayeeName(String payeeName) {
        descriptor.setPayeeName(new Name(payeeName));
        return this;
    }


    /**
     * Sets the {@code Timestamp} of the {@code EditTransactionDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withTimestamp(String timestamp) {
        descriptor.setTimestamp(new Timestamp(timestamp));
        return this;
    }

    public EditTransactionDescriptor build() {
        return descriptor;
    }

}
