package seedu.spendnsplit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;
import static seedu.spendnsplit.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.spendnsplit.commons.core.index.Index;
import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.logic.Messages;
import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.logic.descriptors.PersonDescriptor;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.person.Address;
import seedu.spendnsplit.model.person.Email;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.person.Phone;
import seedu.spendnsplit.model.person.TelegramHandle;
import seedu.spendnsplit.model.tag.Tag;

/**
 * Edits the details of an existing person in the app.
 */
public class EditPersonCommand extends Command {

    public static final String COMMAND_WORD = "editPerson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_TELEGRAM_HANDLE + "TELEGRAM_HANDLE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final PersonDescriptor personDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param personDescriptor details to edit the person with
     */
    public EditPersonCommand(Index index, PersonDescriptor personDescriptor) {
        requireNonNull(index);
        requireNonNull(personDescriptor);

        this.index = index;
        this.personDescriptor = new PersonDescriptor(personDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson;
        try {
            editedPerson = createEditedPerson(personToEdit, personDescriptor);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, PersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        TelegramHandle updatedTelegramHandle = editPersonDescriptor.getTelegramHandle()
                .orElse(personToEdit.getTelegramHandle());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedTelegramHandle, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPersonCommand)) {
            return false;
        }

        EditPersonCommand otherEditPersonCommand = (EditPersonCommand) other;
        return index.equals(otherEditPersonCommand.index)
                && personDescriptor.equals(otherEditPersonCommand.personDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", personDescriptor)
                .toString();
    }
}
