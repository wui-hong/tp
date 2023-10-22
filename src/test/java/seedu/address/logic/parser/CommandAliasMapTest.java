package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SetShorthandCommand;
import seedu.address.logic.commands.exceptions.CommandException;

public class CommandAliasMapTest {
    @Test
    public void put() {
        CommandAliasMap map = new CommandAliasMap();
        try {
            map.putAlias(SetShorthandCommand.COMMAND_WORD, "a");
        } catch (Exception e) {
            assertTrue(e == null);
        }
        try {
            map.putAlias(SetShorthandCommand.COMMAND_WORD, "a");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e instanceof CommandException);
        }
        try {
            map.putAlias("a", "a");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e instanceof CommandException);
        }
    }

    @Test
    public void equals() {
        CommandAliasMap map1 = new CommandAliasMap();
        CommandAliasMap map2 = new CommandAliasMap();
        assertTrue(map1.equals(map1));
        assertTrue(map1.equals(map2));
        assertFalse(map1.equals(null));
        assertFalse(map1.equals(1));
        try {
            map2.putAlias(SetShorthandCommand.COMMAND_WORD, "a");
            assertFalse(map1.equals(map2));
        } catch (Exception e) {
            assertTrue(e == null);
        }
    }
}
