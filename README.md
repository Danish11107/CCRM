# Campus Course & Records Manager (CCRM)

## Project overview
CCRM is a Java SE console application that demonstrates common OOP and Java concepts while managing students, courses, enrollments, marks and transcripts. The project includes:
- Domain model: `Person`, `Student`, `Instructor`, `Course`, `Enrollment`, `Grade`, `Semester`.
- Services: in-memory `DataStore` (Singleton), `ImportExportService` for CSV operations.
- CLI: `edu.ccrm.cli.Main` — runnable main class (menu-driven).
- Utilities: `ReportService`, `ArrayUtilsDemo`, `AssertDemo`.
- Exceptions: `DuplicateEnrollmentException`, `MaxCreditLimitExceededException`.
- Build: `pom.xml` (Maven). License: MIT.


# Project Structure

```
CCRM-Java-Project
edu.ccrm
├─ cli/
│  └─ Main.java                 // Console menu, input loop
│
├─ domain/
│  ├─ Person.java               // Abstract base class
│  ├─ Student.java              // Student entity + Builder
│  ├─ Instructor.java           // Instructor entity
│  ├─ Course.java               // Course entity
│  ├─ Enrollment.java           // Enrollment record
│  ├─ Grade.java                // Enum with constructor & points field
│  ├─ Semester.java             // Enum with constructor & fields (code, label)
│  └─ (immutable value objects if any)
│
├─ service/
│  ├─ StudentService.java       // Interface
│  ├─ StudentServiceImpl.java   // Implementation
│  ├─ CourseService.java
│  ├─ CourseServiceImpl.java
│  ├─ EnrollmentService.java
│  ├─ EnrollmentServiceImpl.java
│  ├─ TranscriptService.java
│  └─ TranscriptServiceImpl.java
│
├─ io/
│  ├─ ImportExportService.java  // CSV import/export (NIO.2)
│  ├─ BackupService.java        // File backup service
│  └─ CsvParser.java            // Simple CSV parsing helper
│
├─ util/
│  ├─ Validators.java           // Input validation
│  ├─ Comparators.java          // Common comparators (lambdas)
│  ├─ RecursionUtils.java       // Recursive utilities
│  ├─ ReportService.java        // GPA distribution reports (streams)
│  ├─ ArrayUtilsDemo.java       // Arrays & Strings demo
│  └─ AssertDemo.java           // Assertions demo
│
├─ config/
│  └─ AppConfig.java            // Singleton config holder
│
├─ docs/
│  └─ (pdf_extracted, etc.)
│
├─ screenshots/
│  ├─ jdk_version.png
│  ├─ eclipse_setup.png
│  ├─ program_running.png
│  └─ exports_backups.png
│
├─ sample_data/
│  └─ students.csv              // Example CSV input
│
├─ README.md
├─ USAGE.md
└─ LICENSE
 
 ```


## How to run
- JDK: **Java 17** (recommended). Ensure `javac` and `java` are on PATH.

Compile & run (no Maven):
```bash
javac -d out $(find src -name '*.java')
java -cp out edu.ccrm.cli.Main
```

Build & run with Maven:
```bash
mvn package
java -cp target/classes edu.ccrm.cli.Main
```

## Evolution of Java (short bullets)
- Java 1.0 (1996): Initial release — OOP on JVM.
- Java 5 (2004): Generics, annotations, enhanced for-loop.
- Java 8 (2014): Lambdas, Streams, default methods.
- Java 9+: Module system; later releases: performance, garbage collection improvements.
- Java 17 (LTS): Current long-term support release with many features and stability.

## Java ME vs SE vs EE
- **Java ME (Micro Edition)**: For constrained devices (embedded, IoT); smaller footprint.
- **Java SE (Standard Edition)**: Core Java APIs for desktop and server-side apps; the target of this project.
- **Java EE / Jakarta EE (Enterprise Edition)**: Extension of SE for enterprise apps — servlets, EJBs, JMS, etc.

## JDK / JRE / JVM explanation
- **JDK**: Java Development Kit — includes `javac`, tools, and a JRE.
- **JRE**: Java Runtime Environment — contains the JVM and standard libraries; needed to run Java apps.
- **JVM**: Java Virtual Machine — executes bytecode, provides garbage collection and platform abstraction.

## Windows install steps (with screenshots)
1. Download JDK 17 from AdoptOpenJDK / Eclipse Temurin or Oracle.
2. Run installer and follow prompts.
3. Add `C:\Program Files\Java\jdk-17\bin` to `PATH` environment variable.
4. Verify in Command Prompt:
```
> java -version
```
(see `screenshots/jdk_version.png`)

## Eclipse setup steps (with screenshots)
1. Install Eclipse IDE (Java Developers).
2. File → Import → Existing Maven Projects → select project root (`CCRM_project`).
3. Right-click project → Run As → Java Application → choose `edu.ccrm.cli.Main`.
(see `screenshots/eclipse_setup.png`)

## Mapping table: syllabus topic → file/class/method
| Topic | File / Class / Method |
|---|---|
| Abstraction (abstract class) | `src/edu/ccrm/domain/Person.java` |
| Inheritance | `src/edu/ccrm/domain/Instructor.java` extends `Person` |
| Builder pattern | `src/edu/ccrm/domain/Student.java` (inner `Builder`) |
| Singleton | `src/edu/ccrm/config/AppConfig.java`, `src/edu/ccrm/service/DataStore.java` |
| Exceptions (custom) | `src/edu/ccrm/service/DuplicateEnrollmentException.java` |
| Streams & Lambdas | `src/edu/ccrm/util/ReportService.java` |
| CSV I/O (NIO.2) | `src/edu/ccrm/io/ImportExportService.java` |
| Assertions | `src/edu/ccrm/util/AssertDemo.java` |
| Arrays & Strings | `src/edu/ccrm/util/ArrayUtilsDemo.java` |
| GPA Calculation | `src/edu/ccrm/domain/Student.java` (calculateGPA) |
| CLI & Menus | `src/edu/ccrm/cli/Main.java` |
| Overloading / Polymorphism | `src/edu/ccrm/service/DataStore.java` (enrollStudent overload) |

## Enabling assertions
To enable Java assertions at runtime, use the `-ea` flag:
```bash
java -ea -cp out edu.ccrm.cli.Main
```
Assertions are used in `src/edu/ccrm/util/AssertDemo.java`.

## Screenshots
See the `screenshots/` folder:
- `jdk_version.png` — `java -version` output (placeholder).
- `eclipse_setup.png` — Eclipse import/run (placeholder).
- `program_running.png` — Sample program run (placeholder).
- `exports_backups.png` — Export & backup folder layout (placeholder).

---

## USAGE (short)
Place sample CSV files under `sample_data/`. Example `sample_data/students.csv`:
```
id,regNo,fullName,email,status
S3,REG003,Charlie Singh,charlie@example.com,ACTIVE
```

Sample commands:
- Import students:
```bash
# from project root (after compilation)
java -cp out edu.ccrm.cli.Main
# from CLI menu choose: 9 (Import students CSV) and provide path: sample_data/students.csv
```

- Export students (menu option 10) will create `students_export.csv` in your OS user home `ccrm_data` folder.

---

MIT License

Copyright (c) 2025

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

