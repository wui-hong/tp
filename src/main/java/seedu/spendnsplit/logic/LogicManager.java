package seedu.spendnsplit.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.spendnsplit.commons.core.LogsCenter;
import seedu.spendnsplit.logic.commands.Command;
import seedu.spendnsplit.logic.commands.CommandResult;
import seedu.spendnsplit.logic.commands.exceptions.CommandException;
import seedu.spendnsplit.logic.parser.SpendNSplitParser;
import seedu.spendnsplit.logic.parser.exceptions.ParseException;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.ReadOnlySpendNSplitBook;
import seedu.spendnsplit.model.person.Person;
import seedu.spendnsplit.model.transaction.Transaction;
import seedu.spendnsplit.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SpendNSplitParser spendNSplitParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        spendNSplitParser = new SpendNSplitParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = spendNSplitParser.parseCommand(commandText, model.getCommandMap());
        commandResult = command.execute(model);

        try {
            storage.saveSpendNSplitBook(model.getSpendNSplitBook());
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlySpendNSplitBook getSpendNSplitBook() {
        return model.getSpendNSplitBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Transaction> getFullTransactionList() {
        return model.getFullTransactionList();
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return model.getFilteredTransactionList();
    }

    @Override
    public Path getSpendNSplitBookFilePath() {
        return model.getSpendNSplitBookFilePath();
    }
}
