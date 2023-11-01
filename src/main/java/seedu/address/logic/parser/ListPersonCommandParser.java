package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPersonCommand object
 */
public class ListPersonCommandParser implements Parser<ListPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonCommand
     * and returns a FindPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListPersonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListPersonCommand(new NameContainsKeywordsPredicate(List.of()));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new ListPersonCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
