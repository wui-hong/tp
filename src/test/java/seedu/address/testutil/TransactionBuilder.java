package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.model.transaction.expense.Weight;

/**
 * A utility class to help with building Person objects.
 */
public class TransactionBuilder {

    public static final String DEFAULT_AMOUNT = "12.34";
    public static final String DEFAULT_DESCRIPTION = "Mala Xiang Guo at Clementi Mall on 12 Oct 2023";
    public static final Person DEFAULT_PAYEE = TypicalPersons.ALICE;

    public static final Set<Expense> DEFAULT_EXPENSES = new HashSet<>(List.of(new Expense(TypicalPersons.BOB.getName(), new Weight("1.0)"))));

    private Amount amount;
    private String description;
    private Person payee;
    private Set<Expense> expenses;

    /**
     * Creates a {@code TransactionBuilder} with the default details.
     */
    public TransactionBuilder() {
        amount = new Amount(DEFAULT_AMOUNT);
        description = DEFAULT_DESCRIPTION;
        payee = DEFAULT_PAYEE;
        expenses = new HashSet<>();
    }

    /**
     * Initializes the TransactionBuilder with the data of {@code transactionToCopy}.
     */
    public TransactionBuilder(Transaction transactionToCopy) {
        amount = transactionToCopy.getAmount();
        description = transactionToCopy.getDescription();
        payee = transactionToCopy.getPayee();
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
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code Payee} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withPayee(Person payee) {
        this.payee = payee;
        return this;
    }

    /**
     * Sets the {@code Expenses} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
        return this;
    }

    public Transaction build() {
        return new Transaction(amount, description, payee, expenses);
    }
}
