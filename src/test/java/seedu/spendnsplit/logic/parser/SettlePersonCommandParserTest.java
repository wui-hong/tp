package seedu.spendnsplit.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.spendnsplit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_TIMESTAMP;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.spendnsplit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.spendnsplit.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.spendnsplit.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.logic.commands.SettlePersonCommand;
import seedu.spendnsplit.model.transaction.Timestamp;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the SettlePersonCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the SettlePersonCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class SettlePersonCommandParserTest {

    private SettlePersonCommandParser parser = new SettlePersonCommandParser();

    @Test
    public void parse_validArgs_returnsSettlePersonCommand() throws Exception {
        assertParseSuccess(parser, "1 " + PREFIX_TIMESTAMP + VALID_TIMESTAMP,
                new SettlePersonCommand(INDEX_FIRST_ELEMENT, new Timestamp(VALID_TIMESTAMP)));
        assertTrue(parser.parse("1") instanceof SettlePersonCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SettlePersonCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 " + PREFIX_TIMESTAMP + "=a", Timestamp.MESSAGE_CONSTRAINTS);
    }
}
