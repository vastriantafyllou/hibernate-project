package gr.aueb.cf.schoolapp.model;

import gr.aueb.cf.schoolapp.enums.GenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "teacher_more_info")
public class TeacherMoreInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @OneToOne(mappedBy = "teacherMoreInfo", fetch = FetchType.LAZY)
    private Teacher teacher;

}
