package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.commons.numbers.fraction.BigFraction;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.FractionUtil;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Transaction;

/**
 * An UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "TransactionListCard.fxml";
    private static final int ROW_HEIGHT = 20;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private HBox transactionCardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label payee;
    @FXML
    private Label amount;
    @FXML
    private Label change;
    @FXML
    private ListView<Name> expenseListView;

    /**
     * Creates a {@code TransactionCode} with the given {@code Transaction} and index to display.
     */
    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        Map<Name, BigFraction> subtotals = transaction.getAllPortions();
        ObservableList<Name> lst = new SortedList<>(FXCollections.observableArrayList(
            subtotals.keySet()), (name1, name2) -> name1.compareTo(name2));
        id.setText(displayedIndex + ". ");
        description.setText(transaction.getDescription().toString());
        date.setText(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss").format(transaction.getTimestamp().value));
        payee.setText(transaction.getPayeeName().toString());
        amount.setText(transaction.getAmount().toString());
        expenseListView.setItems(lst);
        if (transaction.getPayeeName().equals(Name.SELF)) {
            change.setText("-");
            expenseListView.setCellFactory(listView -> new ExpenseListViewCell(subtotals, true));
        } else {
            change.setText("+" + FractionUtil.toString(subtotals.get(Name.SELF), 2));
            expenseListView.setCellFactory(listView -> new ExpenseListViewCell(subtotals, false));
        }
        expenseListView.prefHeightProperty().bind(Bindings.size(lst).multiply(ROW_HEIGHT));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Expense} using an {@code ExpenseListView}.
     */
    class ExpenseListViewCell extends ListCell<Name> {
        private Map<Name, BigFraction> map;
        private boolean isCredit;

        ExpenseListViewCell(Map<Name, BigFraction> map, boolean isCredit) {
            this.map = map;
            this.isCredit = isCredit;
        }

        @Override
        protected void updateItem(Name name, boolean empty) {
            super.updateItem(name, empty);
            String subtotal = map.get(name).toString();
            String change = "-";
            if (isCredit && !Name.RESERVED_NAMES.contains(name)) {
                change = "-" + subtotal;
            }
            if (empty || name == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExpenseListView(name, subtotal, change, getIndex() + 1).getRoot());
            }
        }
    }
}
