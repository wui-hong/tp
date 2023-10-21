package seedu.address.model.transaction.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ExpenseBuilder;

class ExpenseTest {
    @Test
    public void isSameExpense() {
        Expense expense = new ExpenseBuilder().build();

        // same object -> returns true
        assertTrue(expense.isSameExpense(new ExpenseBuilder().build()));

        // null -> returns false
        assertFalse(expense.isSameExpense(null));

        // different attributes -> returns false
        String editedName = "Daniel";
        String editedWeight = "10";
        assertFalse(expense.isSameExpense(new ExpenseBuilder()
                .withName(editedName).withWeight(editedWeight).build()));
        assertFalse(expense.isSameExpense(new ExpenseBuilder().withName(editedName).build()));
        assertFalse(expense.isSameExpense(new ExpenseBuilder().withWeight(editedWeight).build()));
    }

    @Test
    public void equals() {
        String name = "Daniel";
        String weight = "10";
        Expense expense = new ExpenseBuilder()
                .withName(name).withWeight(weight).build();

        assertEquals(expense, expense);
        assertEquals(expense, new ExpenseBuilder()
                .withName(name).withWeight(weight).build());

        String otherName = "Elle";
        String otherWeight = "20.0";
        Expense differentNameAndWeightExpense = new ExpenseBuilder()
                .withName(otherName).withWeight(otherWeight).build();

        assertNotEquals(expense, differentNameAndWeightExpense);
        assertNotEquals(expense, null);

        Expense sameNameDifferentWeightExpense = new ExpenseBuilder()
                .withName(name).withWeight(otherWeight).build();
        assertNotEquals(expense, sameNameDifferentWeightExpense);

        Expense differentNameSameWeightExpense = new ExpenseBuilder()
                .withName(otherName).withWeight(weight).build();
        assertNotEquals(expense, differentNameSameWeightExpense);
    }

    @Test
    public void hashcode() {
        String name = "Daniel";
        String weight = "10";
        Expense expense = new ExpenseBuilder()
                .withName(name).withWeight(weight).build();
        Expense otherExpense = new ExpenseBuilder()
                .withName(name).withWeight(weight).build();

        assertEquals(expense, otherExpense);
    }
}
