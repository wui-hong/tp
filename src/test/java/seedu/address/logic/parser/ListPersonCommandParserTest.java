package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class ListPersonCommandParserTest {

    private final ListPersonCommandParser parser = new ListPersonCommandParser();

    @Test
    public void parse_emptyArg_returnsListCommand() {
        ListPersonCommand expectedFindPersonCommand =
            new ListPersonCommand(new NameContainsKeywordsPredicate(List.of()));
        assertParseSuccess(parser, "     ", expectedFindPersonCommand);
    }

    @Test
    public void parse_validArgs_returnsListCommand() {
        // no leading and trailing whitespaces
        ListPersonCommand expectedFindPersonCommand =
            new ListPersonCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindPersonCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindPersonCommand);
    }

}
