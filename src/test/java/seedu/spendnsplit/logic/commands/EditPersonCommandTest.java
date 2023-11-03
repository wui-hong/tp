package seedu.spendnsplit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.spendnsplit.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.spendnsplit.testutil.TypicalSpendNSplitBook.getTypicalSpendNSplitBook;
import static seedu.spendnsplit.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.spendnsplit.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;

import org.junit.jupiter.api.Test;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.logic.commands.EditPersonCommand.EditPersonDescriptor;
import seedu.spendnsplit.model.SpendNSplit;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.ModelManager;
import seedu.spendnsplit.model.UserPrefs;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.testutil.EditPersonDescriptorBuilder;
import seedu.spendnsplit.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditPersonCommand.
 */
public class EditPersonCommandTest {

    private Model model = new ModelManager(getTypicalSpendNSplitBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new SpendNSplit(model.getSpendNSplitBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editPersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new SpendNSplit(model.getSpendNSplitBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editPersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditPersonCommand editPersonCommand = new EditPersonCommand(INDEX_FIRST_ELEMENT, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_ELEMENT.getZeroBased());

        String expectedMessage = String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new SpendNSplit(model.getSpendNSplitBook()), new UserPrefs());

        assertCommandSuccess(editPersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_ELEMENT);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(INDEX_FIRST_ELEMENT,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new SpendNSplit(model.getSpendNSplitBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editPersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(INDEX_SECOND_ELEMENT, descriptor);

        assertCommandFailure(editPersonCommand, model, EditPersonCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_reservedPersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_ELEMENT.getZeroBased());
        EditPersonDescriptor descriptor =
                new EditPersonDescriptorBuilder(firstPerson).withName(Name.SELF.fullName).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(INDEX_FIRST_ELEMENT, descriptor);

        assertCommandFailure(editPersonCommand, model, String.format(Name.RESERVED_CONSTRAINTS, Name.SELF.fullName));
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_ELEMENT);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getSpendNSplitBook().getPersonList().get(INDEX_SECOND_ELEMENT.getZeroBased());
        EditPersonCommand editPersonCommand = new EditPersonCommand(INDEX_FIRST_ELEMENT,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editPersonCommand, model, EditPersonCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditPersonCommand editPersonCommand = new EditPersonCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_ELEMENT);
        Index outOfBoundIndex = INDEX_SECOND_ELEMENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSpendNSplitBook().getPersonList().size());

        EditPersonCommand editPersonCommand = new EditPersonCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditPersonCommand standardCommand = new EditPersonCommand(INDEX_FIRST_ELEMENT, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditPersonCommand commandWithSameValues = new EditPersonCommand(INDEX_FIRST_ELEMENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPersonCommand(INDEX_SECOND_ELEMENT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPersonCommand(INDEX_FIRST_ELEMENT, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditPersonCommand editPersonCommand = new EditPersonCommand(index, editPersonDescriptor);
        String expected = EditPersonCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editPersonCommand.toString());
    }

}
