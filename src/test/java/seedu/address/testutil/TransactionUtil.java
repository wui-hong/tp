package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.address.logic.commands.EditTransactionCommand.EditTransactionDescriptor;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionUtil {

    /**
     * Returns the part of command string for the given {@code EditTransactionDescriptor}'s details.
     */
    public static String getEditTransactionDescriptorDetails(EditTransactionDescriptor descriptor) {
        //TODO: add payeeName and expenses in v1.3
        StringBuilder sb = new StringBuilder();
        descriptor.getAmount().ifPresent(amount ->
                sb.append(PREFIX_COST).append(amount).append(" "));
        descriptor.getDescription().ifPresent(description ->
                sb.append(PREFIX_DESCRIPTION).append(description.value).append(" "));
        return sb.toString();
    }
}
