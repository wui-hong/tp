package seedu.spendnsplit.testutil;

import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.spendnsplit.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.spendnsplit.logic.descriptors.TransactionDescriptor;


/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionUtil {

    /**
     * Returns the part of command string for the given {@code TransactionDescriptor}'s details.
     */
    public static String getTransactionDescriptorDetails(TransactionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getAmount().ifPresent(amount ->
                sb.append(PREFIX_COST).append(amount).append(" "));
        descriptor.getDescription().ifPresent(description ->
                sb.append(PREFIX_DESCRIPTION).append(description.value).append(" "));
        descriptor.getPayeeName().ifPresent(payeeName ->
                sb.append(PREFIX_NAME).append(payeeName).append(" "));
        descriptor.getTimestamp().ifPresent(timestamp ->
                sb.append(PREFIX_TIMESTAMP).append(timestamp).append(" "));
        return sb.toString();
    }
}
