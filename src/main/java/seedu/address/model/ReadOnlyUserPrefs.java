package seedu.address.model;

import java.nio.file.Path;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CommandAliasMap;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    Path getAddressBookFilePath();

    CommandAliasMap getCommandMap();

    String setCommandAlias(String command, String alias) throws CommandException;

}
