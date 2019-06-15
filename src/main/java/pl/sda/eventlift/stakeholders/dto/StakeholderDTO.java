package pl.sda.eventlift.stakeholders.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StakeholderDTO {

    private Long id;
    @Size(min = 3, max = 15, message = "imie powinno mieć od 3 do 15 znaków")
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String password;
}
