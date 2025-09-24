
import edu.ccrm.service.DataStore;
import edu.ccrm.domain.*;
import java.util.OptionalDouble;

public class TestRunner {
    public static void main(String[] args) {
        int failures = 0;
        try {
            DataStore store = DataStore.getInstance();
            // Create student and course
            Student s = new Student.Builder("T1").name("Test User").email("test@example.com").regNo("REGT1").build();
            store.addStudent(s);
            Course c = new Course.Builder("TST101").title("Test Course").credits(3).instructor("Dr Test").build();
            store.addCourse(c);

            // Enroll and check enrollment exists
            store.enrollStudent("T1", "TST101");
            boolean enrolled = s.getEnrollments().stream().anyMatch(e -> e.getCourse().getCode().equals("TST101"));
            if(!enrolled) { System.out.println("[FAIL] Enrollment missing"); failures++; } else System.out.println("[PASS] Enrollment created");

            // Record marks and check grade computed
            store.recordMarks("T1", "TST101", 85);
            Enrollment e = s.getEnrollments().stream().filter(en->en.getCourse().getCode().equals("TST101")).findFirst().get();
            if(e.getMarks() == null || e.getGrade() == null) { System.out.println("[FAIL] Marks/Grade not set"); failures++; } else System.out.println("[PASS] Marks and grade set: " + e.getGrade());

            // GPA calculation
            OptionalDouble gpa = s.calculateGPA();
            if(!gpa.isPresent()) { System.out.println("[FAIL] GPA not present"); failures++; } else System.out.println("[PASS] GPA computed: " + String.format("%.3f", gpa.getAsDouble()));

            // Duplicate enrollment should throw
            boolean dupThrown = false;
            try {
                store.enrollStudent("T1", "TST101");
            } catch(Exception ex) { dupThrown = true; System.out.println("[PASS] Duplicate enrollment prevented: " + ex.getMessage()); }
            if(!dupThrown) { System.out.println("[FAIL] Duplicate enrollment allowed"); failures++; }

            // Max credits enforcement: create many courses to exceed MAX_CREDITS
            for(int i=0;i<10;i++){
                Course cc = new Course.Builder("C"+i).title("C"+i).credits(10).instructor("X").build();
                store.addCourse(cc);
            }
            boolean maxThrown = false;
            try {
                store.enrollStudent("T1", "C0");
            } catch(Exception ex){ maxThrown = true; System.out.println("[PASS] Max credit prevented: " + ex.getMessage()); }
            if(!maxThrown) { System.out.println("[FAIL] Max credit not enforced"); failures++; }

        } catch(Exception ex) {
            System.out.println("[ERROR] Test exception: " + ex.getMessage());
            ex.printStackTrace();
            failures++;
        }
        if(failures==0) System.out.println("\nALL TESTS PASSED");
        else System.out.println("\nTESTS FAILED: " + failures);
        System.exit(failures==0?0:1);
    }
}
