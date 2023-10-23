package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Timestamp;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.portion.Portion;
import seedu.address.model.transaction.portion.Weight;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Transaction[] getSampleTransactions() {
        return new Transaction[] {
            new Transaction(new Amount("60"), new Description("Group Project Lunch"), new Name("Self"), Set.of(
                new Portion(new Name("Alex Yeoh"), new Weight("2")), new Portion(new Name("Bernice Yu"),
                    new Weight("4"))), new Timestamp("2023-10-13T12:34:56.789")),
            new Transaction(new Amount("90"), new Description("Hall Dinner"), new Name("Bernice Yu"), Set.of(
                new Portion(new Name("Self"), new Weight("8")), new Portion(new Name("Bernice Yu"), new Weight("4"))),
                    new Timestamp("2023-10-13T12:34:56.790")),
            new Transaction(new Amount("600"), new Description("Shared Dorm Rent"), new Name("Bernice Yu"), Set.of(
                new Portion(new Name("Self"), new Weight("1")), new Portion(new Name("Bernice Yu"), new Weight("1"))),
                    new Timestamp("2023-10-13T12:34:56.791")),
            new Transaction(new Amount("100"), new Description("Carpool Subscription"), new Name("Self"), Set.of(
                new Portion(new Name("Self"), new Weight("1")), new Portion(new Name("Bernice Yu"), new Weight("1"))),
                    new Timestamp("2023-10-13T12:34:56.792")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
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
