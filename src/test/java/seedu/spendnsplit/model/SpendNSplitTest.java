package seedu.spendnsplit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.spendnsplit.testutil.Assert.assertThrows;
import static seedu.spendnsplit.testutil.TypicalPersons.ALICE;
import static seedu.spendnsplit.testutil.TypicalPersons.BENSON;
import static seedu.spendnsplit.testutil.TypicalSpendNSplitBook.getTypicalSpendNSplitBook;
import static seedu.spendnsplit.testutil.TypicalTransactions.LUNCH;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.person.exceptions.DuplicatePersonException;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.model.transaction.exceptions.DuplicateTransactionException;
import seedu.spendnsplit.testutil.PersonBuilder;
import seedu.spendnsplit.testutil.SpendNSplitBookBuilder;
import seedu.spendnsplit.testutil.TransactionBuilder;

public class SpendNSplitTest {

    private final SpendNSplit spendNSplit = new SpendNSplit();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), spendNSplit.getPersonList());
        assertEquals(Collections.emptyList(), spendNSplit.getTransactionList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendNSplit.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlySpendNSplitBook_replacesData() {
        SpendNSplit newData = getTypicalSpendNSplitBook();
        spendNSplit.resetData(newData);
        assertEquals(newData, spendNSplit);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Transaction> newTransactions = List.of();
        SpendNSplitBookStub newData = new SpendNSplitBookStub(newPersons, newTransactions);

        assertThrows(DuplicatePersonException.class, () -> spendNSplit.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendNSplit.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInSpendNSplitBook_returnsFalse() {
        assertFalse(spendNSplit.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInSpendNSplitBook_returnsTrue() {
        spendNSplit.addPerson(ALICE);
        assertTrue(spendNSplit.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInSpendNSplitBook_returnsTrue() {
        spendNSplit.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(spendNSplit.hasPerson(editedAlice));
    }

    @Test
    public void resetData_withDuplicateTransactions_throwsDuplicateTransactionException() {
        // Two transactions with the same identity fields
        Transaction editedDinner = new TransactionBuilder(LUNCH)
            .withAmount(LUNCH.getAmount().toString())
            .withDescription(LUNCH.getDescription().toString())
            .withPayeeName(LUNCH.getPayeeName().toString())
            .withPortions(LUNCH.getPortions())
            .withTimestamp(LUNCH.getTimestamp().toString())
            .build();
        List<Person> newPersons = List.of();
        List<Transaction> newTransactions = Arrays.asList(LUNCH, editedDinner);
        SpendNSplitBookStub newData = new SpendNSplitBookStub(newPersons, newTransactions);

        assertThrows(DuplicateTransactionException.class, () -> spendNSplit.resetData(newData));
    }

    @Test
    public void hasTransaction_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendNSplit.hasTransaction(null));
    }

    @Test
    public void hasTransaction_transactionNotInSpendNSplitBook_returnsFalse() {
        assertFalse(spendNSplit.hasTransaction(LUNCH));
    }

    @Test
    public void hasTransaction_transactionInSpendNSplitBook_returnsTrue() {
        SpendNSplit testBook = new SpendNSplitBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        testBook.addTransaction(LUNCH);
        assertTrue(testBook.hasTransaction(LUNCH));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> spendNSplit.getPersonList().remove(0));
    }

    @Test
    public void sort() {
        SpendNSplit ab = new SpendNSplitBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withTransaction(LUNCH).build();
        ab.setPersonAscendingBalance();
        assertTrue(ALICE.equals(ab.getPersonList().get(0)));
        ab.setPersonDescendingBalance();
        assertTrue(BENSON.equals(ab.getPersonList().get(0)));
    }

    @Test
    public void toStringMethod() {
        String expected = SpendNSplit.class.getCanonicalName() + "{persons=" + spendNSplit.getPersonList()
                + ", transactions=" + spendNSplit.getTransactionList() + "}";
        assertEquals(expected, spendNSplit.toString());
    }

    /**
     * A stub ReadOnlySpendNSplitBook whose persons list can violate interface constraints.
     */
    private static class SpendNSplitBookStub implements ReadOnlySpendNSplitBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        SpendNSplitBookStub(Collection<Person> persons, Collection<Transaction> transactions) {
            this.persons.setAll(persons);
            this.transactions.setAll(transactions);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Transaction> getTransactionList() {
            return transactions;
        }
    }

}
