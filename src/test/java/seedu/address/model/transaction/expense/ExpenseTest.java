package seedu.address.model.transaction.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;

class ExpenseTest {
    @Test
    public void isSameExpense() {
        Name name = new Name("Alice");
        Weight weight = new Weight("10.0");
        Expense expense = new Expense(name, weight);

        // same object -> returns true
        assertTrue(expense.isSameExpense(expense));

        // null -> returns false
        assertFalse(expense.isSameExpense(null));

        // different attributes -> returns false
        Name editedName = new Name("Bob");
        Weight editedWeight = new Weight("20.0");
        assertFalse(expense.isSameExpense(new Expense(name, editedWeight)));
        assertFalse(expense.isSameExpense(new Expense(editedName, weight)));
        assertFalse(expense.isSameExpense(new Expense(name, editedWeight)));
    }

    @Test
    public void equals() {
        Name name = new Name("Alice");
        Weight weight = new Weight("10.0");
        Expense expense = new Expense(name, weight);

        assertEquals(expense, expense);
        assertEquals(expense, new Expense(name, weight));

        Name otherName = new Name("Bob");
        Weight otherWeight = new Weight("20.0");
        Expense otherExpense = new Expense(otherName, otherWeight);

        assertNotEquals(expense, otherExpense);
        assertNotEquals(expense, null);
    }

    @Test
    public void hashcode() {
        Name name = new Name("Alice");
        Weight weight = new Weight("10.0");
        Expense expense = new Expense(name, weight);
        Expense otherExpense = new Expense(name, weight);

        assertEquals(expense, otherExpense);
    }
}
