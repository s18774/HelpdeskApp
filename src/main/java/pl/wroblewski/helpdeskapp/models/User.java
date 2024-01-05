package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    //nie zrobiłem tego supervisora bo troche idk jak to zaimplementować
    //generalnie user może być również przełożonym
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department departmentId;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "second_name", length = 20)
    private String secondName;

    @Column(name = "phone_number", length = 12)
    private String phoneNumber;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "employment_date")
    private LocalDate employmentDate;

    @Column(name = "login", length = 20)
    private String login;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "position_name", length = 25)
    private String positionName;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "room")
    private Integer room;

    @ManyToOne
    private User supervisor;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
