package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.transaction.expense.Expense;

/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class TypicalExpenses {
    public static final Expense ALICE_EXPENSE = new ExpenseBuilder().withName(
            TypicalPersons.ALICE.getName().fullName).withWeight("2").build();

    public static final Expense BENSON_EXPENSE = new ExpenseBuilder().withName(
            TypicalPersons.BENSON.getName().fullName).withWeight("4").build();

    public static final Expense CARL_EXPENSE = new ExpenseBuilder().withName(
            TypicalPersons.CARL.getName().fullName).withWeight("6").build();

    public static final Expense SELF_EXPENSE = new ExpenseBuilder().withName(
            Name.SELF.fullName).withWeight("8").build();
}
