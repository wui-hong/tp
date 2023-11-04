package seedu.spendnsplit.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.spendnsplit.logic.commands.CommandResult;
import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.logic.parser.exceptions.ParseException;
import seedu.spendnsplit.model.ReadOnlySpendNSplitBook;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.transaction.Transaction;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the SpendNSplitBook.
     *
     * @see seedu.spendnsplit.model.Model#getSpendNSplitBook()
     */
    ReadOnlySpendNSplitBook getSpendNSplitBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the full list of transactions */
    ObservableList<Transaction> getFullTransactionList();

    /** Returns an unmodifiable view of the filtered list of transactions */
    ObservableList<Transaction> getFilteredTransactionList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getSpendNSplitBookFilePath();
}
