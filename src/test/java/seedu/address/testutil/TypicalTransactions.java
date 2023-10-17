package seedu.address.testutil;

import static seedu.address.testutil.TypicalExpenses.ALICE_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.BENSON_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.SELF_EXPENSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.numbers.fraction.BigFraction;

import seedu.address.model.person.Name;
import seedu.address.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {
    public static final Transaction LUNCH = new TransactionBuilder().withAmount("60")
            .withDescription("Group Project Lunch").withPayeeName(Name.SELF.fullName)
            .withExpenses(Set.of(ALICE_EXPENSE, BENSON_EXPENSE)).withTimestamp("2023-10-13T12:34:56.789").build();
    public static final Transaction DINNER = new TransactionBuilder().withAmount("90").withDescription("Hall Dinner")
            .withPayeeName(TypicalPersons.BENSON.getName().fullName)
            .withExpenses(Set.of(BENSON_EXPENSE, SELF_EXPENSE)).withTimestamp("2023-10-13T12:34:56.790").build();


    public static final Transaction RENT = new TransactionBuilder().withAmount("600")
            .withDescription("Shared Dorm Rent").withPayeeName(TypicalPersons.BENSON.getName().fullName).withExpenses(
                    Set.of(new ExpenseBuilder(BENSON_EXPENSE).withWeight(BigFraction.ONE.toString()).build(),
                           new ExpenseBuilder(SELF_EXPENSE).withWeight(BigFraction.ONE.toString()).build()))
            .withTimestamp("2023-10-13T12:34:56.791").build();

    public static final Transaction TRANSPORTATION = new TransactionBuilder().withAmount("100")
            .withDescription("Carpool Subscription").withPayeeName(Name.SELF.fullName).withExpenses(
                    Set.of(new ExpenseBuilder(BENSON_EXPENSE).withWeight(BigFraction.ONE.toString()).build(),
                            new ExpenseBuilder(SELF_EXPENSE).withWeight(BigFraction.ONE.toString()).build()))
            .withTimestamp("2023-10-13T12:34:56.792").build();

    public static final Transaction GROCERIES = new TransactionBuilder().withAmount("120")
            .withDescription("Groceries").withPayeeName(Name.SELF.fullName).withExpenses(
                    Set.of(new ExpenseBuilder(ALICE_EXPENSE).withWeight(BigFraction.ONE.toString()).build(),
                            new ExpenseBuilder(BENSON_EXPENSE).withWeight(BigFraction.ONE.toString()).build(),
                            new ExpenseBuilder(SELF_EXPENSE).withWeight(BigFraction.ONE.toString()).build()))
            .withTimestamp("2023-10-13T12:34:56.793").build();

    public static final Transaction BREAKFAST_WITH_ALICE = new TransactionBuilder().withAmount("10")
            .withDescription("Breakfast with Alice").withPayeeName(Name.SELF.fullName).withExpenses(
                    Set.of(new ExpenseBuilder(ALICE_EXPENSE).withWeight("2.00").build()))
            .withTimestamp("2023-10-13T12:34:56.794").build();

    public static List<Transaction> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(LUNCH, DINNER, RENT, TRANSPORTATION, BREAKFAST_WITH_ALICE));
    }
}
