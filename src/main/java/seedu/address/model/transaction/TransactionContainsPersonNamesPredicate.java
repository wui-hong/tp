package seedu.address.model.transaction;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;

/**
 * Tests that a {@code Transaction} contains any of the names of all the {@code Person}, either as a payer or a payee.
 * Name matching is exact and case-sensitive.
 */
public class TransactionContainsPersonNamesPredicate implements Predicate<Transaction> {
    private final List<Name> personNames;
    private final String partialDescription;

    /**
     * Constructs a predicate for transactions.
     */
    public TransactionContainsPersonNamesPredicate(String partialDescription, List<Name> personNames) {
        this.personNames = personNames;
        this.partialDescription = partialDescription.trim();
    }

    @Override
    public boolean test(Transaction transaction) {
        if (!(partialDescription.isEmpty()
                || StringUtil.containsWordIgnoreCase(transaction.getDescription().value, partialDescription))) {
            return false;
        }
        if (personNames.isEmpty()) {
            return true;
        }
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
        return partialDescription.equals(otherPredicate.partialDescription)
                && Set.of(personNames).equals(Set.of(otherPredicate.personNames));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("personNames", personNames)
                .add("partialDescription", partialDescription).toString();
    }
}
