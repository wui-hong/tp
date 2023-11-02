package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DuplicateTransactionCommand;
import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Timestamp;
import seedu.address.testutil.EditTransactionDescriptorBuilder;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DuplicateCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DuplicateCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DuplicateTransactionCommandParserTest {

    private static final String VALID_DESCRIPTION = "Dinner";
    private static final String VALID_COST = "10.00";

    private static final String VALID_TIMESTAMP = "10/10/2020 10:10";

    private static final String INVALID_DESCRIPTION = " ";

    private static final String INVALID_COST = "1.3.0.1";

    private static final String INVALID_TIMESTAMP = "20 October 2022";

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DuplicateTransactionCommand.MESSAGE_USAGE);

    private DuplicateTransactionCommandParser parser = new DuplicateTransactionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESCRIPTION, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PREFIX_DESCRIPTION + VALID_DESCRIPTION, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PREFIX_DESCRIPTION + VALID_DESCRIPTION, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i=string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser,
            "1" + " " + PREFIX_DESCRIPTION + INVALID_DESCRIPTION, Description.MESSAGE_CONSTRAINTS);

        // invalid cost
        assertParseFailure(parser,
            "1" + " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION + " "
                + PREFIX_COST + INVALID_COST, Amount.MESSAGE_CONSTRAINTS);

        // invalid timestamp
        assertParseFailure(parser,
            "1" + " " + PREFIX_TIMESTAMP + INVALID_TIMESTAMP, Timestamp.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported (in order of description, cost, timestamp)
        assertParseFailure(parser,
            "1" + " " + PREFIX_COST + INVALID_COST + " "
                + PREFIX_DESCRIPTION + INVALID_DESCRIPTION, Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_noFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_ELEMENT;
        String userInput = targetIndex.getOneBased() + "";
        DuplicateTransactionCommand expectedCommand = new DuplicateTransactionCommand(targetIndex,
            new EditTransactionDescriptor());
        try {
            Command command = parser.parse(userInput);
            assertNotNull(command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // timestamp
        Index targetIndex = INDEX_FIRST_ELEMENT;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_TIMESTAMP + VALID_TIMESTAMP;
        EditTransactionDescriptor descriptor =
            new EditTransactionDescriptorBuilder().withTimestamp(VALID_TIMESTAMP).build();
        DuplicateTransactionCommand expectedCommand = new DuplicateTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_ELEMENT;
        String userInput = targetIndex.getOneBased() + " "
            + PREFIX_DESCRIPTION + VALID_DESCRIPTION + " " + PREFIX_DESCRIPTION + INVALID_DESCRIPTION;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + " "
            + PREFIX_DESCRIPTION + INVALID_DESCRIPTION + " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + " "
            + PREFIX_DESCRIPTION + VALID_DESCRIPTION + " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // multiple invalid fields repeated
        userInput = targetIndex.getOneBased() + " "
            + PREFIX_DESCRIPTION + INVALID_DESCRIPTION + " " + PREFIX_DESCRIPTION + INVALID_DESCRIPTION;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));
    }
}
