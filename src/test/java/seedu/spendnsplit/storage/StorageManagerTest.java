package seedu.spendnsplit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.spendnsplit.testutil.TypicalSpendNSplitBook.getTypicalSpendNSplitBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.spendnsplit.model.SpendNSplit;
import seedu.spendnsplit.model.ReadOnlySpendNSplitBook;
import seedu.spendnsplit.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonSpendNSplitBookStorage spendNSplitBookStorage = new JsonSpendNSplitBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(spendNSplitBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void spendNSplitBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonSpendNSplitBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonSpendNSplitBookStorageTest} class.
         */
        SpendNSplit original = getTypicalSpendNSplitBook();
        storageManager.saveSpendNSplitBook(original);
        ReadOnlySpendNSplitBook retrieved = storageManager.readSpendNSplitBook().get();
        assertEquals(original, new SpendNSplit(retrieved));
    }

    @Test
    public void getSpendNSplitBookFilePath() {
        assertNotNull(storageManager.getSpendNSplitBookFilePath());
    }

}
