package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPartFocusable<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    /**
     * Focuses on the result display.
     */
    public void focus() {
        resultDisplay.requestFocus();
    }

    /**
     * Un-focuses on the result display.
     * Command box automatically un-focuses when another UI element is focused.
     */
    public void unFocus() {
        // result display automatically un-focuses when another UI element is focused
    }
}
