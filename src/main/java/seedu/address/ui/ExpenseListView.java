package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Name;

/**
 * An UI component that displays information of an {@code Expense}.
 */
public class ExpenseListView extends UiPart<Region> {

    private static final String FXML = "ExpenseListView.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Name name;

    @FXML
    private HBox expenseListView;
    @FXML
    private Label payer;
    @FXML
    private Label amount;
    @FXML
    private Label change;

    /**
     * Creates a {@code TransactionCode} with the given {@code Transaction} and index to display.
     */
    public ExpenseListView(Name name, String subtotal, String change, int displayedIndex) {
        super(FXML);
        this.name = name;
        for (Name i : Name.RESERVED_NAMES) {
            if (i.equals(name)) {
                name = i;
            }
        }
        payer.setText(name.toString());
        amount.setText(subtotal);
        this.change.setText(change);
    }

}
