package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.FractionUtil;
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

    public final TransactionCard.NameFraction expense;

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
    public ExpenseListView(TransactionCard.NameFraction expense, int displayedIndex, boolean isOwed) {
        super(FXML);
        this.expense = expense;
        payer.setText(expense.getName().toString());
        amount.setText(FractionUtil.toString(expense.getFraction(), 2));
        if (isOwed && !expense.getName().equals(Name.SELF)) {
            change.setText("-" + FractionUtil.toString(expense.getFraction(), 2));
        } else {
            change.setText("0.00");
        }
    }

}
