package controller;

import model.Course;
import model.CourseService;
import model.CourseServiceImpl;

import java.util.List;

public class CourseController {
    private final CourseService courseService;

    public CourseController() {
        this.courseService = new CourseServiceImpl(); 
    }

    public void addCourse(Course course) {
        courseService.addCourse(course);
    }

    public void updateCourse(Course course) {
        courseService.updateCourse(course);
    }

    public void deleteCourseById(String courseId) {
        courseService.deleteCourseById(courseId);
    }

    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
}
