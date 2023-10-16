package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenses.ALICE_EXPENSE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.expense.Weight;

public class JsonAdaptedExpenseTest {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expense's %s field is missing!";

    private static final String INVALID_NAME = "";
    private static final String INVALID_WEIGHT = "-1/3";

    private static final String VALID_NAME = BOB.getName().toString();

    private static final String VALID_WEIGHT = "0.5";

    @Test
    public void toModelType_validExpenseDetails_returnsExpense() throws Exception {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(ALICE_EXPENSE);
        assertEquals(ALICE_EXPENSE, expense.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(INVALID_NAME, VALID_WEIGHT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(null, VALID_WEIGHT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidWeight_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(VALID_NAME, INVALID_WEIGHT);
        String expectedMessage = Weight.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }


    @Test
    public void toModelType_nullWeight_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Weight.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

}
