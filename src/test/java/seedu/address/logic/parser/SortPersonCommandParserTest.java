package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortPersonCommand;

public class SortPersonCommandParserTest {

    private SortPersonCommandParser parser = new SortPersonCommandParser();

    @Test
    public void parse_validArgs_returnsSettlePersonCommand() {
        assertParseSuccess(parser, "+", new SortPersonCommand(true));
        assertParseSuccess(parser, "-", new SortPersonCommand(false));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortPersonCommand.MESSAGE_USAGE));
    }

}
