package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Singleton AppConfig demonstrating Singleton pattern.
 */
public final class AppConfig {
    private static AppConfig instance;
    private final Path dataDir;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private AppConfig() {
        this.dataDir = Paths.get(System.getProperty("user.home"), "ccrm_data");
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

    public Path getDataDir() {
        return dataDir;
    }

    public String timestamp() {
        return LocalDateTime.now().format(dtf);
    }
}
