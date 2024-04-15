package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserTicketId implements Serializable {
    @Column(name="user_id")
    private Integer userId;

    @Column(name="ticket_id")
    private Integer ticketId;
}
