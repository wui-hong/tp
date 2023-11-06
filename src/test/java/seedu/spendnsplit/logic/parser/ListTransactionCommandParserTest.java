package seedu.spendnsplit.logic.parser;

import static seedu.spendnsplit.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.spendnsplit.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.logic.commands.ListTransactionCommand;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.transaction.TransactionContainsKeywordsAndPersonNamesPredicate;

public class ListTransactionCommandParserTest {
    private final ListTransactionCommandParser parser = new ListTransactionCommandParser();

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero keywords
        ListTransactionCommand expectedListTransactionCommand =
            new ListTransactionCommand(new TransactionContainsKeywordsAndPersonNamesPredicate(List.of(), List.of()));
        assertParseSuccess(parser, "", expectedListTransactionCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n \t \n", expectedListTransactionCommand);
    }

    @Test
    public void parse_validNameFields_success() {
        // one keyword
        ListTransactionCommand expectedListTransactionCommand =
            new ListTransactionCommand(new TransactionContainsKeywordsAndPersonNamesPredicate(List.of(),
            List.of(new Name(VALID_NAME_AMY))));
        assertParseSuccess(parser, NAME_DESC_AMY, expectedListTransactionCommand);

        // multiple different keywords
        expectedListTransactionCommand =
            new ListTransactionCommand(new TransactionContainsKeywordsAndPersonNamesPredicate(List.of(), List.of(
                new Name(VALID_NAME_AMY),
                new Name(VALID_NAME_BOB)
            )));
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB, expectedListTransactionCommand);

        // duplicate keywords
        expectedListTransactionCommand =
            new ListTransactionCommand(new TransactionContainsKeywordsAndPersonNamesPredicate(List.of(),
            List.of(new Name(VALID_NAME_AMY))));
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_AMY, expectedListTransactionCommand);
    }

    @Test
    public void parse_validKeywordFields_success() {
        // one keyword
        ListTransactionCommand expectedListTransactionCommand =
                new ListTransactionCommand(new TransactionContainsKeywordsAndPersonNamesPredicate(List.of("Lunch"),
                List.of()));
        assertParseSuccess(parser, "Lunch", expectedListTransactionCommand);

        // multiple different keywords
        expectedListTransactionCommand =
                new ListTransactionCommand(new TransactionContainsKeywordsAndPersonNamesPredicate(List
                .of("Lunch", "Dinner"), List.of()));
        assertParseSuccess(parser, "Lunch Dinner", expectedListTransactionCommand);

        // duplicate keywords
        expectedListTransactionCommand =
                new ListTransactionCommand(new TransactionContainsKeywordsAndPersonNamesPredicate(List.of("Lunch"),
                List.of()));
        assertParseSuccess(parser, "Lunch Lunch", expectedListTransactionCommand);
    }

    @Test
    public void parse_validBothFields_success() {
        // one keyword
        ListTransactionCommand expectedListTransactionCommand =
                new ListTransactionCommand(new TransactionContainsKeywordsAndPersonNamesPredicate(List.of("Lunch"),
                List.of(new Name(VALID_NAME_AMY))));
        assertParseSuccess(parser, "Lunch" + NAME_DESC_AMY, expectedListTransactionCommand);

        // multiple different keywords
        expectedListTransactionCommand =
                new ListTransactionCommand(new TransactionContainsKeywordsAndPersonNamesPredicate(List
                .of("Lunch", "Dinner"), List.of(new Name(VALID_NAME_AMY), new Name(VALID_NAME_BOB))));
        assertParseSuccess(parser, "Lunch Dinner" + NAME_DESC_AMY + NAME_DESC_BOB, expectedListTransactionCommand);
    }
}
