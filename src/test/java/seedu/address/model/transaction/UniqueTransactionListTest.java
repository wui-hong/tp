package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.transaction.exceptions.TransactionNotFoundException;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.testutil.TypicalPersons;

class UniqueTransactionListTest {

    // ==================== Unit Tests ====================
    private final UniqueTransactionList transactionList = new UniqueTransactionList();

    private final TransactionStub transactionStubOne = new TransactionStub();

    private final TransactionStub transactionStubTwo = new TransactionStub();
    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null));
    }

    @Test
    public void add_validTransaction_success() {
        transactionList.add(transactionStubOne);
        assertTrue(transactionList.contains(transactionStubOne));
    }

    @Test
    public void setTransaction_nullTargetTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(null, new TransactionStub()));
    }

    @Test
    public void setTransaction_nullEditedTransaction_throwsNullPointerException() {
        transactionList.add(transactionStubOne);
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(transactionStubOne, null));
    }

    @Test
    public void setTransaction_targetTransactionNotInList_throwsTransactionNotFoundException() {
        transactionList.add(transactionStubOne);
        assertThrows(TransactionNotFoundException.class, () ->
                transactionList.setTransaction(transactionStubTwo, transactionStubOne));
    }

    @Test
    public void setTransaction_editedTransactionIsSameTransaction_success() {
        transactionList.add(transactionStubOne);
        transactionList.setTransaction(transactionStubOne, transactionStubOne);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionStubOne);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionIsDifferentTransaction_success() {
        transactionList.add(transactionStubOne);
        transactionList.setTransaction(transactionStubOne, transactionStubTwo);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionStubTwo);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.remove(transactionStubOne));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        transactionList.add(transactionStubOne);
        transactionList.remove(transactionStubOne);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((UniqueTransactionList) null));
    }

    @Test
    public void setTransactions_transactionList_replacesOwnListWithProvidedTransactionList() {
        transactionList.add(transactionStubOne);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionStubTwo);
        transactionList.setTransactions(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        transactionList.add(transactionStubOne);
        List<Transaction> transactionCollectionsList = Collections.singletonList(transactionStubTwo);
        transactionList.setTransactions(transactionCollectionsList);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionStubTwo);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> transactionList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    void testEquals_notTransactionList_returnsFalse() {
        Object notATransactionList = new Object();
        assertNotEquals(transactionList, notATransactionList);
    }

    @Test
    void testEquals_sameObject_returnsTrue() {
        assertEquals(transactionList, transactionList);
    }

    @Test
    void testHashCode() {
        assertEquals(transactionList.hashCode(), transactionList.hashCode());
    }

    @Test
    void testToString() {
        assertEquals(transactionList.asUnmodifiableObservableList().toString(), transactionList.toString());
    }

    private static class TransactionStub extends Transaction {

        private static final Amount amount = new Amount("0");
        private static final Description description = new Description("Stub");
        private static final Person payee = TypicalPersons.ALICE;
        private static final Set<Expense> expenses = Collections.emptySet();

        public TransactionStub() {
            super(amount, description, payee, expenses);
        }
    }

}

