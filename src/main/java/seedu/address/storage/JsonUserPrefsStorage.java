package seedu.address.storage;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_ALIAS;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.parser.CommandAliasMap;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonUserPrefsStorage implements UserPrefsStorage {

    public static final String MESSAGE_SHORTHAND_IS_COMMAND = "Existing commands cannot be shorthands!";
    public static final String MESSAGE_EMPTY_FIELD = "Command or alias cannot be empty!";

    private static final Logger logger = LogsCenter.getLogger(JsonUserPrefsStorage.class);

    private Path filePath;

    public JsonUserPrefsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUserPrefsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataLoadingException if the file format is not as expected.
     */
    public Optional<UserPrefs> readUserPrefs(Path prefsFilePath) throws DataLoadingException {
        Optional<UserPrefs> readValue = JsonUtil.readJsonFile(prefsFilePath, UserPrefs.class);
        if (!readValue.isPresent()) {
            return readValue;
        }
        if (readValue.orElse(new UserPrefs()).getAddressBookFilePath() == null) {
            readValue.orElse(new UserPrefs()).setAddressBookFilePath(Paths.get("data" , "addressbook.json"));
        }
        if (readValue.orElse(new UserPrefs()).getCommandMap() == null) {
            readValue.orElse(new UserPrefs()).resetCommandMap();
        }
        Map<String, String> aliasMap = readValue.orElse(new UserPrefs()).getCommandMap().getMap();
        if (aliasMap == null) {
            readValue.orElse(new UserPrefs()).resetCommandMap();
            return readValue;
        }
        Set<String> keySet = aliasMap.keySet().stream().collect(Collectors.toSet());
        for (String key : keySet) {
            if (key == null || key.isEmpty() || aliasMap.get(key) == null || aliasMap.get(key).isEmpty()) {
                logger.warning(MESSAGE_EMPTY_FIELD);
                aliasMap.remove(key);
            }
        }
        if (aliasMap.keySet().stream().anyMatch(CommandAliasMap.FULL_COMMANDS::contains)) {
            logger.warning(String.format(MESSAGE_SHORTHAND_IS_COMMAND));
            for (String command : CommandAliasMap.FULL_COMMANDS) {
                aliasMap.remove(command);
            }
        }
        if (!CommandAliasMap.FULL_COMMANDS.containsAll(aliasMap.values())) {
            logger.warning(MESSAGE_UNKNOWN_COMMAND);
            keySet = aliasMap.keySet().stream().collect(Collectors.toSet());
            for (String key : keySet) {
                if (!CommandAliasMap.FULL_COMMANDS.contains(aliasMap.get(key))) {
                    aliasMap.remove(key);
                }
            }
        }
        Set<String> valueSet = aliasMap.values().stream().collect(Collectors.toSet());
        if (valueSet.size() != aliasMap.size()) {
            throw new DataLoadingException(new Exception(MESSAGE_DUPLICATE_ALIAS));
        }
        return readValue;
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }

}
