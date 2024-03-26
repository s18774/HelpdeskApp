package pl.wroblewski.helpdeskapp.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GroupDto {
    private Integer groupId;

    private String groupName;
}
