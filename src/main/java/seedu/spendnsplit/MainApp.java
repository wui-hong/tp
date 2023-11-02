package seedu.spendnsplit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.spendnsplit.commons.core.Config;
import seedu.spendnsplit.commons.core.LogsCenter;
import seedu.spendnsplit.commons.core.Version;
import seedu.spendnsplit.commons.exceptions.DataLoadingException;
import seedu.spendnsplit.commons.util.ConfigUtil;
import seedu.spendnsplit.commons.util.StringUtil;
import seedu.spendnsplit.logic.Logic;
import seedu.spendnsplit.logic.LogicManager;
import seedu.spendnsplit.model.SpendNSplit;
import seedu.spendnsplit.model.Model;
import seedu.spendnsplit.model.ModelManager;
import seedu.spendnsplit.model.ReadOnlySpendNSplitBook;
import seedu.spendnsplit.model.ReadOnlyUserPrefs;
import seedu.spendnsplit.model.UserPrefs;
import seedu.spendnsplit.model.util.SampleDataUtil;
import seedu.spendnsplit.storage.JsonSpendNSplitBookStorage;
import seedu.spendnsplit.storage.SpendNSplitBookStorage;
import seedu.spendnsplit.storage.JsonUserPrefsStorage;
import seedu.spendnsplit.storage.Storage;
import seedu.spendnsplit.storage.StorageManager;
import seedu.spendnsplit.storage.UserPrefsStorage;
import seedu.spendnsplit.ui.Ui;
import seedu.spendnsplit.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initialising Spend n Split ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        SpendNSplitBookStorage spendNSplitBookStorage = new JsonSpendNSplitBookStorage(userPrefs.getSpendNSplitBookFilePath());
        storage = new StorageManager(spendNSplitBookStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s spendNSplit book and {@code userPrefs}. <br>
     * The data from the sample spendNSplit book will be used instead if {@code storage}'s spendNSplit book is not found,
     * or an empty spendNSplit book will be used instead if errors occur when reading {@code storage}'s spendNSplit book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getSpendNSplitBookFilePath());

        Optional<ReadOnlySpendNSplitBook> spendNSplitBookOptional;
        ReadOnlySpendNSplitBook initialData;
        try {
            spendNSplitBookOptional = storage.readSpendNSplitBook();
            if (!spendNSplitBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getSpendNSplitBookFilePath()
                        + " populated with a sample SpendNSplitBook.");
            }
            initialData = spendNSplitBookOptional.orElseGet(SampleDataUtil::getSampleSpendNSplitBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getSpendNSplitBookFilePath() + " could not be loaded."
                    + " Will be starting with an empty SpendNSplitBook.");
            initialData = new SpendNSplit();
        }
        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting SpendNSplitBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Spend n Split ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
