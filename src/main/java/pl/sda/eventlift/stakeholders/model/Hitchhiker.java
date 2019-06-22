package pl.sda.eventlift.stakeholders.model;

import lombok.*;
import pl.sda.eventlift.configs.BaseEntity;
import pl.sda.eventlift.events.model.Event;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hitchhikers")
public class Hitchhiker extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    @OneToOne
    @JoinColumn(name = "stakeholder_id")
    private Stakeholder stakeholder;
    @ManyToMany
    @JoinTable(name = "hitchhikers_events",
            joinColumns = @JoinColumn(name = "hitchhiker_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "drivers_hitchhikers",
            joinColumns = @JoinColumn(name = "hitchhiker_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id")
    )
    private Set<Driver> drivers;
}
