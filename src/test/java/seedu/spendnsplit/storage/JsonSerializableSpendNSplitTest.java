package seedu.spendnsplit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.spendnsplit.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.commons.exceptions.IllegalValueException;
import seedu.spendnsplit.commons.util.JsonUtil;
import seedu.spendnsplit.model.SpendNSplit;
import seedu.spendnsplit.testutil.TypicalSpendNSplitBook;

public class JsonSerializableSpendNSplitTest {

    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "JsonSerializableSpendNSplitBookTest");
    private static final Path TYPICAL_SPEND_N_SPLIT_BOOK_FILE = TEST_DATA_FOLDER.resolve("typicalSpendNSplitBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonSpendNSplitBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER
            .resolve("duplicatePersonSpendNSplitBook.json");
    private static final Path INVALID_TRANSACTION_FILE = TEST_DATA_FOLDER
            .resolve("invalidTransactionSpendNSplitBook.json");
    private static final Path DUPLICATE_TRANSACTION_FILE =
            TEST_DATA_FOLDER.resolve("duplicateTransactionSpendNSplitBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableSpendNSplitBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_SPEND_N_SPLIT_BOOK_FILE,
                JsonSerializableSpendNSplitBook.class).get();
        SpendNSplit spendNSplitFromFile = dataFromFile.toModelType();
        SpendNSplit typicalSpendNSplit = TypicalSpendNSplitBook.getTypicalSpendNSplitBook();
        assertEquals(spendNSplitFromFile, typicalSpendNSplit);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableSpendNSplitBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableSpendNSplitBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableSpendNSplitBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableSpendNSplitBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableSpendNSplitBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableSpendNSplitBook dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSACTION_FILE,
                JsonSerializableSpendNSplitBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTansactions_throwsIllegalValueException() throws Exception {
        JsonSerializableSpendNSplitBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TRANSACTION_FILE,
                JsonSerializableSpendNSplitBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableSpendNSplitBook.MESSAGE_DUPLICATE_TRANSACTION,
                dataFromFile::toModelType);
    }

}
