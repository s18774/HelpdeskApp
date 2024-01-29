package pl.wroblewski.helpdeskapp.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SLADto {
    private Integer slaId;
    private Short slaLevel;
}
