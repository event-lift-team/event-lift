package pl.sda.eventlift.information.model;

import lombok.*;
import pl.sda.eventlift.configs.BaseEntity;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.stakeholders.model.Driver;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transport_info")
public class TransportInfo extends BaseEntity {

    private String startLocation;
    private LocalDateTime startTime;
    private Integer numberOfSeats;
    private String additionalInformation;
    @OneToOne()
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public LocalDate getStartLocalDate(){
        return startTime.toLocalDate();
    }

    public LocalTime getStartLocalTime(){
        return startTime.toLocalTime();
    }
}
