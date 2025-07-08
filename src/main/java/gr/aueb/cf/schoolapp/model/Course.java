package gr.aueb.cf.schoolapp.model;

import gr.aueb.cf.schoolapp.enums.LessonType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(length = 2048)
    private String comments;

    @Column(name = "lesson_type")
    @Enumerated(EnumType.ORDINAL)       // default value
    private LessonType lessonType;

//    @JoinTable(name = "courses_teachers",
//            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"))    /// για περισσότερο flexibility


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "courses_teachers")   // courses_id, teachers_id αυτόματα τα δημιουργεί αν βρει instance id.
    @Getter(AccessLevel.PROTECTED)
    private Set<Teacher> teachers = new HashSet<>();

    public Set<Teacher> getAllTeachers() {
        return Collections.unmodifiableSet(teachers);
    }

    public void addTeacher(Teacher teacher) {
        if (teachers == null) teachers = new HashSet<>();
        teachers.add(teacher);
        teacher.getCourses().add(this);
    }

    public void removeTeacher(Teacher teacher) {
        if (teachers == null) return;
        teachers.remove(teacher);
        teacher.getCourses().remove(this);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", comments='" + comments + '\'' +
                ", lessonType=" + lessonType +
                '}';
    }
}
