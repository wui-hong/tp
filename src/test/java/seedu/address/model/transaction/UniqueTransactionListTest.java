package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPortions.ALICE_PORTION;
import static seedu.address.testutil.TypicalPortions.BENSON_PORTION;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.numbers.fraction.BigFraction;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.model.transaction.exceptions.TransactionNotFoundException;
import seedu.address.model.transaction.portion.Portion;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;


class UniqueTransactionListTest {

    // ==================== Unit Tests ====================
    private final UniqueTransactionList transactionList = new UniqueTransactionList();

    private final TransactionWithAliceStub transactionWithAliceStub = new TransactionWithAliceStub();

    private final TransactionWithBobStub transactionWithBobStub = new TransactionWithBobStub();
    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null));
    }

    @Test
    public void add_validTransaction_success() {
        transactionList.add(transactionWithAliceStub);
        assertTrue(transactionList.contains(transactionWithAliceStub));
    }

    @Test
    public void setTransaction_nullTargetTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(
                null, new TransactionWithAliceStub()));
    }

    @Test
    public void setTransaction_nullEditedTransaction_throwsNullPointerException() {
        transactionList.add(transactionWithAliceStub);
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(transactionWithAliceStub, null));
    }

    @Test
    public void setTransaction_targetTransactionNotInList_throwsTransactionNotFoundException() {
        transactionList.add(transactionWithAliceStub);
        assertThrows(TransactionNotFoundException.class, () ->
                transactionList.setTransaction(transactionWithBobStub, transactionWithAliceStub));
    }

    @Test
    public void setTransaction_editedTransactionIsSameTransaction_success() {
        transactionList.add(transactionWithAliceStub);
        transactionList.setTransaction(transactionWithAliceStub, transactionWithAliceStub);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionWithAliceStub);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionIsDifferentTransaction_success() {
        transactionList.add(transactionWithAliceStub);
        transactionList.setTransaction(transactionWithAliceStub, transactionWithBobStub);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionWithBobStub);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.remove(transactionWithAliceStub));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        transactionList.add(transactionWithAliceStub);
        transactionList.remove(transactionWithAliceStub);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((UniqueTransactionList) null));
    }

    @Test
    public void setTransactions_transactionList_replacesOwnListWithProvidedTransactionList() {
        transactionList.add(transactionWithAliceStub);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionWithBobStub);
        transactionList.setTransactions(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        transactionList.add(transactionWithAliceStub);
        List<Transaction> transactionCollectionsList = Collections.singletonList(transactionWithBobStub);
        transactionList.setTransactions(transactionCollectionsList);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionWithBobStub);
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

    @Test
    void testGetBalance() {
        transactionList.add(new TransactionBuilder().withAmount("3").withPayeeName(Name.SELF.fullName)
            .withPortions(Set.of(ALICE_PORTION, BENSON_PORTION)).build());
        assertTrue(transactionList.getBalance(ALICE.getName()).equals(BigFraction.ONE));
        assertTrue(UniqueTransactionList.getBalance(BENSON.getName(),
                transactionList.asUnmodifiableObservableList()).equals(BigFraction.ONE.add(BigFraction.ONE)));
    }

    private static class TransactionWithAliceStub extends Transaction {

        private static final Amount amount = new Amount("0");
        private static final Description description = new Description("Stub");
        private static final Name payeeName = TypicalPersons.ALICE.getName();
        private static final Set<Portion> portions = new HashSet<>(Collections.singletonList(ALICE_PORTION));

        public TransactionWithAliceStub() {
            super(amount, description, payeeName, portions);
        }
    }

    private static class TransactionWithBobStub extends Transaction {

        private static final Amount amount = new Amount("0");
        private static final Description description = new Description("Stub");
        private static final Name payeeName = TypicalPersons.BOB.getName();
        private static final Set<Portion> portions = new HashSet<>(Collections.singletonList(BENSON_PORTION));

        public TransactionWithBobStub() {
            super(amount, description, payeeName, portions);
        }
    }
}
