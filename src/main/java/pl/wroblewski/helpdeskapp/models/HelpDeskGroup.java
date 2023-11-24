package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "HelpDeskGroup")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpDeskGroup {

    @Id
    @ManyToOne
    @JoinColumn(name = "helpdesk_id")
    private HelpDesk helpDeskId;

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupId;

    @Column(name = "position_name", length = 25)
    private String positionName;

    @Column(name = "isLeader")
    private Byte isLeader;
}
