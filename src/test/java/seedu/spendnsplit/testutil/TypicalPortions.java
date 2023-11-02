package seedu.spendnsplit.testutil;

import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.portion.Portion;

/**
 * A utility class containing a list of {@code Portion} objects to be used in tests.
 */
public class TypicalPortions {
    public static final Portion ALICE_PORTION = new PortionBuilder().withName(
            TypicalPersons.ALICE.getName().fullName).withWeight("2").build();

    public static final Portion BENSON_PORTION = new PortionBuilder().withName(
            TypicalPersons.BENSON.getName().fullName).withWeight("4").build();

    public static final Portion CARL_PORTION = new PortionBuilder().withName(
            TypicalPersons.CARL.getName().fullName).withWeight("6").build();

    public static final Portion SELF_PORTION = new PortionBuilder().withName(
            Name.SELF.fullName).withWeight("8").build();
}
