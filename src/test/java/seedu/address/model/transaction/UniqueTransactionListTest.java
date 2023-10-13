package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.model.transaction.exceptions.TransactionNotFoundException;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.testutil.TypicalPersons;


class UniqueTransactionListTest {

    // ==================== Unit Tests ====================
    private final UniqueTransactionList transactionList = new UniqueTransactionList();

    private final TransactionStub transactionStub1 = new TransactionStub();

    private final TransactionStub transactionStub2 = new TransactionStub();
    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null));
    }

    @Test
    public void add_validTransaction_success() {
        transactionList.add(transactionStub1);
        assertTrue(transactionList.contains(transactionStub1));
    }

    @Test
    public void setTransaction_nullTargetTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(null, new TransactionStub()));
    }

    @Test
    public void setTransaction_nullEditedTransaction_throwsNullPointerException() {
        transactionList.add(transactionStub1);
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(transactionStub1, null));
    }

    @Test
    public void setTransaction_targetTransactionNotInList_throwsTransactionNotFoundException() {
        transactionList.add(transactionStub1);
        assertThrows(TransactionNotFoundException.class, () ->
                transactionList.setTransaction(transactionStub2, transactionStub1));
    }

    @Test
    public void setTransaction_editedTransactionIsSameTransaction_success() {
        transactionList.add(transactionStub1);
        transactionList.setTransaction(transactionStub1, transactionStub1);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionStub1);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionIsDifferentTransaction_success() {
        transactionList.add(transactionStub1);
        transactionList.setTransaction(transactionStub1, transactionStub2);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionStub2);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.remove(transactionStub1));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        transactionList.add(transactionStub1);
        transactionList.remove(transactionStub1);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((UniqueTransactionList) null));
    }

    @Test
    public void setTransactions_transactionList_replacesOwnListWithProvidedTransactionList() {
        transactionList.add(transactionStub1);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionStub2);
        transactionList.setTransactions(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        transactionList.add(transactionStub1);
        List<Transaction> transactionCollectionsList = Collections.singletonList(transactionStub2);
        transactionList.setTransactions(transactionCollectionsList);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionStub2);
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

    private static class TransactionStub extends Transaction {

        private static final Amount amount = new Amount("0");
        private static final Description description = new Description("Stub");
        private static final Name payeeName = TypicalPersons.ALICE.getName();
        private static final Set<Expense> expenses = Collections.emptySet();

        public TransactionStub() {
            super(amount, description, payeeName, expenses);
        }
    }

}
