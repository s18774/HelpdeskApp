package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "_Group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private Integer groupId;

    @Column(name = "group_name", length = 25, nullable = false)
    private String groupName;

    @Column(name = "is_group_active", nullable = false)
    private Byte isGroupActive;//1 yes, 0 nope
}
