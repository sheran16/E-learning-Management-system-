package model;

import java.util.List;

public interface CourseService {
    void addCourse(Course course);
    void updateCourse(Course course);
    void deleteCourseById(String courseId);
    List<Course> getAllCourses();
    void deleteCourse(String courseId); 
}
