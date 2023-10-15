package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenses.ALICE_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.BENSON_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.CARL_EXPENSE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.numbers.fraction.BigFraction;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TransactionBuilder;

class TransactionTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Transaction transaction = new TransactionBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> transaction.getExpenses().remove(0));
    }

    @Test
    public void isSameTransaction() {
        Transaction transaction = new TransactionBuilder().build();

        // same values but different objects -> returns true
        assertTrue(transaction.isSameTransaction(
                new TransactionBuilder().withTimestamp(transaction.getTimestamp().toString()).build()));

        // different timestamps -> returns false
        assertFalse(transaction.isSameTransaction(
                new TransactionBuilder().withTimestamp("2000-10-13T12:34:56.789").build()));

        // same object -> returns true
        assertTrue(transaction.isSameTransaction(transaction));

        // null -> returns false
        assertFalse(transaction.isSameTransaction(null));

        // different amount -> returns false
        assertFalse(transaction.isSameTransaction(new TransactionBuilder().withAmount("100").build()));

        // different description -> returns false
        assertFalse(transaction.isSameTransaction(new TransactionBuilder().withDescription("Description").build()));

        // different payeeName -> returns false
        assertFalse(transaction.isSameTransaction(
                new TransactionBuilder().withPayeeName(BOB.getName().fullName).build()));

        // different expenses -> returns false
        Set<Expense> expenses = Set.of(BENSON_EXPENSE);
        assertFalse(transaction.isSameTransaction(new TransactionBuilder().withExpenses(expenses).build()));
    }

    @Test
    public void equals() {
        Transaction transaction = new TransactionBuilder().build();

        // same values but different objects -> returns true
        assertEquals(transaction, new TransactionBuilder().withTimestamp(
                transaction.getTimestamp().toString()).build());

        // different timestamps -> returns false
        assertNotEquals(transaction, new TransactionBuilder().withTimestamp("2023-10-13T12:34:56.789").build());

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
        assertNotEquals(transaction, new TransactionBuilder().withPayeeName(BOB.getName().fullName).build());

        // different expenses -> returns false
        Set<Expense> expenses = Set.of(BENSON_EXPENSE);
        assertNotEquals(transaction, new TransactionBuilder().withExpenses(expenses).build());
    }

    @Test
    public void toStringTest() {
        Transaction transaction = new TransactionBuilder().build();
        String expected = Transaction.class.getCanonicalName() + "{amount=" + transaction.getAmount()
            + ", description=" + transaction.getDescription() + ", payeeName=" + transaction.getPayeeName()
            + ", expenses=" + transaction.getExpenses() + "}";
        assertEquals(expected, transaction.toString());
    }

    @Test
    public void isPersonInvolved_payerInTransaction_returnsTrue() {
        Set<Expense> expenses = Set.of(ALICE_EXPENSE, CARL_EXPENSE);
        Transaction transaction = new TransactionBuilder()
            .withPayeeName(BOB.getName().fullName).withExpenses(expenses).build();
        assertTrue(transaction.isPersonInvolved(ALICE.getName()));
        assertTrue(transaction.isPersonInvolved(CARL.getName()));
    }

    @Test
    public void isPersonInvolved_payeeInTransaction_returnsTrue() {
        Set<Expense> expenses = Set.of(ALICE_EXPENSE, CARL_EXPENSE);
        Transaction transaction = new TransactionBuilder()
            .withPayeeName(BOB.getName().fullName).withExpenses(expenses).build();
        assertTrue(transaction.isPersonInvolved(BOB.getName()));
    }

    @Test
    public void isPersonInvolved_personNotInTransaction_returnsFalse() {
        Set<Expense> expenses = Set.of(ALICE_EXPENSE, CARL_EXPENSE);
        Transaction transaction = new TransactionBuilder()
            .withPayeeName(BOB.getName().fullName).withExpenses(expenses).build();
        assertFalse(transaction.isPersonInvolved(DANIEL.getName()));
    }

    @Test
    public void getAllInvolvedPersonNames_transactionWithPayerAndPayee_returnsCorrectNames() {
        Set<Expense> expenses = Set.of(ALICE_EXPENSE, CARL_EXPENSE);
        Transaction transaction = new TransactionBuilder()
            .withPayeeName(BENSON.getName().fullName).withExpenses(expenses).build();
        assertEquals(Set.of(ALICE.getName(), CARL.getName(), BENSON.getName()),
            transaction.getAllInvolvedPersonNames());
    }

    @Test
    public void getPortion_singleExpense_returnsCorrectPortion() {
        Set<Expense> expenses = Set.of(ALICE_EXPENSE);
        Transaction transaction = new TransactionBuilder().withAmount("100").withExpenses(expenses).build();
        BigFraction expectedPortion = BigFraction.of(100, 1);
        assertEquals(expectedPortion, transaction.getPortion(ALICE.getName()));
    }

    @Test
    public void getPortion_multipleExpenses_returnsCorrectPortion() {
        Set<Expense> expenses = Set.of(
            ALICE_EXPENSE,
            BENSON_EXPENSE,
            CARL_EXPENSE
        );
        Transaction transaction = new TransactionBuilder().withAmount("600").withExpenses(expenses).build();
        assertEquals(BigFraction.of(100, 1),
                transaction.getPortion(ALICE.getName()));
        assertEquals(BigFraction.of(200, 1),
                transaction.getPortion(BENSON.getName()));
        assertEquals(BigFraction.of(300, 1),
                transaction.getPortion(CARL.getName()));
    }

    @Test
    public void getAllPortions_singleExpense_returnsCorrectPortion() {
        Set<Expense> expenses = Set.of(ALICE_EXPENSE);
        Transaction transaction = new TransactionBuilder().withAmount("100").withExpenses(expenses).build();
        Person person = new PersonBuilder().withName(ALICE_EXPENSE.getPersonName().fullName).build();
        Map<Name, BigFraction> expectedPortions = new HashMap<>();
        expectedPortions.put(person.getName(), BigFraction.of(100, 1));
        assertEquals(expectedPortions, transaction.getAllPortions());
    }

    @Test
    public void getAllPortions_multipleExpenses_returnsCorrectPortion() {
        Set<Expense> expenses = Set.of(
            ALICE_EXPENSE,
            BENSON_EXPENSE,
            CARL_EXPENSE
        );
        Transaction transaction = new TransactionBuilder().withAmount("1200").withExpenses(expenses).build();
        Map<Name, BigFraction> expectedPortions = new HashMap<>();
        expectedPortions.put(new Name(ALICE.getName().toString()), BigFraction.of(200, 1));
        expectedPortions.put(new Name(BENSON.getName().toString()), BigFraction.of(400, 1));
        expectedPortions.put(new Name(CARL.getName().toString()), BigFraction.of(600, 1));
        assertEquals(expectedPortions, transaction.getAllPortions());
    }

    @Test
    public void isValid() {
        assertFalse(new TransactionBuilder().build().isValid(Set.of()));
        assertFalse(new TransactionBuilder().withPayeeName(Name.SELF.fullName).build().isValid(Set.of()));
        assertFalse(new TransactionBuilder().withPayeeName(Name.SELF.fullName)
                .withAmount("0").build().isValid(Set.of()));
        assertFalse(new TransactionBuilder().withExpenses(Set.of(new ExpenseBuilder()
                .withName(Name.SELF.fullName).build(), ALICE_EXPENSE)).build().isValid(Set.of()));
        assertFalse(new TransactionBuilder().withPayeeName(Name.SELF.fullName).withExpenses(Set.of(new ExpenseBuilder()
                .withName(Name.SELF.fullName).build(), ALICE_EXPENSE)).build().isValid(Set.of()));
        assertFalse(new TransactionBuilder().withPayeeName(Name.SELF.fullName).withExpenses(Set.of(new ExpenseBuilder()
                .withName(Name.SELF.fullName).withWeight("0").build(), ALICE_EXPENSE)).build()
                .isValid(Set.of(ALICE.getName())));
    }
}
