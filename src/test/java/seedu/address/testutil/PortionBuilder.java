package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.transaction.portion.Portion;
import seedu.address.model.transaction.portion.Weight;



/**
 * A utility class to help with building Portion objects.
 */
public class PortionBuilder {
    public static final String DEFAULT_NAME = TypicalPersons.ALICE.getName().toString();
    public static final String DEFAULT_WEIGHT = "0.5";

    private Name name;
    private Weight weight;

    /**
     * Creates a {@code PortionBuilder} with the default details.
     */
    public PortionBuilder() {
        name = new Name(DEFAULT_NAME);
        weight = new Weight(DEFAULT_WEIGHT);
    }

    /**
     * Initializes the PortionBuilder with the data of {@code portionToCopy}.
     */
    public PortionBuilder(Portion portionToCopy) {
        name = portionToCopy.getPersonName();
        weight = portionToCopy.getWeight();
    }

    /**
     * Sets the {@code Name} of the {@code Portion} that we are building.
     */
    public PortionBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code Portion} that we are building.
     */
    public PortionBuilder withWeight(String weight) {
        this.weight = new Weight(weight);
        return this;
    }


    public Portion build() {
        return new Portion(name, weight);
    }
}
