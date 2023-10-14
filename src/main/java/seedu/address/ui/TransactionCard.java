package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

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
    private ListView<NameFraction> expenseListView;

    /**
     * Creates a {@code TransactionCode} with the given {@code Transaction} and index to display.
     */
    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        Map<Name, BigFraction> subtotals = transaction.getAllPortions();
        ObservableList<NameFraction> lst = new SortedList<>(FXCollections.observableArrayList(subtotals.keySet()
                .stream().map(name -> new NameFraction(name, subtotals.get(name))).collect(
                Collectors.toList())), (exp1, exp2) -> exp1.name.compareTo(exp2.name));
        id.setText(displayedIndex + ". ");
        description.setText(transaction.getDescription().toString());
        date.setText(DateTimeFormatter.ofPattern("dd MMM yyyy HH:MM:SS").format(transaction.getTimestamp().value));
        payee.setText(transaction.getPayeeName().toString());
        amount.setText(transaction.getAmount().toString());
        expenseListView.setItems(lst);
        if (transaction.getPayeeName().equals(Name.SELF)) {
            change.setText("0.00");
            expenseListView.setCellFactory(listView -> new CreditListViewCell());
        } else {
            change.setText("+" + FractionUtil.toString(subtotals.get(Name.SELF), 2));
            expenseListView.setCellFactory(listView -> new DebitListViewCell());
        }
        expenseListView.prefHeightProperty().bind(Bindings.size(lst).multiply(20));
    }

    class NameFraction {
        private Name name;
        private BigFraction fraction;

        NameFraction(Name name, BigFraction fraction) {
            this.name = name;
            this.fraction = fraction;
        }

        public Name getName() {
            return name;
        }

        public BigFraction getFraction() {
            return fraction;
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Expense} using an {@code ExpenseListView}.
     */
    class CreditListViewCell extends ListCell<NameFraction> {
        @Override
        protected void updateItem(NameFraction expense, boolean empty) {
            super.updateItem(expense, empty);

            if (empty || expense == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExpenseListView(expense, getIndex() + 1, true).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Expense} using an {@code ExpenseListView}.
     */
    class DebitListViewCell extends ListCell<NameFraction> {
        @Override
        protected void updateItem(NameFraction expense, boolean empty) {
            super.updateItem(expense, empty);

            if (empty || expense == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExpenseListView(expense, getIndex() + 1, false).getRoot());
            }
        }
    }

}
