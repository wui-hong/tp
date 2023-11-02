package seedu.spendnsplit.logic.parser;

import static seedu.spendnsplit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.COST_DESC_LUNCH;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.DESCRIPTION_DESC_LUNCH;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.DESC_TIMESTAMP;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_COST_LUNCH;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_DESCRIPTION_LUNCH;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_WEIGHT_ONE;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.WEIGHT_DESC_ONE;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.spendnsplit.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.logic.commands.AddTransactionCommand;
import seedu.spendnsplit.model.person.Name;

public class AddTransactionCommandParserTest {
    private AddTransactionCommandParser parser = new AddTransactionCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE);

        // missing description prefix
        assertParseFailure(parser, " " + VALID_DESCRIPTION_LUNCH + NAME_DESC_AMY + COST_DESC_LUNCH
                + " " + PREFIX_NAME + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, DESCRIPTION_DESC_LUNCH + " " + VALID_NAME_AMY + COST_DESC_LUNCH
                + " " + PREFIX_NAME + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing cost prefix
        assertParseFailure(parser, DESCRIPTION_DESC_LUNCH + NAME_DESC_AMY + " " + VALID_COST_LUNCH
                + " " + PREFIX_NAME + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, DESCRIPTION_DESC_LUNCH + NAME_DESC_AMY + COST_DESC_LUNCH
                + " " + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing weight prefix
        assertParseFailure(parser, DESCRIPTION_DESC_LUNCH + NAME_DESC_AMY + COST_DESC_LUNCH
                + " " + PREFIX_NAME + Name.SELF + " " + VALID_WEIGHT_ONE, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, " " + VALID_DESCRIPTION_LUNCH + NAME_DESC_AMY + COST_DESC_LUNCH
                + DESC_TIMESTAMP + " " + PREFIX_NAME + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, DESCRIPTION_DESC_LUNCH + " " + VALID_NAME_AMY + COST_DESC_LUNCH
                + " " + PREFIX_NAME + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing cost prefix
        assertParseFailure(parser, DESCRIPTION_DESC_LUNCH + NAME_DESC_AMY + " " + VALID_COST_LUNCH
                + DESC_TIMESTAMP + " " + PREFIX_NAME + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, DESCRIPTION_DESC_LUNCH + NAME_DESC_AMY + COST_DESC_LUNCH
                + DESC_TIMESTAMP + " " + Name.SELF + WEIGHT_DESC_ONE, expectedMessage);

        // missing weight prefix
        assertParseFailure(parser, DESCRIPTION_DESC_LUNCH + NAME_DESC_AMY + COST_DESC_LUNCH
                + DESC_TIMESTAMP + " " + PREFIX_NAME + Name.SELF + " " + VALID_WEIGHT_ONE, expectedMessage);
    }

}
