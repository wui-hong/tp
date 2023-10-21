package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.numbers.fraction.BigFraction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.exceptions.DuplicateTransactionException;
import seedu.address.model.transaction.exceptions.TransactionNotFoundException;

/**
 * A list of transactions.
 */
public class UniqueTransactionList implements Iterable<Transaction> {

    private final ObservableList<Transaction> internalList = FXCollections.observableArrayList();
    private final ObservableList<Transaction> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Get balance for a person with a given name, within this list.
     */
    public BigFraction getBalance(Name name) {
        return UniqueTransactionList.getBalance(name, internalList);
    }

    /**
     * Get balance for a person with a given name, within a given list.
     */
    public static BigFraction getBalance(Name name, ObservableList<Transaction> transactionList) {
        return transactionList.stream().map(transaction -> transaction.getPortionOwed(name))
                .reduce(BigFraction.ZERO, BigFraction::add);
    }

    /**
     * Returns true if the list contains an equivalent transaction as the given argument.
     */
    public boolean contains(Transaction toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTransaction);
    }

    /**
     * Adds a transaction to the list.
     */
    public void add(Transaction toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        sort();
    }

    /**
     * Replaces the transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the list.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TransactionNotFoundException();
        }
        internalList.set(index, editedTransaction);
        sort();
    }

    /**
     * Removes the equivalent transaction from the list.
     * The transaction must exist in the list.
     */
    public void remove(Transaction toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TransactionNotFoundException();
        }
        sort();
    }

    /**
     * Replaces all names with names from the set.
     */
    public void syncNames(Set<Name> validNames) {
        internalList.setAll(internalList.stream()
                .map(transaction -> transaction.syncNames(validNames)).collect(Collectors.toList()));
    }

    /**
     * Removes person p from all {@code transactions} in the list.
     */
    public void deletePerson(Name p, Set<Name> validNames) {
        List<Transaction> validTransactions = new ArrayList<>();
        for (Transaction transaction : internalList) {
            Transaction updatedTransaction = transaction.removePerson(p);
            if (updatedTransaction.isValid(validNames)) {
                validTransactions.add(updatedTransaction);
            }
        }
        internalList.setAll(validTransactions);
    }

    /**
     * Replaces all instances of the target name to the edited name in transactions.
     */
    public void setPerson(Name target, Name edited) {
        internalList.setAll(internalList.stream()
                .map(transaction -> transaction.setPerson(target, edited)).collect(Collectors.toList()));
    }

    public void setTransactions(UniqueTransactionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        sort();
    }

    /**
     * Replaces the contents of this list with {@code transactions}.
     * {@code transactions} must not contain duplicate transactions.
     */
    public void setTransactions(List<Transaction> transactions) {
        requireAllNonNull(transactions);
        if (!transactionsAreUnique(transactions)) {
            throw new DuplicateTransactionException();
        }

        internalList.setAll(transactions);
        sort();
    }

    /**
     * Sorts transactions in the list by their comparator.
     */
    public void sort() {
        internalList.sort((t1, t2) -> t1.compareTo(t2));
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Transaction> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Transaction> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueTransactionList)) {
            return false;
        }

        UniqueTransactionList otherTransactionList = (UniqueTransactionList) other;
        return internalList.equals(otherTransactionList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code transactions} contains only unique transactions.
     */
    private boolean transactionsAreUnique(List<Transaction> transactions) {
        for (int i = 0; i < transactions.size() - 1; i++) {
            for (int j = i + 1; j < transactions.size(); j++) {
                if (transactions.get(i).isSameTransaction(transactions.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
