package seedu.spendnsplit.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.spendnsplit.commons.core.LogsCenter;
import seedu.spendnsplit.commons.exceptions.DataLoadingException;
import seedu.spendnsplit.commons.exceptions.IllegalValueException;
import seedu.spendnsplit.commons.util.FileUtil;
import seedu.spendnsplit.commons.util.JsonUtil;
import seedu.spendnsplit.model.ReadOnlySpendNSplitBook;

/**
 * A class to access SpendNSplitBook data stored as a json file on the hard disk.
 */
public class JsonSpendNSplitBookStorage implements SpendNSplitBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonSpendNSplitBookStorage.class);

    private Path filePath;

    public JsonSpendNSplitBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSpendNSplitBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySpendNSplitBook> readSpendNSplitBook() throws DataLoadingException {
        return readSpendNSplitBook(filePath);
    }

    /**
     * Similar to {@link #readSpendNSplitBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlySpendNSplitBook> readSpendNSplitBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableSpendNSplitBook> jsonSpendNSplitBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableSpendNSplitBook.class);
        if (!jsonSpendNSplitBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSpendNSplitBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    public void saveSpendNSplitBook(ReadOnlySpendNSplitBook spendNSplitBook) throws IOException {
        saveSpendNSplitBook(spendNSplitBook, filePath);
    }

    /**
     * Similar to {@link #saveSpendNSplitBook(ReadOnlySpendNSplitBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveSpendNSplitBook(ReadOnlySpendNSplitBook spendNSplitBook, Path filePath) throws IOException {
        requireNonNull(spendNSplitBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSpendNSplitBook(spendNSplitBook), filePath);
    }

}
