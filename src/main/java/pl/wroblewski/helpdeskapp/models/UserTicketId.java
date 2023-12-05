package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserTicketId implements Serializable {
    @Column(name="user_id")
    private Integer userId;

    @Column(name="ticket_id")
    private Integer ticketId;
}
