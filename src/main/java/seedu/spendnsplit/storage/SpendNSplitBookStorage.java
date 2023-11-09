package seedu.spendnsplit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.spendnsplit.commons.exceptions.DataLoadingException;
import seedu.spendnsplit.model.ReadOnlySpendNSplitBook;

/**
 * Represents a storage for {@link seedu.spendnsplit.model.SpendNSplit}.
 */
public interface SpendNSplitBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSpendNSplitBookFilePath();

    /**
     * Returns SpendNSplitBook data as a {@link ReadOnlySpendNSplitBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlySpendNSplitBook> readSpendNSplitBook() throws DataLoadingException;

    /**
     * @see #getSpendNSplitBookFilePath()
     */
    Optional<ReadOnlySpendNSplitBook> readSpendNSplitBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlySpendNSplitBook} to the storage.
     * @param spendNSplitBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSpendNSplitBook(ReadOnlySpendNSplitBook spendNSplitBook) throws IOException;

    /**
     * @see #saveSpendNSplitBook(ReadOnlySpendNSplitBook)
     */
    void saveSpendNSplitBook(ReadOnlySpendNSplitBook spendNSplitBook, Path filePath) throws IOException;

}
