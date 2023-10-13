package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.numbers.fraction.BigFraction;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.model.transaction.expense.Weight;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;

class TransactionTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Transaction transaction = new TransactionBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> transaction.getExpenses().remove(0));
    }

    @Test
    public void isSameTransaction() {
        // same values but different objects -> returns true
        Transaction transaction = new TransactionBuilder().build();
        assertTrue(transaction.isSameTransaction(new TransactionBuilder().build()));

        // same object -> returns true
        assertTrue(transaction.isSameTransaction(transaction));

        // null -> returns false
        assertFalse(transaction.isSameTransaction(null));

        // different amount -> returns false
        assertFalse(transaction.isSameTransaction(new TransactionBuilder().withAmount("100").build()));

        // different description -> returns false
        assertFalse(transaction.isSameTransaction(new TransactionBuilder().withDescription("Description").build()));

        // different payee -> returns false
        assertFalse(transaction.isSameTransaction(new TransactionBuilder().withPayee(TypicalPersons.BOB).build()));

        // different expenses -> returns false
        Set<Expense> expenses = Set.of(new Expense(new Name("Bob"), new Weight("1")));
        assertFalse(transaction.isSameTransaction(new TransactionBuilder().withExpenses(expenses).build()));
    }

    @Test
    public void equals() {
        // same values but different objects -> returns false
        Transaction transaction = new TransactionBuilder().build();
        assertEquals(transaction, new TransactionBuilder().build());

        // same object -> returns true
        assertEquals(transaction, transaction);

        // null -> returns false
        assertNotEquals(null, transaction);

        // different types -> returns false
        assertNotEquals(transaction, 5.0f);

        // different amount -> returns false
        assertNotEquals(transaction, new TransactionBuilder().withAmount("100").build());

        // different description -> returns false
        assertNotEquals(transaction, new TransactionBuilder().withDescription("Description").build());

        // different payee -> returns false
        assertNotEquals(transaction, new TransactionBuilder().withPayee(TypicalPersons.BOB).build());

        // different expenses -> returns false
        Set<Expense> expenses = Set.of(new Expense(new Name("Bob"), new Weight("1")));
        assertNotEquals(transaction, new TransactionBuilder().withExpenses(expenses).build());
    }

    @Test
    public void toStringTest() {
        Transaction transaction = new TransactionBuilder().build();
        String expected = Transaction.class.getCanonicalName() + "{amount=" + transaction.getAmount()
            + ", description=" + transaction.getDescription() + ", payee=" + transaction.getPayee()
            + ", expenses=" + transaction.getExpenses() + "}";
        assertEquals(expected, transaction.toString());
    }

    @Test
    public void isPersonInvolved_expenseWithPersonNotInTransaction_returnsFalse() {
        Set<Expense> expenses = Set.of(new Expense(new Name("Alice"), new Weight("1")));
        Transaction transaction = new TransactionBuilder().withExpenses(expenses).build();
        Person person = new PersonBuilder().withName("Bob").build();
        assertFalse(transaction.isPersonInvolved(person));
    }

    @Test
    public void isPersonInvolved_expenseWithPersonInTransaction_returnsTrue() {
        Set<Expense> expenses = Set.of(new Expense(new Name("Alice"), new Weight("1")));
        Transaction transaction = new TransactionBuilder().withExpenses(expenses).build();
        Person person = new PersonBuilder().withName("Alice").build();
        assertTrue(transaction.isPersonInvolved(person));
    }

    @Test
    public void getPortion_singleExpense_returnsCorrectPortion() {
        Set<Expense> expenses = Set.of(new Expense(new Name("Alice"), new Weight("1")));
        Transaction transaction = new TransactionBuilder().withAmount("100").withExpenses(expenses).build();
        Person person = new PersonBuilder().withName("Alice").build();
        BigFraction expectedPortion = BigFraction.of(100, 1);
        assertEquals(expectedPortion, transaction.getPortion(person));
    }

    @Test
    public void getPortion_multipleExpenses_returnsCorrectPortion() {
        Set<Expense> expenses = Set.of(
            new Expense(new Name("Alice"), new Weight("1")),
            new Expense(new Name("Bob"), new Weight("2")),
            new Expense(new Name("Charlie"), new Weight("3"))
        );
        Transaction transaction = new TransactionBuilder().withAmount("600").withExpenses(expenses).build();
        assertEquals(BigFraction.of(100, 1), transaction.getPortion(new PersonBuilder().withName("Alice").build()));
        assertEquals(BigFraction.of(200, 1), transaction.getPortion(new PersonBuilder().withName("Bob").build()));
        assertEquals(BigFraction.of(300, 1), transaction.getPortion(new PersonBuilder().withName("Charlie").build()));
    }

    @Test
    public void getAllPortions_singleExpense_returnsCorrectPortion() {
        Set<Expense> expenses = Set.of(new Expense(new Name("Alice"), new Weight("1")));
        Transaction transaction = new TransactionBuilder().withAmount("100").withExpenses(expenses).build();
        Person person = new PersonBuilder().withName("Alice").build();
        Map<Name, BigFraction> expectedPortions = new HashMap<>();
        expectedPortions.put(person.getName(), BigFraction.of(100, 1));
        assertEquals(expectedPortions, transaction.getAllPortions());
    }

    @Test
    public void getAllPortions_multipleExpenses_returnsCorrectPortion() {
        Set<Expense> expenses = Set.of(
            new Expense(new Name("Alice"), new Weight("1")),
            new Expense(new Name("Bob"), new Weight("2")),
            new Expense(new Name("Charlie"), new Weight("3"))
        );
        Transaction transaction = new TransactionBuilder().withAmount("600").withExpenses(expenses).build();
        Map<Name, BigFraction> expectedPortions = new HashMap<>();
        expectedPortions.put(new Name("Alice"), BigFraction.of(100, 1));
        expectedPortions.put(new Name("Bob"), BigFraction.of(200, 1));
        expectedPortions.put(new Name("Charlie"), BigFraction.of(300, 1));
        assertEquals(expectedPortions, transaction.getAllPortions());
    }
}
