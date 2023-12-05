package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserApplicationId implements Serializable {
    @Column(name="user_id")
    private Integer userId;

    @Column(name="application_id")
    private Integer applicationId;
}
