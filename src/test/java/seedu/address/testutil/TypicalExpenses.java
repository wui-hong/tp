package seedu.address.testutil;

import seedu.address.model.transaction.expense.Expense;

/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class TypicalExpenses {
    public static final Expense ALICE_EXPENSE = new ExpenseBuilder().withName(
            TypicalPersons.ALICE.getName().fullName).withWeight("2").build();

    public static final Expense BOB_EXPENSE = new ExpenseBuilder().withName(
            TypicalPersons.BOB.getName().fullName).withWeight("4").build();

    public static final Expense CARL_EXPENSE = new ExpenseBuilder().withName(
            TypicalPersons.CARL.getName().fullName).withWeight("6").build();

}
