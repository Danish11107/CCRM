package edu.ccrm.service;

public class TranscriptServiceImpl implements TranscriptService {
    private final DataStore store = DataStore.getInstance();
    @Override public void printTranscript(String studentId){ 
        // delegate to DataStore helper if exists, otherwise replicate logic
        try {
            // try calling method if exists
            java.lang.reflect.Method m = store.getClass().getMethod("printTranscript", String.class);
            m.invoke(store, studentId);
        } catch(Exception e){
            // fallback simple print
            var os = store.getStudent(studentId);
            if(os.isEmpty()){ System.out.println("Unknown student"); return; }
            var s = os.get();
            System.out.println("\n--- TRANSCRIPT for " + s.getFullName() + " ---");
            System.out.printf("%-8s %-30s %-7s %-7s %-7s\n","CODE","TITLE","CREDITS","MARKS","GRADE");
            s.getEnrollments().forEach(e->{
                String code = e.getCourse().getCode();
                String title = e.getCourse().getTitle();
                int credits = e.getCourse().getCredits();
                Integer marks = e.getMarks();
                var g = e.getGrade();
                System.out.printf("%-8s %-30s %-7d %-7s %-7s\n", code, title, credits, (marks==null?"-":marks), (g==null?"-":g));
            });
        }
    }
}
