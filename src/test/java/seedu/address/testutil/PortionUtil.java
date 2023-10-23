package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import seedu.address.logic.commands.UpdatePortionCommand.UpdatePortionDescriptor;

/**
 * A utility class to help with building Portion objects.
 */
public class PortionUtil {

    /**
     * Returns the part of command string for the given {@code UpdatePortionDescriptor}'s details.\
     */
    public static String getUpdatePortionDescriptorDetails(UpdatePortionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(descriptor.getPersonName().toString()).append(" ");
        sb.append(PREFIX_WEIGHT).append(descriptor.getWeight().toString()).append(" ");
        return sb.toString();
    }
}
