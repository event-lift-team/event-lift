package pl.sda.eventlift.events.model;

import lombok.*;
import pl.sda.eventlift.configs.BaseEntity;
import pl.sda.eventlift.stakeholders.model.Stakeholder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Events")
public class Event extends BaseEntity {

    private String name;
    private String uuid;
    @ManyToMany(mappedBy = "events")
    private Set<Stakeholder> stakeholders;
}
