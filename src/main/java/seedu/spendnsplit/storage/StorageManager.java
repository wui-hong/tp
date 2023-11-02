package seedu.spendnsplit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.spendnsplit.commons.core.LogsCenter;
import seedu.spendnsplit.commons.exceptions.DataLoadingException;
import seedu.spendnsplit.model.ReadOnlyUserPrefs;
import seedu.spendnsplit.model.UserPrefs;

/**
 * Manages storage of SpendNSplitBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SpendNSplitBookStorage spendNSplitBookStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code SpendNSplitBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(SpendNSplitBookStorage spendNSplitBookStorage, UserPrefsStorage userPrefsStorage) {
        this.spendNSplitBookStorage = spendNSplitBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ SpendNSplitBook methods ==============================

    @Override
    public Path getSpendNSplitBookFilePath() {
        return spendNSplitBookStorage.getSpendNSplitBookFilePath();
    }

    @Override
    public Optional<ReadOnlySpendNSplitBook> readSpendNSplitBook() throws DataLoadingException {
        return readSpendNSplitBook(spendNSplitBookStorage.getSpendNSplitBookFilePath());
    }

    @Override
    public Optional<ReadOnlySpendNSplitBook> readSpendNSplitBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return spendNSplitBookStorage.readSpendNSplitBook(filePath);
    }

    @Override
    public void saveSpendNSplitBook(ReadOnlySpendNSplitBook spendNSplitBook) throws IOException {
        saveSpendNSplitBook(spendNSplitBook, spendNSplitBookStorage.getSpendNSplitBookFilePath());
    }

    @Override
    public void saveSpendNSplitBook(ReadOnlySpendNSplitBook spendNSplitBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        spendNSplitBookStorage.saveSpendNSplitBook(spendNSplitBook, filePath);
    }

}
