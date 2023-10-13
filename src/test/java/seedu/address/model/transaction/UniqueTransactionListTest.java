package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalTransactions.DINNER;
import static seedu.address.testutil.TypicalTransactions.LUNCH;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.transaction.exceptions.TransactionNotFoundException;


class UniqueTransactionListTest {

    // ==================== Unit Tests ====================
    private final UniqueTransactionList transactionList = new UniqueTransactionList();

    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null));
    }

    @Test
    public void add_validTransaction_success() {
        transactionList.add(LUNCH);
        assertTrue(transactionList.contains(LUNCH));
    }

    @Test
    public void setTransaction_nullTargetTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(null, LUNCH));
    }

    @Test
    public void setTransaction_nullEditedTransaction_throwsNullPointerException() {
        transactionList.add(LUNCH);
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(LUNCH, null));
    }

    @Test
    public void setTransaction_targetTransactionNotInList_throwsTransactionNotFoundException() {
        transactionList.add(LUNCH);
        assertThrows(TransactionNotFoundException.class, () ->
                transactionList.setTransaction(DINNER, LUNCH));
    }

    @Test
    public void setTransaction_editedTransactionIsSameTransaction_success() {
        transactionList.add(LUNCH);
        transactionList.setTransaction(LUNCH, LUNCH);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(LUNCH);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionIsDifferentTransaction_success() {
        transactionList.add(LUNCH);
        transactionList.setTransaction(LUNCH, DINNER);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(DINNER);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.remove(LUNCH));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        transactionList.add(LUNCH);
        transactionList.remove(LUNCH);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((UniqueTransactionList) null));
    }

    @Test
    public void setTransactions_transactionList_replacesOwnListWithProvidedTransactionList() {
        transactionList.add(LUNCH);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(DINNER);
        transactionList.setTransactions(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        transactionList.add(LUNCH);
        List<Transaction> transactionCollectionsList = Collections.singletonList(DINNER);
        transactionList.setTransactions(transactionCollectionsList);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(DINNER);
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
    void hashcode() {
        assertEquals(transactionList.hashCode(), transactionList.hashCode());
    }

    @Test
    void testToString() {
        assertEquals(transactionList.asUnmodifiableObservableList().toString(), transactionList.toString());
    }
}

