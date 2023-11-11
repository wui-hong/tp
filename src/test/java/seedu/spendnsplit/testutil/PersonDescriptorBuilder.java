package seedu.spendnsplit.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.spendnsplit.logic.descriptors.PersonDescriptor;
import seedu.spendnsplit.model.person.Address;
import seedu.spendnsplit.model.person.Email;
import seedu.spendnsplit.model.person.Name;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.person.Phone;
import seedu.spendnsplit.model.person.TelegramHandle;
import seedu.spendnsplit.model.tag.Tag;

/**
 * A utility class to help with building PersonDescriptor objects.
 */
public class PersonDescriptorBuilder {

    private PersonDescriptor descriptor;

    public PersonDescriptorBuilder() {
        descriptor = new PersonDescriptor();
    }

    public PersonDescriptorBuilder(PersonDescriptor descriptor) {
        this.descriptor = new PersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code PersonDescriptor} with fields containing {@code person}'s details.
     */
    public PersonDescriptorBuilder(Person person) {
        descriptor = new PersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setTelegramHandle(person.getTelegramHandle());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code PersonDescriptor} that we are building.
     */
    public PersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code PersonDescriptor} that we are building.
     */
    public PersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code TelegramHandle} of the {@code PersonDescriptor} that we are building.
     */
    public PersonDescriptorBuilder withTelegramHandle(String telegramHandle) {
        descriptor.setTelegramHandle(new TelegramHandle(telegramHandle));
        return this;
    }


    /**
     * Sets the {@code Email} of the {@code PersonDescriptor} that we are building.
     */
    public PersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code PersonDescriptor} that we are building.
     */
    public PersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code PersonDescriptor}
     * that we are building.
     */
    public PersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public PersonDescriptor build() {
        return descriptor;
    }
}
