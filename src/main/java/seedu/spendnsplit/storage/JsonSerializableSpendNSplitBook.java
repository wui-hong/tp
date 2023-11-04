package seedu.spendnsplit.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.spendnsplit.commons.exceptions.IllegalValueException;
import seedu.spendnsplit.model.ReadOnlySpendNSplitBook;
import seedu.spendnsplit.model.SpendNSplit;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.transaction.Transaction;

/**
 * An Immutable SpendNSplitBook that is serializable to JSON format.
 */
@JsonRootName(value = "spendnsplitbook")
class JsonSerializableSpendNSplitBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_TRANSACTION = "Persons list contains duplicate transaction(s).";
    public static final String MESSAGE_INVALID_TRANSACTION = "Invalid transaction!";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableSpendNSplitBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableSpendNSplitBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                           @JsonProperty("transactions") List<JsonAdaptedTransaction> transactions) {
        this.persons.addAll(persons);
        this.transactions.addAll(transactions);
    }

    /**
     * Converts a given {@code ReadOnlySpendNSplitBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSpendNSplitBook}.
     */
    public JsonSerializableSpendNSplitBook(ReadOnlySpendNSplitBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        transactions.addAll(source.getTransactionList().stream()
                .map(JsonAdaptedTransaction::new).collect(Collectors.toList()));
    }

    /**
     * Converts this spendNSplit book into the model's {@code SpendNSplitBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SpendNSplit toModelType() throws IllegalValueException {
        SpendNSplit spendNSplit = new SpendNSplit();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (spendNSplit.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            spendNSplit.addPerson(person);
        }
        for (JsonAdaptedTransaction jsonAdaptedTransaction : transactions) {
            Transaction transaction = jsonAdaptedTransaction.toModelType();
            if (!transaction.isValid(spendNSplit.getAllNames())) {
                throw new IllegalValueException(MESSAGE_INVALID_TRANSACTION);
            }
            if (spendNSplit.hasTransaction(transaction)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TRANSACTION);
            }
            spendNSplit.addTransaction(transaction);
        }
        return spendNSplit;
    }

}
