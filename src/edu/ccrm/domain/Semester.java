package edu.ccrm.domain;

/**
 * Semester enum with constructor and fields (code and label).
 */
public enum Semester {
    SPRING(1, "Spring"),
    SUMMER(2, "Summer"),
    FALL(3, "Fall");

    private final int code;
    private final String label;

    Semester(int code, String label){
        this.code = code;
        this.label = label;
    }

    public int getCode(){ return code; }
    public String getLabel(){ return label; }

    @Override
    public String toString(){
        return label + " (code=" + code + ")";
    }
}
