package pl.wroblewski.helpdeskapp.dto.group;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GroupCreateDto {
    @NotBlank
    private String groupName;
}
