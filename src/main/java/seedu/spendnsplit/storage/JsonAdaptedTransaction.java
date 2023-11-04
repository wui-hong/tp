package seedu.spendnsplit.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.spendnsplit.commons.exceptions.IllegalValueException;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.Amount;
import seedu.spendnsplit.model.transaction.Description;
import seedu.spendnsplit.model.transaction.Timestamp;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.model.transaction.portion.Portion;


/**
 * Jackson-friendly version of {@link Transaction}.
 */
public class JsonAdaptedTransaction {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String amount;
    private final String description;

    private final String payeeName;

    private final List<JsonAdaptedPortion> portions = new ArrayList<>();
    private final String timestamp;

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("amount") String amount,
                                  @JsonProperty("description") String description,
                                  @JsonProperty("payeeName") String payeeName,
                                  @JsonProperty("portions") List<JsonAdaptedPortion> portions,
                                  @JsonProperty("timestamp") String timestamp) {
        this.amount = amount;
        this.description = description;
        this.payeeName = payeeName;
        if (portions != null) {
            this.portions.addAll(portions);
        }
        this.timestamp = timestamp;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        amount = source.getAmount().amount.toString();
        description = source.getDescription().value;
        payeeName = source.getPayeeName().fullName;
        portions.addAll(source.getPortions().stream()
                .map(JsonAdaptedPortion::new)
                .collect(Collectors.toList()));
        timestamp = source.getTimestamp().toString();
    }


    /**
     * Converts this Jackson-friendly adapted transaction object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public Transaction toModelType() throws IllegalValueException {
        final List<Portion> transactionPortions = new ArrayList<>();
        for (JsonAdaptedPortion portion: portions) {
            transactionPortions.add(portion.toModelType());
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

        if (payeeName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Name.isValidName(payeeName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelPayeeName = new Name(payeeName);

        if (timestamp == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName()));
        }
        if (!Timestamp.isValidTimestamp(timestamp)) {
            throw new IllegalValueException(Timestamp.MESSAGE_CONSTRAINTS);
        }
        final Timestamp modelTimestamp = new Timestamp(timestamp);


        final Set<Portion> modelPortions = new HashSet<>(transactionPortions);

        return new Transaction(modelAmount, modelDescription, modelPayeeName, modelPortions, modelTimestamp);
    }
}
