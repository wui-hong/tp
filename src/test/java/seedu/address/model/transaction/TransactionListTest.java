package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.transaction.exceptions.TransactionNotFoundException;

class TransactionListTest {

    // ==================== Unit Tests ====================
    private final TransactionList transactionList = new TransactionList();

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
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(transactionStubOne);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionIsDifferentTransaction_success() {
        transactionList.add(transactionStubOne);
        transactionList.setTransaction(transactionStubOne, transactionStubTwo);
        TransactionList expectedTransactionList = new TransactionList();
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
        TransactionList expectedTransactionList = new TransactionList();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((TransactionList) null));
    }

    @Test
    public void setTransactions_transactionList_replacesOwnListWithProvidedTransactionList() {
        transactionList.add(transactionStubOne);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(transactionStubTwo);
        transactionList.setTransactions(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        transactionList.add(transactionStubOne);
        List<Transaction> transactionCollectionsList = Collections.singletonList(transactionStubTwo);
        transactionList.setTransactions(transactionCollectionsList);
        TransactionList expectedTransactionList = new TransactionList();
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
        assertFalse(transactionList.equals(notATransactionList));
    }

    @Test
    void testEquals_sameObject_returnsTrue() {
        assertTrue(transactionList.equals(transactionList));
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

    }

}

