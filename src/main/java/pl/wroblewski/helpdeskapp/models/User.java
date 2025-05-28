package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;
import pl.wroblewski.helpdeskapp.converters.LocalDateStringConverter;

import java.time.LocalDate;

@Entity(name = "_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department departmentId;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "second_name", length = 20, nullable = false)
    private String secondName;

    @Column(name = "phone_number", length = 12)
    private String phoneNumber;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "employment_date", nullable = false)
    @Convert(converter = LocalDateStringConverter.class)
    private LocalDate employmentDate;

    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "position_name", length = 25, nullable = false)
    private String positionName;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "room")
    private Integer room;

    @ManyToOne
    private User supervisor;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "exp_id", nullable = false)
    private ExperienceLevel experienceLevel;

    public String getFullName() {
        return firstName + " " + secondName;
    }
}
