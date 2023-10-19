package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_LUNCH;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_LUNCH;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_LUNCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_LUNCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.model.person.Name;

public class AddTransactionCommandParserTest {
    private AddTransactionCommandParser parser = new AddTransactionCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser, " " + VALID_DESC_LUNCH + NAME_DESC_AMY + COST_DESC_LUNCH
                + " " + PREFIX_NAME + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, DESC_DESC_LUNCH + " " + VALID_NAME_AMY + COST_DESC_LUNCH
                + " " + PREFIX_NAME + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing cost prefix
        assertParseFailure(parser, DESC_DESC_LUNCH + NAME_DESC_AMY + " " + VALID_COST_LUNCH
                + " " + PREFIX_NAME + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, DESC_DESC_LUNCH + NAME_DESC_AMY + COST_DESC_LUNCH
                + " " + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing weight prefix
        assertParseFailure(parser, DESC_DESC_LUNCH + NAME_DESC_AMY + COST_DESC_LUNCH
                + " " + PREFIX_NAME + Name.SELF + " " + VALID_WEIGHT_ONE, expectedMessage);
    }

}
