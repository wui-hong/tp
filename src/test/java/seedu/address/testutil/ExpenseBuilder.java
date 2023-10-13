package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.transaction.expense.Expense;
import seedu.address.model.transaction.expense.Weight;



/**
 * A utility class to help with building Expense objects.
 */
public class ExpenseBuilder {
    public static final String DEFAULT_NAME = TypicalPersons.ALICE.getName().toString();
    public static final String DEFAULT_WEIGHT = "0.5";

    private Name name;
    private Weight weight;

    /**
     * Creates an {@code ExpenseBuilder} with the default details.
     */
    public ExpenseBuilder() {
        name = new Name(DEFAULT_NAME);
        weight = new Weight(DEFAULT_WEIGHT);
    }

    /**
     * Initializes the ExpenseBuilder with the data of {@code expenseToCopy}.
     */
    public ExpenseBuilder(Expense expenseToCopy) {
        name = expenseToCopy.getName();
        weight = expenseToCopy.getWeight();
    }

    /**
     * Sets the {@code Name} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withWeight(String weight) {
        this.weight = new Weight(weight);
        return this;
    }


    public Expense build() {
        return new Expense(name, weight);
    }
}
