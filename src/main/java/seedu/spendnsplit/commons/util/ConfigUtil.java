package seedu.spendnsplit.commons.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.spendnsplit.commons.core.Config;
import seedu.spendnsplit.commons.exceptions.DataLoadingException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    /**
     * Reads the config file in the given path.
     */
    public static Optional<Config> readConfig(Path configFilePath) throws DataLoadingException {
        Optional<Config> readVal = JsonUtil.readJsonFile(configFilePath, Config.class);
        if (readVal.isPresent()) {
            readVal.get().setUserPrefsFilePath();
        }
        return readVal;
    }

    /**
     * Saves the configs to the given path.
     */
    public static void saveConfig(Config config, Path configFilePath) throws IOException {
        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
