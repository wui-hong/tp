package seedu.spendnsplit.model;

import static seedu.spendnsplit.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    @Test
    public void setSpendNSplitBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setSpendNSplitBookFilePath(null));
    }

}
