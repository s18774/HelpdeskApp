package pl.wroblewski.helpdeskapp.dto.group;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GroupUpdateDto {
    private Integer groupId;
    private String groupName;
    private Byte isGroupActive;
}
