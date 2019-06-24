package pl.sda.eventlift.information.dto;

import lombok.*;
import pl.sda.eventlift.events.dto.EventSimpleDataDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportInfoDTO {

    private String startLocation;
    private LocalDateTime startTime;
    private Integer numberOfSeats;
    private String additionalInformation;
    private EventSimpleDataDTO event;
}
