package seedu.spendnsplit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.spendnsplit.commons.exceptions.DataLoadingException;
import seedu.spendnsplit.model.ReadOnlySpendNSplitBook;
import seedu.spendnsplit.model.ReadOnlyUserPrefs;
import seedu.spendnsplit.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends SpendNSplitBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getSpendNSplitBookFilePath();

    @Override
    Optional<ReadOnlySpendNSplitBook> readSpendNSplitBook() throws DataLoadingException;

    @Override
    void saveSpendNSplitBook(ReadOnlySpendNSplitBook spendNSplitBook) throws IOException;

}
