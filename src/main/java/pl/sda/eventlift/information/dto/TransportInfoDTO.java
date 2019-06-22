package pl.sda.eventlift.information.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportInfoDTO {

    private String startLocation;
    private LocalDate startDate;
    private LocalTime startTime;
    private Integer numberOfSeats;
    private String additionalInformation;
}
