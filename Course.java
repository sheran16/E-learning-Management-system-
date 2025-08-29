package model;

public class Course extends AbstractCourse {

    public Course(String courseId, String courseName, String instructorName, String instructorEmail, String description) {
        super(courseId, courseName, instructorName, instructorEmail, description);
    }

    @Override
    public void printCourseDetails() {
        System.out.println(courseId + " - " + courseName + " by " + instructorName + " (" + instructorEmail + ")");
    }

    public String getCourseId()
    { return courseId; }
    public String getCourseName()
    { return courseName; }
    public String getInstructorName()
    { return instructorName; }
    public String getInstructorEmail()
    { return instructorEmail; }
    public String getDescription()
    { return description; }

    
    public void setCourseId(String courseId)
    { this.courseId = courseId; }
    public void setCourseName(String courseName)
    { this.courseName = courseName; }
    public void setInstructorName(String instructorName)
    { this.instructorName = instructorName; }
    public void setInstructorEmail(String instructorEmail)
    { this.instructorEmail = instructorEmail; }
    public void setDescription(String description)
    { this.description = description; }
}
