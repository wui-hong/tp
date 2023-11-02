package seedu.spendnsplit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.spendnsplit.testutil.Assert.assertThrows;
import static seedu.spendnsplit.testutil.TypicalPersons.ALICE;
import static seedu.spendnsplit.testutil.TypicalPersons.HOON;
import static seedu.spendnsplit.testutil.TypicalPersons.IDA;
import static seedu.spendnsplit.testutil.TypicalTransactions.GROCERIES;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.spendnsplit.commons.exceptions.DataLoadingException;
import seedu.spendnsplit.model.SpendNSplit;

public class JsonSpendNSplitBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSpendNSplitBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readSpendNSplitBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readSpendNSplitBook(null));
    }

    private java.util.Optional<ReadOnlySpendNSplitBook> readSpendNSplitBook(String filePath) throws Exception {
        return new JsonSpendNSplitBookStorage(Paths.get(filePath)).readSpendNSplitBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readSpendNSplitBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readSpendNSplitBook("notJsonFormatSpendNSplitBook.json"));
    }

    @Test
    public void readSpendNSplitBook_invalidPersonSpendNSplitBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readSpendNSplitBook("invalidPersonSpendNSplitBook.json"));
    }

    @Test
    public void readSpendNSplitBook_invalidAndValidPersonSpendNSplitBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readSpendNSplitBook("invalidAndValidPersonSpendNSplitBook.json"));
    }

    @Test
    public void readAndSaveSpendNSplitBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempSpendNSplitBook.json");
        SpendNSplit original = getTypicalSpendNSplitBook();
        JsonSpendNSplitBookStorage jsonSpendNSplitBookStorage = new JsonSpendNSplitBookStorage(filePath);

        // Save in new file and read back
        jsonSpendNSplitBookStorage.saveSpendNSplitBook(original, filePath);
        ReadOnlySpendNSplitBook readBack = jsonSpendNSplitBookStorage.readSpendNSplitBook(filePath).get();
        assertEquals(original, new SpendNSplit(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        original.addTransaction(GROCERIES);
        jsonSpendNSplitBookStorage.saveSpendNSplitBook(original, filePath);
        readBack = jsonSpendNSplitBookStorage.readSpendNSplitBook(filePath).get();
        assertEquals(original, new SpendNSplit(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonSpendNSplitBookStorage.saveSpendNSplitBook(original); // file path not specified
        readBack = jsonSpendNSplitBookStorage.readSpendNSplitBook().get(); // file path not specified
        assertEquals(original, new SpendNSplit(readBack));

    }

    @Test
    public void saveSpendNSplitBook_nullSpendNSplitBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSpendNSplitBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code spendNSplitBook} at the specified {@code filePath}.
     */
    private void saveSpendNSplitBook(ReadOnlySpendNSplitBook spendNSplitBook, String filePath) {
        try {
            new JsonSpendNSplitBookStorage(Paths.get(filePath))
                    .saveSpendNSplitBook(spendNSplitBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveSpendNSplitBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSpendNSplitBook(new SpendNSplit(), null));
    }
}
