package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalExpenses.ALICE_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.BOB_EXPENSE;
import static seedu.address.testutil.TypicalExpenses.CARL_EXPENSE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.testutil.TransactionBuilder;

public class TransactionContainsPersonNamesPredicateTest {
    @Test
    public void equals() {
        List<Name> firstPredicateNameList = List.of(new Name("first"));
        List<Name> secondPredicateNameList = List.of(new Name("first"), new Name("second"));

        TransactionContainsPersonNamesPredicate firstPredicate =
            new TransactionContainsPersonNamesPredicate(firstPredicateNameList);
        TransactionContainsPersonNamesPredicate secondPredicate =
            new TransactionContainsPersonNamesPredicate(secondPredicateNameList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionContainsPersonNamesPredicate firstPredicateCopy =
            new TransactionContainsPersonNamesPredicate(firstPredicateNameList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different name list -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_transactionContainsPersonNames_returnsTrue() {
        // One name
        TransactionContainsPersonNamesPredicate predicate =
            new TransactionContainsPersonNamesPredicate(List.of(CARL.getName()));
        assertTrue(predicate.test(new TransactionBuilder().withPayeeName(CARL.getName().fullName).build()));
        assertTrue(predicate.test(new TransactionBuilder().withExpenses(Set.of(CARL_EXPENSE)).build()));

        // Multiple names
        predicate = new TransactionContainsPersonNamesPredicate(List.of(BOB.getName(), CARL.getName()));
        assertTrue(predicate.test(new TransactionBuilder().withPayeeName(CARL.getName().fullName).build()));
        assertTrue(predicate.test(new TransactionBuilder()
            .withPayeeName(ALICE.getName().fullName).withExpenses(Set.of(BOB_EXPENSE, CARL_EXPENSE)).build()));
        assertTrue(predicate.test(new TransactionBuilder().withExpenses(Set.of(BOB_EXPENSE)).build()));
        assertTrue(predicate.test(new TransactionBuilder().withExpenses(Set.of(ALICE_EXPENSE, CARL_EXPENSE)).build()));
    }

    @Test
    public void test_transactionDoesNotContainPersonNames_returnsFalse() {
        // Zero names
        TransactionContainsPersonNamesPredicate predicate =
            new TransactionContainsPersonNamesPredicate(List.of());
        assertFalse(predicate.test(new TransactionBuilder().withPayeeName(CARL.getName().fullName).build()));
        assertFalse(predicate.test(new TransactionBuilder().withExpenses(Set.of(CARL_EXPENSE)).build()));

        // Non-matching name
        predicate = new TransactionContainsPersonNamesPredicate(List.of(new Name("Carol")));
        assertFalse(predicate.test(new TransactionBuilder().withPayeeName(CARL.getName().fullName).build()));
        assertFalse(predicate.test(new TransactionBuilder().withExpenses(Set.of(CARL_EXPENSE)).build()));
    }

    @Test
    public void toStringMethod() {
        List<Name> names = List.of(new Name("name1"), new Name("name2"));
        TransactionContainsPersonNamesPredicate predicate =
            new TransactionContainsPersonNamesPredicate(names);

        String expected = TransactionContainsPersonNamesPredicate.class.getCanonicalName()
            + "{personNames=" + names + "}";
        assertEquals(expected, predicate.toString());
    }
}
