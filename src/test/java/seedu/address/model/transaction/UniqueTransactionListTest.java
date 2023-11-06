package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPortions.ALICE_PORTION;
import static seedu.address.testutil.TypicalPortions.BENSON_PORTION;
import static seedu.address.testutil.TypicalPortions.SELF_PORTION;

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
    private static TransactionWithAliceStub transactionWithAliceStub;

    static {
        try {
            transactionWithAliceStub = new TransactionWithAliceStub();
        } catch (Exception e) {
            //
        }
    }

    private static TransactionWithBobStub transactionWithBobStub;

    static {
        try {
            transactionWithBobStub = new TransactionWithBobStub();
        } catch (Exception e) {
            //
        }
    }

    private final UniqueTransactionList transactionList = new UniqueTransactionList();

    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null, Set.of()));
    }

    @Test
    public void add_validTransaction_success() {
        transactionList.add(transactionWithAliceStub, Set.of(ALICE.getName()));
        assertTrue(transactionList.contains(transactionWithAliceStub));
    }

    @Test
    public void setTransaction_nullTargetTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(
                null, new TransactionWithAliceStub(), Set.of(ALICE.getName())));
    }

    @Test
    public void setTransaction_nullEditedTransaction_throwsNullPointerException() {
        transactionList.add(transactionWithAliceStub, Set.of(ALICE.getName()));
        assertThrows(NullPointerException.class, () ->
                transactionList.setTransaction(transactionWithAliceStub, null, Set.of(ALICE.getName())));
    }

    @Test
    public void setTransaction_targetTransactionNotInList_throwsTransactionNotFoundException() {
        transactionList.add(transactionWithAliceStub, Set.of(ALICE.getName()));
        assertThrows(TransactionNotFoundException.class, () ->
                transactionList.setTransaction(transactionWithBobStub, transactionWithAliceStub,
                Set.of(ALICE.getName(), BOB.getName(), BENSON.getName())));
    }

    @Test
    public void setTransaction_editedTransactionIsSameTransaction_success() {
        transactionList.add(transactionWithAliceStub, Set.of(ALICE.getName()));
        transactionList.setTransaction(transactionWithAliceStub, transactionWithAliceStub,
                Set.of(ALICE.getName(), BOB.getName(), BENSON.getName()));
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionWithAliceStub, Set.of(ALICE.getName()));
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionIsDifferentTransaction_success() {
        transactionList.add(transactionWithAliceStub, Set.of(ALICE.getName()));
        transactionList.setTransaction(transactionWithAliceStub, transactionWithBobStub,
                Set.of(ALICE.getName(), BOB.getName(), BENSON.getName()));
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionWithBobStub, Set.of(BOB.getName(), BENSON.getName()));
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
        transactionList.add(transactionWithAliceStub, Set.of(ALICE.getName()));
        transactionList.remove(transactionWithAliceStub);
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                transactionList.setTransactions((UniqueTransactionList) null, Set.of()));
    }

    @Test
    public void setTransactions_transactionList_replacesOwnListWithProvidedTransactionList() {
        transactionList.add(transactionWithAliceStub, Set.of(ALICE.getName()));
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionWithBobStub, Set.of(BOB.getName(), BENSON.getName()));
        transactionList.setTransactions(expectedTransactionList,
                Set.of(ALICE.getName(), BOB.getName(), BENSON.getName()));
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_list_replacesOwnListWithProvidedList() {
        transactionList.add(transactionWithAliceStub, Set.of(ALICE.getName()));
        List<Transaction> transactionCollectionsList = Collections.singletonList(transactionWithBobStub);
        transactionList.setTransactions(transactionCollectionsList,
                Set.of(ALICE.getName(), BOB.getName(), BENSON.getName()));
        UniqueTransactionList expectedTransactionList = new UniqueTransactionList();
        expectedTransactionList.add(transactionWithBobStub, Set.of(BOB.getName(), BENSON.getName()));
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
            .withPortions(Set.of(ALICE_PORTION, BENSON_PORTION)).build(), Set.of(ALICE.getName(), BENSON.getName()));
        assertTrue(transactionList.getBalance(ALICE.getName()).equals(BigFraction.ONE));
        assertTrue(UniqueTransactionList.getBalance(BENSON.getName(),
                transactionList.asUnmodifiableObservableList()).equals(BigFraction.ONE.add(BigFraction.ONE)));
    }

    private static class TransactionWithAliceStub extends Transaction {

        private static final Description description = new Description("Stub");
        private static final Name payeeName = Name.SELF;
        private static final Set<Portion> portions = new HashSet<>(Collections.singletonList(ALICE_PORTION));

        public TransactionWithAliceStub() throws Exception {
            super(new Amount("1"), description, payeeName, portions);
        }
    }

    private static class TransactionWithBobStub extends Transaction {

        private static final Description description = new Description("Stub");
        private static final Name payeeName = TypicalPersons.BOB.getName();
        private static final Set<Portion> portions = Set.of(SELF_PORTION, BENSON_PORTION);

        public TransactionWithBobStub() throws Exception {
            super(new Amount("1"), description, payeeName, portions);
        }
    }
}
