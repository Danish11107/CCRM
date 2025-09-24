package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.DataStore;
import edu.ccrm.config.AppConfig;
import java.nio.file.*;
import java.io.*;
import java.util.stream.*;
import java.util.*;

/**
 * Basic CSV import/export using NIO.2 and Streams.
 */
public class ImportExportService {

    private final Path base = AppConfig.getInstance().getDataDir();

    public ImportExportService(){
        try { Files.createDirectories(base); } catch(IOException e){ throw new RuntimeException(e); }
    }

    public void exportStudents() throws IOException {
        Path f = base.resolve("students_export.csv");
        try (BufferedWriter bw = Files.newBufferedWriter(f)) {
            bw.write("id,regNo,fullName,email,status");
            bw.newLine();
            for(var s : DataStore.getInstance().listStudents()){
                bw.write(String.join(",", s.getId(), s.getRegNo(), s.getFullName(), s.getEmail(), s.getStatus().name()));
                bw.newLine();
            }
        }
    }

    public void importStudents(Path csv) throws IOException {
        try (Stream<String> lines = Files.lines(csv)) {
            lines.skip(1).forEach(l -> {
                String[] parts = l.split(",", -1);
                if(parts.length>=5){
                    var s = new Student.Builder(parts[0]).name(parts[2]).email(parts[3]).regNo(parts[1]).build();
                    DataStore.getInstance().addStudent(s);
                }
            });
        }
    }

    public Path backupExports(String tag) throws IOException {
        Path target = base.resolve("backup_" + tag);
        Files.createDirectories(target);
        try (var stream = Files.list(base)) {
            stream.filter(p->p.getFileName().toString().endsWith(".csv"))
                  .forEach(p->{
                      try { Files.copy(p, target.resolve(p.getFileName()), StandardCopyOption.REPLACE_EXISTING); }
                      catch(IOException e){ throw new UncheckedIOException(e); }
                  });
        }
        return target;
    }

    // Recursive utility: compute total size
    public long folderSizeRecursive(Path p) throws IOException {
        try (var walk = Files.walk(p)) {
            return walk.filter(Files::isRegularFile).mapToLong(pp->{
                try { return Files.size(pp); } catch(IOException e){ return 0L; }
            }).sum();
        }
    }
}
