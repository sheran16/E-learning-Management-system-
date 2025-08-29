package model;

public abstract class AbstractCourse {
    protected String courseId;          
    protected String courseName;
    protected String instructorName;
    protected String instructorEmail;
    protected String description;

    public AbstractCourse(String courseId, String courseName, String instructorName, String instructorEmail, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.description = description;
    }

    public abstract void printCourseDetails(); //abstract method
}
