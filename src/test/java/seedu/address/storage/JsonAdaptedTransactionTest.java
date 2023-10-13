package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTransactions.DINNER;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;

public class JsonAdaptedTransactionTest {
    private static final String INVALID_AMOUNT = "1/3";
    private static final String INVALID_DESCRIPTION = " ";

    private static final String VALID_AMOUNT = DINNER.getAmount().toString();
    private static final String VALID_DESCRIPTION = DINNER.getDescription().toString();
    private static final JsonAdaptedPerson VALID_PERSON = new JsonAdaptedPerson(DINNER.getPayee());
    private static final List<JsonAdaptedExpense> VALID_EXPENSES = DINNER.getExpenses().stream()
            .map(JsonAdaptedExpense::new)
            .collect(Collectors.toList());

    private static final String VALID_TIMESTAMP = DINNER.getTimestamp().toString();


    @Test
    public void toModelType_validTransactionDetails_returnsTransaction() throws Exception {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(DINNER);
        assertEquals(DINNER, transaction.toModelType());
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(
                INVALID_AMOUNT, VALID_DESCRIPTION, VALID_PERSON, VALID_EXPENSES, VALID_TIMESTAMP);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(null, VALID_DESCRIPTION, VALID_PERSON, VALID_EXPENSES, VALID_TIMESTAMP);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(
                        VALID_AMOUNT, INVALID_DESCRIPTION, VALID_PERSON, VALID_EXPENSES, VALID_TIMESTAMP);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_AMOUNT, null, VALID_PERSON, VALID_EXPENSES, VALID_TIMESTAMP);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullPayee_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_AMOUNT, VALID_DESCRIPTION, null, VALID_EXPENSES, VALID_TIMESTAMP);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }
}
