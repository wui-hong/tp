package seedu.spendnsplit.model;

import static java.util.Objects.requireNonNull;
import static seedu.spendnsplit.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.apache.commons.numbers.fraction.BigFraction;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.spendnsplit.commons.core.LogsCenter;
import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.logic.parser.CommandAliasMap;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.transaction.Timestamp;
import seedu.spendnsplit.model.transaction.Transaction;

/**
 * Represents the in-memory model of the spendNSplit book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final SpendNSplit spendNSplit;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Transaction> filteredTransactions;

    /**
     * Initializes a ModelManager with the given spendNSplitBook and userPrefs.
     */
    public ModelManager(ReadOnlySpendNSplitBook spendNSplitBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(spendNSplitBook, userPrefs);

        logger.fine("Initializing with spendNSplit book: " + spendNSplitBook + " and user prefs " + userPrefs);

        this.spendNSplit = new SpendNSplit(spendNSplitBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.spendNSplit.getPersonList());
        filteredTransactions = new FilteredList<>(this.spendNSplit.getTransactionList());
    }

    public ModelManager() {
        this(new SpendNSplit(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public Path getSpendNSplitBookFilePath() {
        return userPrefs.getSpendNSplitBookFilePath();
    }

    @Override
    public void setSpendNSplitBookFilePath(Path spendNSplitBookFilePath) {
        requireNonNull(spendNSplitBookFilePath);
        userPrefs.setSpendNSplitBookFilePath(spendNSplitBookFilePath);
    }

    @Override
    public CommandAliasMap getCommandMap() {
        return userPrefs.getCommandMap();
    }

    @Override
    public String setCommandAlias(String command, String alias) throws CommandException {
        requireAllNonNull(command, alias);
        return userPrefs.setCommandAlias(command, alias);
    }

    //=========== SpendNSplitBook ================================================================================

    @Override
    public void setSpendNSplitBook(ReadOnlySpendNSplitBook spendNSplitBook) {
        this.spendNSplit.resetData(spendNSplitBook);
    }

    @Override
    public ReadOnlySpendNSplitBook getSpendNSplitBook() {
        return spendNSplit;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return spendNSplit.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        spendNSplit.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        spendNSplit.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public Set<Name> getAllNames() {
        return spendNSplit.getAllNames();
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        spendNSplit.setPerson(target, editedPerson);
    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);

        spendNSplit.setTransaction(target, editedTransaction);
    }

    @Override
    public void deleteTransaction(Transaction target) {
        spendNSplit.removeTransaction(target);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        spendNSplit.addTransaction(transaction);
        updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
    }

    @Override
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return spendNSplit.hasTransaction(transaction);
    }

    @Override
    public BigFraction getBalance(Name name) {
        requireNonNull(name);
        return spendNSplit.getBalance(name);
    }

    @Override
    public BigFraction getBalance(Name name, Timestamp time) {
        requireAllNonNull(name, time);
        return spendNSplit.getBalance(name, time);
    }

    public void sortPersonDescending() {
        spendNSplit.setPersonDescendingBalance();
    }

    public void sortPersonAscending() {
        spendNSplit.setPersonAscendingBalance();
    }

    //=========== Filtered Person & Transaction List Accessors ===============================================

    /**
     * Returns an unmodifiable view to be displayed of the list of {@code Person} backed by the internal list of
     * {@code versionedSpendNSplitBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Transaction} backed by the internal list of
     * {@code versionedSpendNSplitBook}
     */
    @Override
    public ObservableList<Transaction> getFullTransactionList() {
        return spendNSplit.getTransactionList();
    }

    /**
     * Returns an unmodifiable view to be displayed of the list of {@code Transaction} backed by the internal list of
     * {@code versionedSpendNSplitBook}
     */
    public ObservableList<Transaction> getFilteredTransactionList() {
        return filteredTransactions;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return spendNSplit.equals(otherModelManager.spendNSplit)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredTransactions.equals(otherModelManager.filteredTransactions);
    }

}
