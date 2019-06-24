package pl.sda.eventlift.stakeholders.dto;

import lombok.Data;
import pl.sda.eventlift.information.dto.TransportInfoDTO;

import java.util.Set;

@Data
public class DriverDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<TransportInfoDTO> information;
}
