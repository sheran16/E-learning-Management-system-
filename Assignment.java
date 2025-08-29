package model;

import java.time.LocalDateTime;

public class Assignment {
    private int id;
    private String courseId;
    private String courseName;
    private String title;
    private String description;
    private String dueDate;
    private LocalDateTime addDate;
    private LocalDateTime updateDate;
    private LocalDateTime deleteDate;
    private boolean isDeleted;

    public Assignment() {}

    public Assignment(String courseId, String courseName, String title, 
                    String description, String dueDate) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public LocalDateTime getAddDate() { return addDate; }
    public void setAddDate(LocalDateTime addDate) { this.addDate = addDate; }
    public LocalDateTime getUpdateDate() { return updateDate; }
    public void setUpdateDate(LocalDateTime updateDate) { this.updateDate = updateDate; }
    public LocalDateTime getDeleteDate() { return deleteDate; }
    public void setDeleteDate(LocalDateTime deleteDate) { this.deleteDate = deleteDate; }
    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }
}