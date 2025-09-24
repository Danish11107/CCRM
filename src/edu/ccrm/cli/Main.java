package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.service.DataStore;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.util.Validators;

import java.nio.file.Path;
import java.util.*;
import java.io.IOException;
import java.util.OptionalDouble;

/**
 * Expanded CLI for demo with enrollment, marks, transcripts and GPA.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DataStore store = DataStore.getInstance();
    private static final ImportExportService io = new ImportExportService();

    public static void main(String[] args) {
        System.out.println("CCRM Console — start");
        AppConfig cfg = AppConfig.getInstance();
        // seed sample data
        seed();
        // Demo snippets required by PDF
        edu.ccrm.util.ArrayUtilsDemo.demo();
        edu.ccrm.util.AssertDemo.demo();
        // anonymous inner class example
        Runnable r = new Runnable(){ public void run(){ System.out.println("Anonymous inner class running"); } };
        r.run();
        mainMenu();
        System.out.println("Exiting. Goodbye.");
    }

    private static void seed(){
        var s1 = new Student.Builder("S1").name("Alice Jones").email("alice@example.com").regNo("REG001").build();
        var s2 = new Student.Builder("S2").name("Bob Kumar").email("bob@example.com").regNo("REG002").build();
        store.addStudent(s1); store.addStudent(s2);
        var c1 = new Course.Builder("CS101").title("Intro to CS").credits(4).instructor("Dr. X").semester(Semester.FALL).build();
        var c2 = new Course.Builder("MA101").title("Calculus").credits(3).instructor("Dr. Y").semester(Semester.SPRING).build();
        store.addCourse(c1); store.addCourse(c2);
    }

    private static void mainMenu(){
        outer:
        while(true){
            System.out.println("\n=== CCRM MENU ===");
            System.out.println("1. List students  2. List courses  3. Add student 4. Add course 5. Enroll student 6. Record marks 7. Print transcript 8. Calculate GPA 9. Import students CSV  10. Export students  11. Backup exports  12. Search courses by instructor  0. Exit");
            System.out.print("Choose: ");
            String opt = scanner.nextLine().trim();
            switch(opt){
                case "1": listStudents(); break;
                case "2": listCourses(); break;
                case "3": addStudent(); break;
                case "4": addCourse(); break;
                case "5": enrollStudent(); break;
                case "6": recordMarks(); break;
                case "7": printTranscript(); break;
                case "8": calcGPA(); break;
                case "9": importStudents(); break;
                case "10": exportStudents(); break;
                case "11": backup(); break;
                case "12": searchCourses(); break;
                case "0": break outer;
                default: System.out.println("Unknown");
            }
        }
    }

    private static void listStudents(){
        store.listStudents().forEach(s->System.out.println(s));
    }

    private static void listCourses(){
        store.listCourses().forEach(c->System.out.println(c));
    }

    private static void addStudent(){
        System.out.print("New student id: ");
        String id = scanner.nextLine().trim();
        System.out.print("Full name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        if(!Validators.isEmail(email)){ System.out.println("Invalid email"); return; }
        System.out.print("RegNo: ");
        String reg = scanner.nextLine().trim();
        var s = new Student.Builder(id).name(name).email(email).regNo(reg).build();
        store.addStudent(s);
        System.out.println("Added student.");
    }

    private static void addCourse(){
        System.out.print("Course code: ");
        String code = scanner.nextLine().trim();
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Credits (int): ");
        int credits = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Instructor: ");
        String instr = scanner.nextLine().trim();
        var c = new Course.Builder(code).title(title).credits(credits).instructor(instr).build();
        store.addCourse(c);
        System.out.println("Added course.");
    }

    private static void enrollStudent(){
        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();
        System.out.print("Course code: ");
        String cc = scanner.nextLine().trim();
        try {
            store.enrollStudent(sid, cc);
            System.out.println("Enrolled.");
        } catch(Exception e){ System.out.println("Enroll failed: " + e.getMessage()); }
    }

    private static void recordMarks(){
        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();
        System.out.print("Course code: ");
        String cc = scanner.nextLine().trim();
        System.out.print("Marks (0-100): ");
        int m = Integer.parseInt(scanner.nextLine().trim());
        try {
            store.recordMarks(sid, cc, m);
            System.out.println("Recorded marks.");
        } catch(Exception e){ System.out.println("Record failed: " + e.getMessage()); }
    }

    private static void printTranscript(){
        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();
        var os = store.getStudent(sid);
        if(os.isEmpty()){ System.out.println("Unknown student"); return; }
        Student s = os.get();
        System.out.println("\n--- TRANSCRIPT for " + s.getFullName() + " ---");
        System.out.printf("%-8s %-30s %-7s %-7s %-7s\n","CODE","TITLE","CREDITS","MARKS","GRADE");
        s.getEnrollments().forEach(e->{
            String code = e.getCourse().getCode();
            String title = e.getCourse().getTitle();
            int credits = e.getCourse().getCredits();
            Integer marks = e.getMarks();
            Grade g = e.getGrade();
            System.out.printf("%-8s %-30s %-7d %-7s %-7s\n", code, title, credits, (marks==null?"-":marks), (g==null?"-":g));
        });
        OptionalDouble gpa = s.calculateGPA();
        System.out.println("GPA: " + (gpa.isPresent()?String.format("%.3f", gpa.getAsDouble()):"N/A"));
    }

    private static void calcGPA(){
        System.out.print("Student id: ");
        String sid = scanner.nextLine().trim();
        var os = store.getStudent(sid);
        if(os.isEmpty()){ System.out.println("Unknown student"); return; }
        Student s = os.get();
        OptionalDouble gpa = s.calculateGPA();
        if(gpa.isPresent()) System.out.println("GPA: " + String.format("%.3f", gpa.getAsDouble()));
        else System.out.println("No graded enrollments yet.");
    }

    private static void importStudents(){
        System.out.print("Path to CSV: ");
        String p = scanner.nextLine().trim();
        try {
            io.importStudents(Path.of(p));
            System.out.println("Imported.");
        } catch(IOException e){ System.out.println("Import failed: " + e.getMessage()); }
    }

    private static void exportStudents(){
        try {
            io.exportStudents();
            System.out.println("Exported to data dir: " + AppConfig.getInstance().getDataDir());
        } catch(IOException e){ System.out.println("Export failed: " + e.getMessage()); }
    }

    private static void backup(){
        try {
            String tag = AppConfig.getInstance().timestamp();
            var p = io.backupExports(tag);
            long size = io.folderSizeRecursive(p);
            System.out.println("Backup created at: " + p + " (size=" + size + " bytes)");
        } catch(IOException e){ System.out.println("Backup failed: " + e.getMessage()); }
    }

    private static void searchCourses(){
        System.out.print("Instructor name: ");
        String instr = scanner.nextLine().trim();
        var list = store.searchByInstructor(instr);
        if(list.isEmpty()) System.out.println("No courses");
        else list.forEach(System.out::println);
    }
}
