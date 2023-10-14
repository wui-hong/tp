package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.CollectionUtil.requireNonEmptyCollection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.numbers.fraction.BigFraction;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.expense.Expense;

/**
 * Represents a Transaction in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {

    // Data fields
    private final Amount amount;
    private final Description description;
    private final Name payeeName;
    private final Set<Expense> expenses = new HashSet<>();

    /**
     * Internal timestamp used for uniquely identifying transactions.
     **/
    private final Timestamp timestamp;

    /**
     * Every field must be present and not null.
     */
    public Transaction(Amount amount, Description description, Name payeeName, Set<Expense> expenses) {
        this(amount, description, payeeName, expenses, Timestamp.now());
    }

    /**
     * Every field must be present and not null.
     */
    public Transaction(Amount amount, Description description, Name payeeName, Set<Expense> expenses,
                       Timestamp timestamp) {
        requireAllNonNull(amount, description, payeeName);
        requireNonEmptyCollection(expenses);
        this.amount = amount;
        this.description = description;
        this.payeeName = payeeName;
        this.expenses.addAll(expenses);
        this.timestamp = timestamp;
    }

    public Amount getAmount() {
        return amount;
    }

    public Description getDescription() {
        return description;
    }

    public Name getPayeeName() {
        return payeeName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Expense> getExpenses() {
        return Collections.unmodifiableSet(expenses);
    }

    /**
     * Returns the portion of the transaction that the person has to pay.
     *
     * @param personName the name of the person
     */
    public BigFraction getPortion(Name personName) {
        BigFraction totalWeight = getTotalWeight();
        return expenses.stream()
            .filter(expense -> expense.getPersonName().equals(personName))
            .map(expenses -> expenses.getWeight().value.multiply(this.amount.amount).divide(totalWeight))
            .reduce(BigFraction.ZERO, BigFraction::add);
    }

    /**
     * Returns a map of all the portions each person has to pay for this transaction.
     */
    public Map<Name, BigFraction> getAllPortions() {
        BigFraction totalWeight = getTotalWeight();
        return expenses.stream()
            .collect(
                Collectors.toMap(
                    Expense::getPersonName,
                    expense -> expense.getWeight().value.multiply(this.amount.amount).divide(totalWeight)
                )
            );
    }

    /**
     * Returns true if the person with the given name is involved in this transaction, either as a payer or a payee.
     *
     * @param personName the name of the person
     */
    public boolean isPersonInvolved(Name personName) {
        return getAllInvolvedPersonNames().contains(personName);
    }

    /**
     * Returns the names of all the persons involved in this transaction, either as a payer or a payee.
     */
    public Set<Name> getAllInvolvedPersonNames() {
        Set<Name> names = expenses.stream()
            .map(Expense::getPersonName)
            .collect(Collectors.toSet());
        names.add(payeeName);
        return names;
    }

    /**
     * Returns true if both transactions have the same amount, description, payeeName, expenses and transactions.
     * This defines a weaker notion of equality between two transactions.
     */
    public boolean isSameTransaction(Transaction otherTransaction) {
        if (otherTransaction == this) {
            return true;
        }

        return otherTransaction != null
            && otherTransaction.getAmount().equals(getAmount())
            && otherTransaction.getDescription().equals(getDescription())
            && otherTransaction.getPayeeName().equals(getPayeeName())
            && otherTransaction.getExpenses().equals(getExpenses())
            && otherTransaction.getTimestamp().equals(getTimestamp());
    }

    /**
     * Returns true if both transactions is the same object.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Transaction)) {
            return false;
        }


        Transaction otherTransaction = (Transaction) other;
        return amount.equals(otherTransaction.amount)
                && payeeName.equals(otherTransaction.payeeName)
                && description.equals(otherTransaction.description)
                && expenses.equals(otherTransaction.expenses)
                && timestamp.equals(otherTransaction.timestamp);

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(amount, description, payeeName, expenses);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("amount", amount)
            .add("description", description)
            .add("payeeName", payeeName)
            .add("expenses", expenses)
            .toString();
    }

    private BigFraction getTotalWeight() {
        return expenses.stream()
            .map(expense -> expense.getWeight().value)
            .reduce(BigFraction.ZERO, BigFraction::add);
    }
}
