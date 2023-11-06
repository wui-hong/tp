package seedu.spendnsplit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.spendnsplit.testutil.Assert.assertThrows;
import static seedu.spendnsplit.testutil.TypicalPersons.BOB;
import static seedu.spendnsplit.testutil.TypicalPortions.ALICE_PORTION;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.commons.exceptions.IllegalValueException;
import seedu.spendnsplit.commons.util.FractionUtil;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.portion.Weight;

public class JsonAdaptedPortionTest {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Portion's %s field is missing!";

    private static final String INVALID_NAME = "";
    private static final String INVALID_WEIGHT = "-1/2/3";
    private static final String VALID_NAME = BOB.getName().toString();

    private static final String VALID_WEIGHT = "0.5";

    @Test
    public void toModelType_validPortionDetails_returnsPortion() throws Exception {
        JsonAdaptedPortion portion = new JsonAdaptedPortion(ALICE_PORTION);
        assertEquals(ALICE_PORTION, portion.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPortion portion = new JsonAdaptedPortion(INVALID_NAME, VALID_WEIGHT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, portion::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPortion portion = new JsonAdaptedPortion(null, VALID_WEIGHT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, portion::toModelType);
    }

    @Test
    public void toModelType_invalidWeight_throwsIllegalValueException() {
        JsonAdaptedPortion portion = new JsonAdaptedPortion(VALID_NAME, INVALID_WEIGHT);
        String expectedMessage = Weight.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, portion::toModelType);
    }

    @Test
    public void toModelType_zeroDivWeight_throwsIllegalValueException() {
        JsonAdaptedPortion portion = new JsonAdaptedPortion(VALID_NAME, "1/0");
        String expectedMessage = FractionUtil.ZERO_DIVISION;
        assertThrows(IllegalValueException.class, expectedMessage, portion::toModelType);
    }

    @Test
    public void toModelType_nullWeight_throwsIllegalValueException() {
        JsonAdaptedPortion portion = new JsonAdaptedPortion(VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Weight.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, portion::toModelType);
    }

}
