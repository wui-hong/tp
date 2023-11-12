package seedu.spendnsplit.logic.descriptors;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.spendnsplit.commons.util.CollectionUtil;
import seedu.spendnsplit.commons.util.ToStringBuilder;
import seedu.spendnsplit.model.person.Address;
import seedu.spendnsplit.model.person.Email;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.person.Phone;
import seedu.spendnsplit.model.person.TelegramHandle;
import seedu.spendnsplit.model.tag.Tag;

/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public class PersonDescriptor {
    private Name name;
    private Phone phone;
    private TelegramHandle telegramHandle;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public PersonDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public PersonDescriptor(PersonDescriptor toCopy) {
        setName(toCopy.name);
        setPhone(toCopy.phone);
        setTelegramHandle(toCopy.telegramHandle);
        setEmail(toCopy.email);
        setAddress(toCopy.address);
        setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, phone, telegramHandle, email, address, tags);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Optional<Phone> getPhone() {
        return Optional.ofNullable(phone);
    }

    public void setTelegramHandle(TelegramHandle telegramHandle) {
        this.telegramHandle = telegramHandle;
    }

    public Optional<TelegramHandle> getTelegramHandle() {
        return Optional.ofNullable(telegramHandle);
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Optional<Email> getEmail() {
        return Optional.ofNullable(email);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDescriptor)) {
            return false;
        }

        PersonDescriptor otherPersonDescriptor = (PersonDescriptor) other;
        return Objects.equals(name, otherPersonDescriptor.name)
            && Objects.equals(phone, otherPersonDescriptor.phone)
            && Objects.equals(telegramHandle, otherPersonDescriptor.telegramHandle)
            && Objects.equals(email, otherPersonDescriptor.email)
            && Objects.equals(address, otherPersonDescriptor.address)
            && Objects.equals(tags, otherPersonDescriptor.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", name)
            .add("phone", phone)
            .add("telegramHandle", telegramHandle)
            .add("email", email)
            .add("address", address)
            .add("tags", tags)
            .toString();
    }
}
