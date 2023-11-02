package seedu.spendnsplit.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.numbers.fraction.BigFraction;

import javafx.collections.ObservableList;
import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.person.UniquePersonList;
import seedu.spendnsplit.model.transaction.Timestamp;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.model.transaction.UniqueTransactionList;

/**
 * Wraps all data at the spendNSplit-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class SpendNSplit implements ReadOnlySpendNSplitBook {

    private final UniquePersonList persons;
    private final UniqueTransactionList transactions;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        transactions = new UniqueTransactionList();
    }
    private Comparator<Person> personComparator;

    public SpendNSplit() {
        this.setPersonDescendingBalance();
    }

    /**
     * Creates an SpendNSplitBook using the Persons and Transactions in the {@code toBeCopied}
     */
    public SpendNSplit(ReadOnlySpendNSplitBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
        sortPersons();
    }

    /**
     * Replaces the contents of the transaction list with {@code transactions}.
     * {@code transactions} must not contain duplicate transactions.
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setTransactions(transactions, getAllNames());
        sortPersons();
    }


    /**
     * Resets the existing data of this {@code SpendNSplitBook} with {@code newData}.
     */
    public void resetData(ReadOnlySpendNSplitBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTransactions(newData.getTransactionList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the spendNSplit book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the spendNSplit book.
     * The person must not already exist in the spendNSplit book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        sortPersons();
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the spendNSplit book.
     * The person identity of {@code editedPerson} must not be the same as another existing
     * person in the spendNSplit book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        transactions.setPerson(target.getName(), editedPerson.getName());
        syncNames();
        sortPersons();
    }

    /**
     * Removes {@code key} from this {@code SpendNSplitBook}.
     * {@code key} must exist in the spendNSplit book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        transactions.deletePerson(key.getName(), getAllNames());
        sortPersons();
    }

    /**
     * Returns a set of all names in the spendNSplit book.
     */
    public Set<Name> getAllNames() {
        return persons.getAllNames();
    }

    //// transaction-level operations

    /**
     * Returns true if a transaction with the same identity as {@code transaction} exists in the spendNSplit book.
     */
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return transactions.contains(transaction);
    }

    /**
     * Adds a transaction to the spendNSplit book.
     * The transaction must not already exist in the spendNSplit book.
     */
    public void addTransaction(Transaction transaction) {
        requireNonNull(transaction);
        transactions.add(transaction, getAllNames());
        syncNames();
        sortPersons();
    }

    /**
     * Replaces the given transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the spendNSplit book.
     * The transaction identity of {@code editedTransaction} must not be the same as another existing transaction
     * in the spendNSplit book.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireNonNull(editedTransaction);

        transactions.setTransaction(target, editedTransaction, getAllNames());
        syncNames();
        sortPersons();
    }

    /**
     * Removes {@code key} from this {@code SpendNSplitBook}.
     * {@code key} must exist in the spendNSplit book.
     */
    public void removeTransaction(Transaction key) {
        transactions.remove(key);
        sortPersons();
    }

    /**
     * Sets sort person to descending.
     */
    public void setPersonDescendingBalance() {
        personComparator = (p1, p2) -> {
            int balCompare = -transactions.getBalance(p1.getName()).compareTo(transactions.getBalance(p2.getName()));
            if (balCompare == 0) {
                return p1.getName().compareTo(p2.getName());
            }
            return balCompare;
        };
        sortPersons();
    }

    /**
     * Sets sort person to ascending.
     */
    public void setPersonAscendingBalance() {
        personComparator = (p1, p2) -> {
            int balCompare = transactions.getBalance(p1.getName()).compareTo(transactions.getBalance(p2.getName()));
            if (balCompare == 0) {
                return p1.getName().compareTo(p2.getName());
            }
            return balCompare;
        };
        sortPersons();
    }

    private void sortPersons() {
        persons.sort(personComparator);
    }

    private void syncNames() {
        Set<Name> names = getAllNames();
        names.addAll(Name.RESERVED_NAMES);
        this.transactions.syncNames(names);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("transactions", transactions)
                .toString();
    }

    /**
     * Returns the total balance of all transaction that the person has to pay the user.
     *
     * @param name the name of the person
     */
    public BigFraction getBalance(Name name) {
        return transactions.getBalance(name);
    }

    /**
     * Returns the total balance of all transaction before a stated time that the person has to pay the user.
     *
     * @param name the name of the person
     * @param time the time before which transactions are accounted for
     */
    public BigFraction getBalance(Name name, Timestamp time) {
        return transactions.getBalance(name, time);
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactions.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SpendNSplit)) {
            return false;
        }

        SpendNSplit otherSpendNSplit = (SpendNSplit) other;
        return persons.equals(otherSpendNSplit.persons) && transactions.equals(otherSpendNSplit.transactions);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
