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
    @EmbeddedId
    private HelpDeskGroupId id;

    @MapsId("helpdeskId")
    @ManyToOne
    @JoinColumn(name = "helpdesk_id")
    private HelpDesk helpDesk;

    @MapsId("groupId")
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "position_name", length = 25)
    private String positionName;

    @Column(name = "isLeader")
    private Byte isLeader;
}
