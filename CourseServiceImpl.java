package model;

import model.Course;
import model.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CourseServiceImpl implements CourseService {

    @Override
    public void addCourse(Course course) {
        String sql = "INSERT INTO courses (id, name, instructor, email, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getCourseId());
            stmt.setString(2, course.getCourseName());
            stmt.setString(3, course.getInstructorName());
            stmt.setString(4, course.getInstructorEmail());
            stmt.setString(5, course.getDescription());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCourse(Course course) {
        String sql = "UPDATE courses SET name=?, instructor=?, email=?, description=? WHERE id=?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getInstructorName());
            stmt.setString(3, course.getInstructorEmail());
            stmt.setString(4, course.getDescription());
            stmt.setString(5, course.getCourseId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCourse(String courseId) {
        String sql = "DELETE FROM courses WHERE id=?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCourseById(String courseId) {
        deleteCourse(courseId); 
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Course c = new Course(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("instructor"),
                        rs.getString("email"),
                        rs.getString("description")
                );
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
