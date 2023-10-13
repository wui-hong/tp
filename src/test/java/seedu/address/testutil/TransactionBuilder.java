package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Timestamp;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.expense.Expense;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {

    public static final String DEFAULT_AMOUNT = "12.34";
    public static final String DEFAULT_DESCRIPTION = "Mala Xiang Guo at Clementi Mall on 12 Oct 2023";
    public static final String DEFAULT_PAYEE_NAME = TypicalPersons.ALICE.getName().fullName;

    private Amount amount;
    private Description description;
    private Name payeeName;
    private Set<Expense> expenses;
    private Timestamp timestamp;

    /**
     * Creates a {@code TransactionBuilder} with the default details.
     */
    public TransactionBuilder() {
        amount = new Amount(DEFAULT_AMOUNT);
        description = new Description(DEFAULT_DESCRIPTION);
        payeeName = new Name(DEFAULT_PAYEE_NAME);
        expenses = new HashSet<>();
        timestamp = Timestamp.now();
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code transactionToCopy}.
     */
    public TransactionBuilder(Transaction transactionToCopy) {
        amount = transactionToCopy.getAmount();
        description = transactionToCopy.getDescription();
        payeeName = transactionToCopy.getPayeeName();
        expenses = new HashSet<>(transactionToCopy.getExpenses());
    }

    /**
     * Sets the {@code Amount} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code PayeeName} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withPayeeName(String payeeName) {
        this.payeeName = new Name(payeeName);
        return this;
    }

    /**
     * Sets the {@code Expenses} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withTimestamp(String timestamp) {
        this.timestamp = new Timestamp(timestamp);
        return this;
    }

    public Transaction build() {
        return new Transaction(amount, description, payeeName, expenses, timestamp);
    }
}
