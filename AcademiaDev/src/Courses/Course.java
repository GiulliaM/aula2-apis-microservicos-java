package Courses;

import java.time.Duration;

public class Course {
    private String title;//deve ser um atributo unico
    private String description;
    private String instructorName;
    private Duration duration;//Duration.ofHours(5).plusMinutes(30); // 5 horas e 30 min
    private DifficultyLevel difficultyLevel; // Alterado para Enum
    private CourseStatus status; // Alterado para Enum

    public Course(String title, String description, String instructorName, Duration duration, DifficultyLevel difficultyLevel, CourseStatus status) {
        this.title = title;
        this.description = description;
        this.instructorName = instructorName;
        this.duration = duration;
        this.difficultyLevel = difficultyLevel;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }
}
