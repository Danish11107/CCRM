# Usage & Sample Commands

## Compile (no Maven)
javac -d out $(find src -name '*.java')

## Run
java -cp out edu.ccrm.cli.Main

## Import sample students
# In program choose: 9 (Import students CSV)
# Provide path: sample_data/students.csv

## Export students
# In program choose: 10 (Export students). Exports to: ~/ccrm_data/students_export.csv

## Enroll student (example)
# Menu: 5
# Student id: S1
# Course code: CS101

