package seedu.address.model.transaction;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;

/**
 * Tests that a {@code Transaction} contains any of the keywords and any of the names of all the {@code Person},
 * either as a payer or a payee. Name matching is exact and case-insensitive.
 */
public class TransactionContainsKeywordsAndPersonNamesPredicate implements Predicate<Transaction> {
    private final List<Name> personNames;
    private final List<String> keywords;

    /**
     * Constructs a predicate for transactions.
     */
    public TransactionContainsKeywordsAndPersonNamesPredicate(List<String> keywords, List<Name> personNames) {
        this.personNames = personNames;
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        if (!(keywords.isEmpty() || keywords.stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(transaction.getDescription().value, keyword)))) {
            return false;
        }
        if (!(personNames.isEmpty() || personNames.stream().anyMatch(transaction::isPersonInvolved))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionContainsKeywordsAndPersonNamesPredicate)) {
            return false;
        }

        TransactionContainsKeywordsAndPersonNamesPredicate otherPredicate =
            (TransactionContainsKeywordsAndPersonNamesPredicate) other;
        return keywords.stream().collect(Collectors.toSet())
                .equals(otherPredicate.keywords.stream().collect(Collectors.toSet()))
                && personNames.stream().collect(Collectors.toSet())
                .equals(otherPredicate.personNames.stream().collect(Collectors.toSet()));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords)
                .add("personNames", personNames).toString();
    }
}
