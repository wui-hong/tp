package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TransactionUtil.getEditTransactionDescriptorDetails;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditTransactionCommand;
import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.commands.ListTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionContainsPersonNamesPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TransactionBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddPersonCommand command = (AddPersonCommand) parser.parseCommand(PersonUtil.getAddPersonCommand(person));
        assertEquals(new AddPersonCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD + " " + INDEX_FIRST_ELEMENT.getOneBased());
        assertEquals(new DeletePersonCommand(INDEX_FIRST_ELEMENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditPersonCommand command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ELEMENT.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditPersonCommand(INDEX_FIRST_ELEMENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_WORD) instanceof ListPersonCommand);
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_WORD + " 3") instanceof ListPersonCommand);
    }

    @Test
    public void parseCommand_listTransaction() throws Exception {
        ListTransactionCommand command = (ListTransactionCommand) parser.parseCommand(
            ListTransactionCommand.COMMAND_WORD + " " + NAME_DESC_AMY + " " + NAME_DESC_BOB);
        TransactionContainsPersonNamesPredicate predicate = new TransactionContainsPersonNamesPredicate(
            List.of(new Name(VALID_NAME_AMY), new Name(VALID_NAME_BOB)));
        assertEquals(new ListTransactionCommand(predicate), command);
    }

    @Test
    public void parseCommand_addTransaction() throws Exception {
        assertTrue(parser.parseCommand(AddTransactionCommand.COMMAND_WORD
                + " d=bread n=Bob c=20.00 n=self w=1") instanceof AddTransactionCommand);
        assertTrue(parser.parseCommand(AddTransactionCommand.COMMAND_WORD
                + " d=bread n=Bob c=20.00 ts=2020-10-10T12:00 n=self w=1") instanceof AddTransactionCommand);
    }

    @Test
    public void parseCommand_editTransaction() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(transaction)
                .withoutPayeeNameAndExpenses().withoutTimestamp().build();
        EditTransactionCommand command = (EditTransactionCommand) parser.parseCommand(
                EditTransactionCommand.COMMAND_WORD + " " + INDEX_FIRST_ELEMENT.getOneBased() + " "
                        + getEditTransactionDescriptorDetails(descriptor));
        assertEquals(new EditTransactionCommand(INDEX_FIRST_ELEMENT, descriptor), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
