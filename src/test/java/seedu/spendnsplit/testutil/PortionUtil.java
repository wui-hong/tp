package seedu.spendnsplit.testutil;

import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_WEIGHT;

import seedu.spendnsplit.logic.descriptors.PortionDescriptor;

/**
 * A utility class to help with building Portion objects.
 */
public class PortionUtil {

    /**
     * Returns the part of command string for the given {@code PortionDescriptor}'s details.
     */
    public static String getPortionDescriptorDetails(PortionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(descriptor.getPersonName().toString()).append(" ");
        sb.append(PREFIX_WEIGHT).append(descriptor.getWeight().toString()).append(" ");
        return sb.toString();
    }
}
