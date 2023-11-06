package seedu.spendnsplit.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.spendnsplit.commons.core.LogsCenter;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.model.transaction.UniqueTransactionList;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPartFocusable<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, ObservableList<Transaction> transactionList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell(transactionList));
    }

    /**
     * Focuses on the person list.
     */
    public void focus() {
        if (!personListView.getItems().isEmpty()) {
            personListView.requestFocus();
            personListView.getSelectionModel().select(0);
        }
    }

    /**
     * Un-focuses on the person list.
     * This is done by clearing the selection in the list view.
     */
    public void unFocus() {
        personListView.getSelectionModel().clearSelection();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {

        private ObservableList<Transaction> transactions;

        PersonListViewCell(ObservableList<Transaction> transactions) {
            this.transactions = transactions;
        }

        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person,
                        UniqueTransactionList.getBalance(person.getName(), transactions), getIndex() + 1).getRoot());
            }
        }
    }

}
