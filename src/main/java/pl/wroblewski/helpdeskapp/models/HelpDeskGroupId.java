package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class HelpDeskGroupId implements Serializable {
    @Column(name="helpdesk_id")
    private Integer helpdeskId;

    @Column(name="group_id")
    private Integer groupId;
}
