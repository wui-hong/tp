package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandAliasMap;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");
    private CommandAliasMap commandMap = new CommandAliasMap();

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
        this.commandMap = new CommandAliasMap(newUserPrefs.getCommandMap());
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    @Override
    public String setCommandAlias(String command, String alias) throws CommandException {
        return commandMap.putAlias(command, alias);
    }

    @Override
    public CommandAliasMap getCommandMap() {
        return commandMap;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserPrefs)) {
            return false;
        }

        UserPrefs otherUserPrefs = (UserPrefs) other;
        return addressBookFilePath.equals(otherUserPrefs.addressBookFilePath)
                && commandMap.equals(otherUserPrefs.commandMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressBookFilePath, commandMap);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nCommand map: " + commandMap);
        return sb.toString();
    }

}
