package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_HALF;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_HALF;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdatePortionCommand;
import seedu.address.logic.commands.UpdatePortionCommand.UpdatePortionDescriptor;
import seedu.address.testutil.UpdatePortionDescriptorBuilder;

class UpdatePortionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdatePortionCommand.MESSAGE_USAGE);


    private UpdatePortionCommandParser parser = new UpdatePortionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, " " + NAME_DESC_AMY + WEIGHT_DESC_HALF, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, " 1", MESSAGE_INVALID_FORMAT);

        // some fields specified
        assertParseFailure(parser, " 1" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " 1" + WEIGHT_DESC_HALF, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ELEMENT;
        String userInput = " " + targetIndex.getOneBased() + " " + NAME_DESC_AMY + WEIGHT_DESC_HALF;
        UpdatePortionDescriptor descriptor = new UpdatePortionDescriptorBuilder()
                .withPersonName(VALID_NAME_AMY).withWeight(VALID_WEIGHT_HALF).build();
        UpdatePortionCommand expectedCommand = new UpdatePortionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_ELEMENT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + WEIGHT_DESC_HALF + NAME_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        userInput = targetIndex.getOneBased() + NAME_DESC_AMY + WEIGHT_DESC_ONE + WEIGHT_DESC_HALF;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WEIGHT));
    }

}
