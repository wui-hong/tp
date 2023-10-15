package seedu.address.model.transaction;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;

/**
 * Tests that a {@code Transaction} contains any of the names of all the {@code Person}, either as a payer or a payee.
 * Name matching is exact and case-sensitive.
 */
public class TransactionContainsPersonNamesPredicate implements Predicate<Transaction> {
    private final List<Name> personNames;

    public TransactionContainsPersonNamesPredicate(List<Name> personNames) {
        this.personNames = personNames;
    }

    public boolean isEmpty() {
        return personNames.isEmpty();
    }

    @Override
    public boolean test(Transaction transaction) {
        return personNames.stream().anyMatch(transaction::isPersonInvolved);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionContainsPersonNamesPredicate)) {
            return false;
        }

        TransactionContainsPersonNamesPredicate otherPredicate =
            (TransactionContainsPersonNamesPredicate) other;
        return personNames.equals(otherPredicate.personNames);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("personNames", personNames).toString();
    }
}
