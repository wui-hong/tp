package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_HALF;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertTransactionCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ELEMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ELEMENT;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdatePortionCommand.UpdatePortionDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.portion.Portion;
import seedu.address.testutil.PortionBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.UpdatePortionDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdatePortionCommand.
 */
class UpdatePortionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_editExistingPortionWeightNonZeroUnfilteredList_success() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(1);
        Set<Portion> originalPortions = originalTransaction.getPortions();
        Portion originalPortion = originalTransaction.getPortions().stream().iterator().next();
        Portion editedPortion = new PortionBuilder(originalPortion).withWeight("8").build();
        Set<Portion> editedPortions = originalPortions.stream().map(portion ->
                portion.equals(originalPortion) ? editedPortion : portion).collect(Collectors.toSet());
        Transaction editedTransaction = new TransactionBuilder(originalTransaction)
                .withPortions(editedPortions).build();

        UpdatePortionDescriptor descriptor = new UpdatePortionDescriptorBuilder(originalPortion)
                .withWeight("1/2").build();
        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(INDEX_SECOND_ELEMENT, descriptor);

        String expectedMessage = String.format(UpdatePortionCommand.MESSAGE_UPDATE_PORTION_SUCCESS,
                Messages.format(editedTransaction));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(1), editedTransaction);

        assertTransactionCommandSuccess(updatePortionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyPortion_failure() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(0);
        Portion originalPortion = originalTransaction.getPortions().stream().iterator().next();

        UpdatePortionDescriptor descriptor = new UpdatePortionDescriptorBuilder(originalPortion)
                .withWeight("1/2").build();
        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = UpdatePortionCommand.MESSAGE_ONLY_PORTION;

        assertCommandFailure(updatePortionCommand, model, expectedMessage);
    }

    @Test
    public void execute_deleteExistingPortionUnfilteredList_success() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(2);
        Set<Portion> originalPortions = originalTransaction.getPortions();
        Portion originalPortion = originalTransaction.getPortions().stream().iterator().next();

        Set<Portion> editedPortions = originalPortions.stream().filter(portion ->
                !portion.equals(originalPortion)).collect(Collectors.toSet());
        Transaction editedTransaction = new TransactionBuilder(originalTransaction)
                .withPortions(editedPortions).build();

        UpdatePortionDescriptor descriptor = new UpdatePortionDescriptorBuilder(originalPortion)
                .withWeight("0").build();
        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(INDEX_THIRD_ELEMENT, descriptor);

        String expectedMessage = String.format(UpdatePortionCommand.MESSAGE_UPDATE_PORTION_SUCCESS,
                Messages.format(editedTransaction));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(2), editedTransaction);

        assertTransactionCommandSuccess(updatePortionCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Delete the only portion in the transaction.
     */
    @Test
    public void execute_deleteOnlyExistingPortionUnfilteredList_failure() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(0);
        Portion originalPortion = originalTransaction.getPortions().stream().iterator().next();
        UpdatePortionDescriptor descriptor = new UpdatePortionDescriptorBuilder(originalPortion)
                .withWeight("0").build();
        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(INDEX_FIRST_ELEMENT, descriptor);

        CommandTestUtil.assertCommandFailure(updatePortionCommand, model,
                UpdatePortionCommand.MESSAGE_DELETE_ONLY_PORTION_FAILURE);
    }

    /**
     * Deletes a portion that results in an irrelevant transaction.
     * i.e. Transaction does not involve yourself.
     */
    @Test
    public void execute_deleteExistingPortionIrrelevantTransactionUnfilteredList_failure() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(2);
        Portion originalPortion = originalTransaction.getPortions().stream().filter(portion ->
                portion.getPersonName().equals(Name.SELF)).iterator().next();
        UpdatePortionDescriptor descriptor = new UpdatePortionDescriptorBuilder(originalPortion)
                .withWeight("0").build();
        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(INDEX_THIRD_ELEMENT, descriptor);
        String expectedMessage = UpdatePortionCommand.MESSAGE_IRRELEVANT_UPDATED_TRANSACTION;
        CommandTestUtil.assertCommandFailure(updatePortionCommand, model, expectedMessage);
    }

    @Test
    public void execute_addNewPortionUnfilteredList_success() {
        Transaction originalTransaction = model.getFilteredTransactionList().get(0);

        Portion newPortion = new PortionBuilder().withName(CARL.getName().fullName).withWeight("1").build();
        Set<Portion> editedPortions = originalTransaction.getPortionsCopy();
        editedPortions.add(newPortion);
        Transaction editedTransaction = new TransactionBuilder(originalTransaction)
                .withPortions(editedPortions).build();

        UpdatePortionDescriptor descriptor = new UpdatePortionDescriptorBuilder(newPortion).withWeight("1/3").build();
        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = String.format(UpdatePortionCommand.MESSAGE_UPDATE_PORTION_SUCCESS,
                Messages.format(editedTransaction));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertTransactionCommandSuccess(updatePortionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unknownName_failure() {
        Portion newPortion = new PortionBuilder().withName(VALID_NAME_AMY).withWeight("1/2").build();

        UpdatePortionDescriptor descriptor = new UpdatePortionDescriptorBuilder(newPortion).build();
        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = UpdatePortionCommand.MESSAGE_UNKNOWN_PARTY;
        assertCommandFailure(updatePortionCommand, model, expectedMessage);
    }

    public void execute_highWeight_failure() {
        Portion newPortion = new PortionBuilder().withName(VALID_NAME_AMY).withWeight("1").build();
        UpdatePortionDescriptor descriptor = new UpdatePortionDescriptorBuilder(newPortion).withWeight("1").build();
        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(INDEX_FIRST_ELEMENT, descriptor);

        String expectedMessage = UpdatePortionCommand.MESSAGE_INVALID_PROPORTION;
        assertCommandFailure(updatePortionCommand, model, expectedMessage);
    }

    @Test
    public void execute_filteredList_success() {
        showTransactionAtIndex(model, INDEX_FIRST_ELEMENT);

        Transaction transactionInFilteredList = model.getFilteredTransactionList()
                .get(INDEX_FIRST_ELEMENT.getZeroBased());

        Portion newPortion = new PortionBuilder().withName(CARL.getName().fullName).withWeight("1").build();
        Set<Portion> editedPortions = transactionInFilteredList.getPortionsCopy();
        editedPortions.add(newPortion);
        Transaction editedTransaction = new TransactionBuilder(transactionInFilteredList)
                .withPortions(editedPortions).build();

        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(INDEX_FIRST_ELEMENT,
                new UpdatePortionDescriptorBuilder(newPortion).withWeight("1/3").build());

        String expectedMessage = String.format(UpdatePortionCommand.MESSAGE_UPDATE_PORTION_SUCCESS,
                Messages.format(editedTransaction));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTransaction(model.getFilteredTransactionList().get(0), editedTransaction);

        assertTransactionCommandSuccess(updatePortionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTransactionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        UpdatePortionDescriptor descriptor = new UpdatePortionDescriptorBuilder()
                .withPersonName(CARL.getName().fullName).withWeight(VALID_WEIGHT_HALF).build();
        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(updatePortionCommand, model,
                Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTransactionIndexFilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        UpdatePortionDescriptor descriptor = new UpdatePortionDescriptorBuilder()
                .withPersonName(CARL.getName().fullName).withWeight(VALID_WEIGHT_HALF).build();
        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(updatePortionCommand, model,
                Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final UpdatePortionDescriptor standardDescriptor = new UpdatePortionDescriptorBuilder()
                .withPersonName(VALID_NAME_AMY).withWeight(VALID_WEIGHT_HALF).build();
        final UpdatePortionCommand standardCommand = new UpdatePortionCommand(INDEX_FIRST_ELEMENT, standardDescriptor);

        // same values -> returns true
        UpdatePortionDescriptor copyDescriptor = new UpdatePortionDescriptorBuilder()
                .withPersonName(VALID_NAME_AMY).withWeight(VALID_WEIGHT_HALF).build();
        UpdatePortionCommand commandWithSameValues = new UpdatePortionCommand(INDEX_FIRST_ELEMENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertTrue(!standardCommand.equals(null));

        // different types -> returns false
        assertTrue(!standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertTrue(!standardCommand.equals(new UpdatePortionCommand(INDEX_SECOND_ELEMENT, copyDescriptor)));

        // different descriptor -> returns false
        UpdatePortionDescriptor differentDescriptor = new UpdatePortionDescriptorBuilder()
                .withPersonName(VALID_NAME_BOB).withWeight("100").build();
        assertFalse(standardCommand.equals(new UpdatePortionCommand(INDEX_FIRST_ELEMENT, differentDescriptor)));
    }

    @Test
    public void toStringMethod() {
        Index index = INDEX_FIRST_ELEMENT;
        UpdatePortionDescriptor updatePortionDescriptor = new UpdatePortionDescriptorBuilder()
                .withPersonName(VALID_NAME_AMY).withWeight(VALID_WEIGHT_HALF).build();
        UpdatePortionCommand updatePortionCommand = new UpdatePortionCommand(index, updatePortionDescriptor);
        String expectedString = UpdatePortionCommand.class.getCanonicalName()
                + "{index=" + index
                + ", updatePortionDescriptor=" + updatePortionDescriptor + "}";
        assertEquals(expectedString, updatePortionCommand.toString());
    }
}
