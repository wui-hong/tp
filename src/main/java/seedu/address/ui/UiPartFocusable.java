package seedu.address.ui;

import java.net.URL;

/**
 * Represents a UiPart that can be focused on.
 */
public abstract class UiPartFocusable<T> extends UiPart<T> {

    /**
     * Constructs a UiPart with the specified FXML file URL.
     * The FXML file must not specify the {@code fx:controller} attribute.
     *
     * @param fxmlFileUrl
     */
    public UiPartFocusable(URL fxmlFileUrl) {
        super(fxmlFileUrl);
    }

    /**
     * Constructs a UiPart using the specified FXML file within {@link #FXML_FILE_FOLDER}.
     *
     * @param fxmlFileName
     * @see #UiPartFocusable(URL)
     */
    public UiPartFocusable(String fxmlFileName) {
        super(fxmlFileName);
    }

    /**
     * Constructs a UiPart with the specified FXML file URL and root object.
     * The FXML file must not specify the {@code fx:controller} attribute.
     *
     * @param fxmlFileUrl
     * @param root
     */
    public UiPartFocusable(URL fxmlFileUrl, T root) {
        super(fxmlFileUrl, root);
    }

    /**
     * Constructs a UiPart with the specified FXML file within {@link #FXML_FILE_FOLDER} and root object.
     *
     * @param fxmlFileName
     * @param root
     * @see #UiPartFocusable(URL, T)
     */
    public UiPartFocusable(String fxmlFileName, T root) {
        super(fxmlFileName, root);
    }

    /**
     * Focuses on the root node of UiPartFocusable.
     */
    public abstract void focus();

    /**
     * Un-focuses on the root node of UiPartFocusable.
     */
    public abstract void unFocus();
}
