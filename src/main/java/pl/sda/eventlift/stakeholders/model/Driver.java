package pl.sda.eventlift.stakeholders.model;

import lombok.*;
import pl.sda.eventlift.configs.BaseEntity;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.information.model.TransportInfo;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drivers")
public class Driver extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    @OneToOne
    @JoinColumn(name = "stakeholder_id")
    private Stakeholder stakeholder;
    @ManyToMany
    @JoinTable(name = "drivers_events",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events;
    @OneToMany(mappedBy = "driver")
    private Set<TransportInfo> information;
    @ManyToMany(mappedBy = "drivers")
    Set<Hitchhiker> hitchhikers;

}
