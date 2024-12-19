package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserApplicationId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "application_id")
    private Integer applicationId;
}
