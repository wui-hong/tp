package seedu.spendnsplit.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.spendnsplit.logic.parser.exceptions.ParseException;
import seedu.spendnsplit.model.ReadOnlySpendNSplitBook;
import seedu.spendnsplit.model.SpendNSplit;
import seedu.spendnsplit.model.person.Address;
import seedu.spendnsplit.model.person.Email;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.person.Phone;
import seedu.spendnsplit.model.person.TelegramHandle;
import seedu.spendnsplit.model.tag.Tag;
import seedu.spendnsplit.model.transaction.Amount;
import seedu.spendnsplit.model.transaction.Description;
import seedu.spendnsplit.model.transaction.Timestamp;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.model.transaction.portion.Portion;
import seedu.spendnsplit.model.transaction.portion.Weight;

/**
 * Contains utility methods for populating {@code SpendNSplitBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new TelegramHandle("@alex_yeoh"),
                new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new TelegramHandle("@bernice122"),
                new Email("berniceyu@example.com"),
                null,
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), null,
                null,
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), null, null,
                null,
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new TelegramHandle("@irfan_handsome"),
                new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), null, null,
                null,
                null,
                getTagSet("colleagues"))
        };
    }

    public static Transaction[] getSampleTransactions() {
        try {
            return new Transaction[] {
                new Transaction(new Amount("60"), new Description("Group Project Lunch"), new Name("Self"), Set.of(
                    new Portion(new Name("Alex Yeoh"), new Weight("2")), new Portion(new Name("Bernice Yu"),
                        new Weight("4"))), new Timestamp("13/10/2023 12:00")),
                new Transaction(new Amount("90"), new Description("Hall Dinner"), new Name("Bernice Yu"), Set.of(
                    new Portion(new Name("Self"), new Weight("8")), new Portion(new Name("Bernice Yu"),
                        new Weight("4"))), new Timestamp("13/10/2023 16:00")),
                new Transaction(new Amount("600"), new Description("Shared Dorm Rent"), new Name("Bernice Yu"), Set.of(
                    new Portion(new Name("Self"), new Weight("1")), new Portion(new Name("Bernice Yu"),
                        new Weight("1"))), new Timestamp("13/10/2023 13:00")),
                new Transaction(new Amount("100"), new Description("Carpool Subscription"), new Name("Self"), Set.of(
                    new Portion(new Name("Self"), new Weight("1")), new Portion(new Name("Bernice Yu"),
                        new Weight("1"))), new Timestamp("13/10/2023 04:00"))
            };
        } catch (ParseException e) {
            return new Transaction[0];
        }
    }

    public static ReadOnlySpendNSplitBook getSampleSpendNSplitBook() {
        SpendNSplit sampleAb = new SpendNSplit();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Transaction sampleTransaction : getSampleTransactions()) {
            sampleAb.addTransaction(sampleTransaction);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
