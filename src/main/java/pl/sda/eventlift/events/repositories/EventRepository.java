package pl.sda.eventlift.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.stakeholders.model.Stakeholder;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByUuid(String uuid);

    boolean existsByUuid(String uuid);
}
