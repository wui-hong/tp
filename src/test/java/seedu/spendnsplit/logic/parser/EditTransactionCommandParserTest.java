package seedu.spendnsplit.logic.parser;

import static seedu.spendnsplit.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.spendnsplit.logic.commands.EditTransactionCommand.MESSAGE_TRANSACTION_NOT_EDITED;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.spendnsplit.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.spendnsplit.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.spendnsplit.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.logic.commands.EditTransactionCommand;
import seedu.spendnsplit.logic.descriptors.TransactionDescriptor;
import seedu.spendnsplit.model.transaction.Amount;
import seedu.spendnsplit.model.transaction.Description;
import seedu.spendnsplit.model.transaction.Timestamp;
import seedu.spendnsplit.testutil.TransactionDescriptorBuilder;

class EditTransactionCommandParserTest {

    private static final String VALID_DESCRIPTION = "Dinner";
    private static final String VALID_COST = "10.00";

    private static final String VALID_TIMESTAMP = "10/10/2020 10:10";

    private static final String INVALID_DESCRIPTION = " ";

    private static final String INVALID_COST = "1.3.0.1";

    private static final String INVALID_TIMESTAMP = "20 October 2022";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE);

    private EditTransactionCommandParser parser = new EditTransactionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESCRIPTION, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_TRANSACTION_NOT_EDITED);

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
    public void parse_oneFieldSpecified_success() {
        // description
        Index targetIndex = INDEX_FIRST_ELEMENT;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION;
        TransactionDescriptor descriptor =
                new TransactionDescriptorBuilder().withDescription(VALID_DESCRIPTION).build();
        EditTransactionCommand expectedCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // cost
        userInput = targetIndex.getOneBased() + " " + PREFIX_COST + VALID_COST;
        descriptor = new TransactionDescriptorBuilder().withAmount(VALID_COST).build();
        expectedCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // payeeName
        userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + VALID_NAME_AMY;
        descriptor = new TransactionDescriptorBuilder().withPayeeName(VALID_NAME_AMY).build();
        expectedCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // timestamp
        userInput = targetIndex.getOneBased() + " " + PREFIX_TIMESTAMP + VALID_TIMESTAMP;
        descriptor = new TransactionDescriptorBuilder().withTimestamp(VALID_TIMESTAMP).build();
        expectedCommand = new EditTransactionCommand(targetIndex, descriptor);
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
