package seedu.spendnsplit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.spendnsplit.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.spendnsplit.testutil.Assert.assertThrows;
import static seedu.spendnsplit.testutil.TypicalPersons.ALICE;
import static seedu.spendnsplit.testutil.TypicalPersons.BENSON;
import static seedu.spendnsplit.testutil.TypicalTransactions.DINNER;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.model.person.NameContainsKeywordsPredicate;
import seedu.spendnsplit.testutil.SpendNSplitBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new SpendNSplit(), new SpendNSplit(modelManager.getSpendNSplitBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setSpendNSplitBookFilePath(Paths.get("spendnsplit/book/file/path"));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setSpendNSplitBookFilePath(Paths.get("new/spendnsplit/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setSpendNSplitBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setSpendNSplitBookFilePath(null));
    }

    @Test
    public void setSpendNSplitBookFilePath_validPath_setsSpendNSplitBookFilePath() {
        Path path = Paths.get("spendnsplit/book/file/path");
        modelManager.setSpendNSplitBookFilePath(path);
        assertEquals(path, modelManager.getSpendNSplitBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasTransaction_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTransaction(null));
    }

    @Test
    public void hasPerson_personNotInSpendNSplitBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasTransaction_transactionNotInSpendNSplitBook_returnsFalse() {
        assertFalse(modelManager.hasTransaction(DINNER));
    }

    @Test
    public void hasPerson_personInSpendNSplitBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasTransaction_transactionInSpendNSplitBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);
        modelManager.addTransaction(DINNER);
        assertTrue(modelManager.hasTransaction(DINNER));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
            UnsupportedOperationException.class, () -> modelManager.getFilteredTransactionList().remove(0));
    }

    @Test
    public void equals() {
        SpendNSplit spendNSplit = new SpendNSplitBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        SpendNSplit differentSpendNSplit = new SpendNSplit();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(spendNSplit, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(spendNSplit, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different spendNSplitBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentSpendNSplit, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(spendNSplit, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setSpendNSplitBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(spendNSplit, differentUserPrefs)));
    }
}
