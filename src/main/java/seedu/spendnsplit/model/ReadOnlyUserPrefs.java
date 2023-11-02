package seedu.spendnsplit.model;

import java.nio.file.Path;

import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.logic.parser.CommandAliasMap;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    Path getSpendNSplitBookFilePath();

    CommandAliasMap getCommandMap();

    String setCommandAlias(String command, String alias) throws CommandException;

}
