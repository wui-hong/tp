package seedu.address.ui;

import javafx.scene.control.Label;
import seedu.address.model.person.Name;

/**
 * Util class that adds a name to a label, with consideration for special names.
 */
public class NameLabel {
    private static final String SELF_LABEL = "You";

    /**
     * Sets the Name {@code name} to the Label {@code l}.
     */
    public static void setNameLabel(Label l, Name name) {
        if (name.equals(Name.SELF)) {
            l.setStyle("-fx-font-family: \"Poppins Medium\"");
            l.setText(NameLabel.SELF_LABEL);
        } else if (name.equals(Name.OTHERS)) {
            l.setStyle("-fx-font-family: \"Poppins Italic\"");
            l.setText(Name.OTHERS.toString());
        } else {
            l.setStyle("-fx-font-family: \"Poppins Regular\"");
            l.setText(name.toString());
        }
    }
}
