package seedu.spendnsplit.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.logic.parser.CommandAliasMap;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private Path spendNSplitBookFilePath = Paths.get("data" , "spendNSplitbook.json");
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
        setSpendNSplitBookFilePath(newUserPrefs.getSpendNSplitBookFilePath());
        this.commandMap = new CommandAliasMap(newUserPrefs.getCommandMap());
    }

    public Path getSpendNSplitBookFilePath() {
        return spendNSplitBookFilePath;
    }

    public void setSpendNSplitBookFilePath(Path spendNSplitBookFilePath) {
        requireNonNull(spendNSplitBookFilePath);
        this.spendNSplitBookFilePath = spendNSplitBookFilePath;
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
        return spendNSplitBookFilePath.equals(otherUserPrefs.spendNSplitBookFilePath)
                && commandMap.equals(otherUserPrefs.commandMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spendNSplitBookFilePath, commandMap);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nLocal data file location : " + spendNSplitBookFilePath);
        sb.append("\nCommand map: " + commandMap);
        return sb.toString();
    }

}
