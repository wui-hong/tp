package seedu.spendnsplit.testutil;

import seedu.spendnsplit.logic.descriptors.TransactionDescriptor;
import seedu.spendnsplit.logic.parser.exceptions.ParseException;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.Amount;
import seedu.spendnsplit.model.transaction.Description;
import seedu.spendnsplit.model.transaction.Timestamp;
import seedu.spendnsplit.model.transaction.Transaction;

/**
 * A utility class to help with building TransactionDescriptor objects.
 */
public class TransactionDescriptorBuilder {

    private TransactionDescriptor descriptor;

    public TransactionDescriptorBuilder() {
        descriptor = new TransactionDescriptor();
    }

    public TransactionDescriptorBuilder(TransactionDescriptor descriptor) {
        this.descriptor = new TransactionDescriptor(descriptor);
    }

    /**
     * Returns an {@code TransactionDescriptor} with fields containing {@code transaction}'s details
     */
    public TransactionDescriptorBuilder(Transaction transaction) {
        descriptor = new TransactionDescriptor();
        descriptor.setAmount(transaction.getAmount());
        descriptor.setDescription(transaction.getDescription());
        descriptor.setPayeeName(transaction.getPayeeName());
        descriptor.setTimestamp(transaction.getTimestamp());
    }

    /**
     * Returns an {@code TransactionDescriptor} with fields {@code timestamp} set to null.
     * This is required for testing the parser, as the parser does not parse the timestamp.
     */
    public TransactionDescriptorBuilder withoutTimestamp() {
        descriptor.setTimestamp(null);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code TransactionDescriptor} that we are building.
     */
    public TransactionDescriptorBuilder withAmount(String amount) {
        try {
            descriptor.setAmount(new Amount(amount));
        } catch (ParseException e) {
            assert false;
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code TransactionDescriptor} that we are building.
     */
    public TransactionDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code PayeeName} of the {@code TransactionDescriptor} that we are building.
     */
    public TransactionDescriptorBuilder withPayeeName(String payeeName) {
        descriptor.setPayeeName(new Name(payeeName));
        return this;
    }


    /**
     * Sets the {@code Timestamp} of the {@code TransactionDescriptor} that we are building.
     */
    public TransactionDescriptorBuilder withTimestamp(String timestamp) {
        descriptor.setTimestamp(new Timestamp(timestamp));
        return this;
    }

    public TransactionDescriptor build() {
        return descriptor;
    }

}
