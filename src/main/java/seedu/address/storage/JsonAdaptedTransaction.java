package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.expense.Expense;


/**
 * Jackson-friendly version of {@link Transaction}.
 */
public class JsonAdaptedTransaction {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String amount;
    private final String description;
    private final JsonAdaptedPerson payee;

    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("amount") String amount,
                                  @JsonProperty("description") String description,
                                  @JsonProperty("payee") JsonAdaptedPerson payee,
                                  @JsonProperty("expenses") List<JsonAdaptedExpense> expenses) {
        this.amount = amount;
        this.description = description;
        this.payee = payee;
        if (expenses != null) {
            this.expenses.addAll(expenses);
        }
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        amount = source.getAmount().toString();
        description = source.getDescription().value;
        payee = new JsonAdaptedPerson(source.getPayee());
        expenses.addAll(source.getExpenses().stream()
                .map(JsonAdaptedExpense::new)
                .collect(Collectors.toList()));
    }


    /**
     * Converts this Jackson-friendly adapted transaction object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public Transaction toModelType() throws IllegalValueException {
        final List<Expense> transactionExpenses = new ArrayList<>();
        for (JsonAdaptedExpense expense: expenses) {
            transactionExpenses.add(expense.toModelType());
        }

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (description == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (payee == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        final Person modelPayee = payee.toModelType();

        final Set<Expense> modelExpenses = new HashSet<>(transactionExpenses);

        return new Transaction(modelAmount, modelDescription, modelPayee, modelExpenses);
    }
}
