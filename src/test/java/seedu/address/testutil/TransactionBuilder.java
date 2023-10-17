package seedu.address.testutil;


import static seedu.address.testutil.TypicalExpenses.ALICE_EXPENSE;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Timestamp;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.model.transaction.expense.Weight;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {

    public static final String DEFAULT_AMOUNT = "12.34";
    public static final String DEFAULT_DESCRIPTION = "Mala Xiang Guo at Clementi Mall on 12 Oct 2023";
    public static final String DEFAULT_PAYEE_NAME = "Default Payee Name";

    // necessary for testing of EditTransactionCommand, to test actual vs expected transactions
    public static final String DEFAULT_TIMESTAMP = "2023-10-12T12:34:56.789";

    public static final Set<Expense> DEFAULT_EXPENSES = new HashSet<>(
            List.of(new Expense(TypicalPersons.BOB.getName(), new Weight("1.0"))));

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
        expenses = new HashSet<>(Collections.singletonList(ALICE_EXPENSE));
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
        timestamp = transactionToCopy.getTimestamp();
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
