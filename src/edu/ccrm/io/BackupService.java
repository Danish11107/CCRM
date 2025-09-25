package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BackupService creates timestamped backups of export files.
 */
public class BackupService {
    public static void backupFile(Path source, Path backupDir) throws IOException {
        if (!Files.exists(backupDir)) Files.createDirectories(backupDir);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path target = backupDir.resolve(source.getFileName().toString().replace(".csv","_"+timestamp+".csv"));
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
