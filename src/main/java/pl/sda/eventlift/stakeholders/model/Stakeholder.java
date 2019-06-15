package pl.sda.eventlift.stakeholders.model;

import lombok.*;
import pl.sda.eventlift.configs.BaseEntity;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.roles.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Stakeholders")
public class Stakeholder extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "stakeholders_events",
            joinColumns = @JoinColumn(name = "stakeholder_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "stakeholders_roles",
            joinColumns = @JoinColumn(name = "stakeholder_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
