package seedu.spendnsplit.logic.descriptors;

import java.util.Objects;
import java.util.Optional;

import seedu.spendnsplit.commons.util.CollectionUtil;
import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.Amount;
import seedu.spendnsplit.model.transaction.Description;
import seedu.spendnsplit.model.transaction.Timestamp;

/**
 * Stores the details to edit the transaction with. Each non-empty field value will replace the
 * corresponding field value of the transaction.
 * Note that "cost" is represented by {@code Amount} named {@code amount} in the model.
 * TransactionDescriptor does not edit and store portions.
 */
public class TransactionDescriptor {
    private Amount amount;
    private Description description;
    private Name payeeName;
    private Timestamp timestamp;

    public TransactionDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code portions} is used internally.
     */
    public TransactionDescriptor(TransactionDescriptor toCopy) {
        setAmount(toCopy.amount);
        setDescription(toCopy.description);
        setPayeeName(toCopy.payeeName);
        setTimestamp(toCopy.timestamp);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(amount, description, payeeName, timestamp);
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Optional<Amount> getAmount() {
        return Optional.ofNullable(amount);
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Optional<Description> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setPayeeName(Name payeeName) {
        this.payeeName = payeeName;
    }

    public Optional<Name> getPayeeName() {
        return Optional.ofNullable(payeeName);
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Optional<Timestamp> getTimestamp() {
        return Optional.ofNullable(timestamp);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionDescriptor)) {
            return false;
        }

        TransactionDescriptor otherEditTransactionDescriptor = (TransactionDescriptor) other;
        return Objects.equals(amount, otherEditTransactionDescriptor.amount)
            && Objects.equals(description, otherEditTransactionDescriptor.description)
            && Objects.equals(payeeName, otherEditTransactionDescriptor.payeeName)
            && Objects.equals(timestamp, otherEditTransactionDescriptor.timestamp);
    }

    /**
     * Note that "cost" is represented by {@code Amount} named {@code amount} in the model.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("timestamp", timestamp)
            .add("cost", amount)
            .add("description", description)
            .add("payeeName", payeeName)
            .toString();
    }
}
