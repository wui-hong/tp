package seedu.spendnsplit.logic.parser;

import static seedu.spendnsplit.logic.Messages.MESSAGE_DUPLICATE_ALIAS;
import static seedu.spendnsplit.logic.Messages.MESSAGE_SHORTHAND_IS_COMMAND;
import static seedu.spendnsplit.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.logic.commands.AddPersonCommand;
import seedu.spendnsplit.logic.commands.AddTransactionCommand;
import seedu.spendnsplit.logic.commands.ClearCommand;
import seedu.spendnsplit.logic.commands.DeletePersonCommand;
import seedu.spendnsplit.logic.commands.DeleteTransactionCommand;
import seedu.spendnsplit.logic.commands.DuplicateTransactionCommand;
import seedu.spendnsplit.logic.commands.EditPersonCommand;
import seedu.spendnsplit.logic.commands.EditTransactionCommand;
import seedu.spendnsplit.logic.commands.ExitCommand;
import seedu.spendnsplit.logic.commands.HelpCommand;
import seedu.spendnsplit.logic.commands.ListPersonCommand;
import seedu.spendnsplit.logic.commands.ListTransactionCommand;
import seedu.spendnsplit.logic.commands.SetShorthandCommand;
import seedu.spendnsplit.logic.commands.SettlePersonCommand;
import seedu.spendnsplit.logic.commands.SortPersonCommand;
import seedu.spendnsplit.logic.commands.UpdatePortionCommand;
import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.logic.parser.exceptions.ParseException;

/**
 * Class that maps aliases to commands.
 */
public class CommandAliasMap {

    public static final String VALIDATION_REGEX = "^[a-zA-Z][a-zA-Z]*$";

    public static final Set<String> FULL_COMMANDS = new HashSet<>() {{
            add(AddPersonCommand.COMMAND_WORD);
            add(AddTransactionCommand.COMMAND_WORD);
            add(ClearCommand.COMMAND_WORD);
            add(DeletePersonCommand.COMMAND_WORD);
            add(DeleteTransactionCommand.COMMAND_WORD);
            add(DuplicateTransactionCommand.COMMAND_WORD);
            add(EditPersonCommand.COMMAND_WORD);
            add(EditTransactionCommand.COMMAND_WORD);
            add(ExitCommand.COMMAND_WORD);
            add(HelpCommand.COMMAND_WORD);
            add(ListPersonCommand.COMMAND_WORD);
            add(ListTransactionCommand.COMMAND_WORD);
            add(SetShorthandCommand.COMMAND_WORD);
            add(SettlePersonCommand.COMMAND_WORD);
            add(SortPersonCommand.COMMAND_WORD);
            add(UpdatePortionCommand.COMMAND_WORD);
        }};

    private Map<String, String> aliasToCommand;

    /**
     * Constructs a CommandAliasMap.
     */
    public CommandAliasMap() {
        this.aliasToCommand = new HashMap<>();
    }

    /**
     * Copies a CommandAliasMap.
     */
    public CommandAliasMap(CommandAliasMap copy) {
        this.aliasToCommand = new HashMap<>(copy.aliasToCommand);
    }

    /**
     * Gets to full input command from the alias.
     */
    public String getCommand(String inputCommand) throws ParseException {
        if (FULL_COMMANDS.contains(inputCommand)) {
            return inputCommand;
        }
        if (aliasToCommand.containsKey(inputCommand)) {
            return aliasToCommand.get(inputCommand);
        }
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Returns aliasToCommand map.
     */
    public Map<String, String> getMap() {
        return aliasToCommand;
    }

    /**
     * Sets the alias of a given command to alias.
     */
    public String putAlias(String command, String alias) throws CommandException {
        if (!FULL_COMMANDS.contains(command)) {
            throw new CommandException(MESSAGE_UNKNOWN_COMMAND + " " + command);
        }
        if (FULL_COMMANDS.contains(alias)) {
            throw new CommandException(MESSAGE_SHORTHAND_IS_COMMAND + " " + command);
        }
        if (aliasToCommand.containsKey(alias)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_ALIAS, alias, aliasToCommand.get(alias)));
        }
        String previousAlias = null;
        for (String key : aliasToCommand.keySet()) {
            if (aliasToCommand.get(key).equals(command)) {
                previousAlias = key;
            }
        }
        if (previousAlias != null) {
            aliasToCommand.remove(previousAlias);
        }
        aliasToCommand.put(alias, command);
        return previousAlias;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CommandAliasMap)) {
            return false;
        }

        CommandAliasMap otherMap = (CommandAliasMap) other;
        return aliasToCommand.equals(otherMap.aliasToCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliasToCommand);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("aliasToCommand", aliasToCommand)
                .toString();
    }

}
