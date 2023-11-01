package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPortions.ALICE_PORTION;
import static seedu.address.testutil.TypicalPortions.BENSON_PORTION;
import static seedu.address.testutil.TypicalPortions.CARL_PORTION;

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
            new TransactionContainsPersonNamesPredicate(List.of(), firstPredicateNameList);
        TransactionContainsPersonNamesPredicate secondPredicate =
            new TransactionContainsPersonNamesPredicate(List.of(), secondPredicateNameList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionContainsPersonNamesPredicate firstPredicateCopy =
            new TransactionContainsPersonNamesPredicate(List.of(), firstPredicateNameList);
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
            new TransactionContainsPersonNamesPredicate(List.of(), List.of(CARL.getName()));
        assertTrue(predicate.test(new TransactionBuilder().withPayeeName(CARL.getName().fullName).build()));
        assertTrue(predicate.test(new TransactionBuilder().withPortions(Set.of(CARL_PORTION)).build()));

        // Multiple names
        predicate = new TransactionContainsPersonNamesPredicate(List.of(), List.of(BENSON.getName(), CARL.getName()));
        assertTrue(predicate.test(new TransactionBuilder().withPayeeName(CARL.getName().fullName).build()));
        assertTrue(predicate.test(new TransactionBuilder()
            .withPayeeName(ALICE.getName().fullName).withPortions(Set.of(BENSON_PORTION, CARL_PORTION)).build()));
        assertTrue(predicate.test(new TransactionBuilder().withPortions(Set.of(BENSON_PORTION)).build()));
        assertTrue(predicate.test(new TransactionBuilder().withPortions(Set.of(ALICE_PORTION, CARL_PORTION)).build()));
    }

    @Test
    public void test_transactionDoesNotContainNames_returnsTrue() {
        TransactionContainsPersonNamesPredicate predicate =
            new TransactionContainsPersonNamesPredicate(List.of(), List.of());
        assertTrue(predicate.test(new TransactionBuilder().withPayeeName(CARL.getName().fullName).build()));
        assertTrue(predicate.test(new TransactionBuilder().withPortions(Set.of(CARL_PORTION)).build()));

    }

    @Test
    public void test_transactionDoesNotContainPersonNames_returnsFalse() {
        TransactionContainsPersonNamesPredicate predicate =
                new TransactionContainsPersonNamesPredicate(List.of(), List.of(new Name("Carol")));
        assertFalse(predicate.test(new TransactionBuilder().withPayeeName(CARL.getName().fullName).build()));
        assertFalse(predicate.test(new TransactionBuilder().withPortions(Set.of(CARL_PORTION)).build()));
    }

    @Test
    public void toStringMethod() {
        List<Name> names = List.of(new Name("name1"), new Name("name2"));
        TransactionContainsPersonNamesPredicate predicate =
            new TransactionContainsPersonNamesPredicate(List.of(), names);

        String expected = TransactionContainsPersonNamesPredicate.class.getCanonicalName()
            + "{keywords=" + List.of() + ", personNames=" + names + "}";
        assertEquals(expected, predicate.toString());
    }
}
