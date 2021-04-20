package CurriculumMonitoring;

/**Reference class:Template for a course*/
public class Course implements Comparable<Course>{
    private String number; // course number attribute
    private String title; // descriptive title attribute
    private double units; // credit units attribute
    private int yearLevel; // year level attribute: 1= first year, 2= second year, ...
    private int term; //term attribute: 1= first sem, 2=second sem, 3=short term
    private int grade; // grade earned attribute
    /**Default Constructor
     *Constructs a course with the empty string as the course number,
     *empty string as the course title, 0 as the units, 0 as the year level,
     *0 as the term, 0 as the grade
     **/
    public Course(){
        number="";
        title="";
        units= 0;
        yearLevel= 0;
        term= 0;
        grade=0;
    }
    /**given string t as the course title, given u as the units,
     *given y as the year level, given e the term,
     *given g as the grade
     **/
    public Course(String course, String title, double units, int yearLevel, int term, int grade){
        this.number=course;
        this.title=title;
        this.units=units;
        this.yearLevel= yearLevel;
        this.term= term;
        this.grade=grade;
    }
    /***Sets given string n as the value of the number field**/
    public void setNumber(String number){
        this.number = number;
    }
    /***Sets given string t as the value of the title field**/
    public void setTitle(String title){
        this.title = title;
    }
    /**Sets given integer u as the value of the units field*/
    public void setUnits(double units){
        this.units = units;
    }
    /**Sets given integer y as the value of the yearLevelfield*/
    public void setYearLevel(int yearLevel){
        this.yearLevel= yearLevel;
    }
    /**Sets given integer t as the value of the term field*/
    public void setTerm(int term){
        this.term =term;
    }
    /**Sets given float g as the value of the grade field*/
    public void setGrade(int grade){
        this.grade = grade;
    }
    /***Returns the value of the number field**/
    public String getNumber(){
        return number;
    }
    /***Returns the value of the title field**/
    public String getTitle(){
        return title;
    }
    /***Returns the value of the units field**/
    public double getUnits(){
        return units;
    }
    /***Returns the value of the year level field**/
    public int getYearLevel(){
        return yearLevel;
    }
    /***Returns the value of the terms field**/
    public int getTerm(){
        return term;
    }
    /***Returns the value of the grade field**/
    public int getGrade(){
        return grade;
    }/***Returns a string describing a course**/
    public String toString(){
        String result="";
        result = yearLevel+"/"+term+"/"+number+"/"+title+"/"+units+"/"+grade;
        return result;
    }
    /**Returns 0 if this course has the same string representation as another course as formulated
     * by the toStringmethod. Returns -1 if this course should be before another course if the courses
     * are compared based on the string representation of the course objects as defined by the toStringmethod.
     * Returns 1 if this course should be after another course if the course are compared based
     * on the string representation of the course objects as defined by the toStringmethod. */
    public int compareTo(Course another){
        if (this.toString().equals(another.toString()))
            return 0;
        else if (this.toString().compareTo(another.toString()) < 0)
            return -1;
        else
            return 1;
    }
} // end of class Course


