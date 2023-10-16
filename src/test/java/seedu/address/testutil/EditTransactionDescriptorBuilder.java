package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.expense.Expense;

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
        descriptor.setExpenses(transaction.getExpenses());
    }

    /**
     * Returns an {@code EditTransactionDescriptor} with fields {@code payeeName} and {@code expenses} set to null.
     * This is used for v1.2 testing {@code EditTransactionCommand} without {@code payeeName} and {@code expenses}.
     */
    public EditTransactionDescriptorBuilder withoutPayeeNameAndExpenses() {
        descriptor.setPayeeName(null);
        descriptor.setExpenses(null);
        return this;
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
     * Parses the {@code expenses} into a {@code Set<Expense>}
     * and set it to the {@code EditTransactionDescriptor}.
     * Arguments should be in pairs of {@code name} and {@code weight}.
     */
    public EditTransactionDescriptorBuilder withExpenses(String... expenses) {
        if (expenses.length % 2 != 0) {
            throw new IllegalArgumentException("Arguments should be in pairs of name and weight");
        }
        Set<Expense> expenseSet = new HashSet<>();
        for (int i = 0; i < expenses.length; i += 2) {
            expenseSet.add(new ExpenseBuilder().withName(expenses[i]).withWeight(expenses[i + 1]).build());
        }
        descriptor.setExpenses(expenseSet);
        return this;
    }

    public EditTransactionDescriptor build() {
        return descriptor;
    }

}
