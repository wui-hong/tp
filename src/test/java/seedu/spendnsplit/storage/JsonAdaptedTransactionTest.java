package seedu.spendnsplit.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.spendnsplit.storage.JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.spendnsplit.testutil.Assert.assertThrows;
import static seedu.spendnsplit.testutil.TypicalTransactions.LUNCH;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.commons.exceptions.IllegalValueException;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.Amount;
import seedu.spendnsplit.model.transaction.Description;
import seedu.spendnsplit.model.transaction.Timestamp;

public class JsonAdaptedTransactionTest {
    private static final String INVALID_AMOUNT = "1-3";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_PAYEE_NAME = "";
    private static final String INVALID_TIMESTAMP = "";
    private static final List<JsonAdaptedPortion> INVALID_PORTIONS = List.of();

    private static final String VALID_AMOUNT = LUNCH.getAmount().toString();
    private static final String VALID_DESCRIPTION = LUNCH.getDescription().toString();
    private static final String VALID_PAYEE_NAME = LUNCH.getPayeeName().toString();
    private static final List<JsonAdaptedPortion> VALID_PORTIONS = LUNCH.getPortions().stream()
            .map(JsonAdaptedPortion::new)
            .collect(Collectors.toList());

    private static final String VALID_TIMESTAMP = LUNCH.getTimestamp().toString();


    @Test
    public void toModelType_validTransactionDetails_returnsTransaction() throws Exception {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(LUNCH);
        assertEquals(LUNCH, transaction.toModelType());
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(
                INVALID_AMOUNT, VALID_DESCRIPTION, VALID_PAYEE_NAME, VALID_PORTIONS, VALID_TIMESTAMP);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(null, VALID_DESCRIPTION, VALID_PAYEE_NAME, VALID_PORTIONS, VALID_TIMESTAMP);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(
                        VALID_AMOUNT, INVALID_DESCRIPTION, VALID_PAYEE_NAME, VALID_PORTIONS, VALID_TIMESTAMP);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_AMOUNT, null, VALID_PAYEE_NAME, VALID_PORTIONS, VALID_TIMESTAMP);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }
    @Test
    public void toModelType_invalidPayeeName_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(
                VALID_AMOUNT, VALID_DESCRIPTION, INVALID_PAYEE_NAME, VALID_PORTIONS, VALID_TIMESTAMP);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }
    @Test
    public void toModelType_nullPayeeName_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_AMOUNT, VALID_DESCRIPTION, null, VALID_PORTIONS, VALID_TIMESTAMP);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidTimestamp_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(
                VALID_AMOUNT, VALID_DESCRIPTION, VALID_PAYEE_NAME, VALID_PORTIONS, INVALID_TIMESTAMP);
        String expectedMessage = Timestamp.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }
    @Test
    public void toModelType_nullTimestamp_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(
                VALID_AMOUNT, VALID_DESCRIPTION, VALID_PAYEE_NAME, VALID_PORTIONS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidPortion_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(
                VALID_AMOUNT, VALID_DESCRIPTION, VALID_PAYEE_NAME, INVALID_PORTIONS, VALID_TIMESTAMP);
        String expectedMessage = "Collection should not be empty";
        assertThrows(IllegalArgumentException.class, expectedMessage, transaction::toModelType);
    }
    @Test
    public void toModelType_nullPortion_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(
                VALID_AMOUNT, VALID_DESCRIPTION, VALID_PAYEE_NAME, null, VALID_TIMESTAMP);
        String expectedMessage = "Collection should not be empty";
        assertThrows(IllegalArgumentException.class, expectedMessage, transaction::toModelType);
    }
}
