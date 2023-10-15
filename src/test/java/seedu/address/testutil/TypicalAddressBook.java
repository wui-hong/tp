package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

/**
 * A utility class to get a typical address book containing persons and transactions.
 */
public class TypicalAddressBook {
    /**
     * Returns an {@code AddressBook} with all the typical persons and transactions.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Transaction transaction : TypicalTransactions.getTypicalTransactions()) {
            ab.addTransaction(transaction);
        }
        return ab;
    }
}
