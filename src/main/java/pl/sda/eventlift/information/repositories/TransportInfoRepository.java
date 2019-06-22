package pl.sda.eventlift.information.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.information.model.TransportInfo;
import pl.sda.eventlift.stakeholders.model.Driver;

public interface TransportInfoRepository extends JpaRepository<TransportInfo, Long> {

    TransportInfo findByDriverAndEvent(Driver driver, Event event);

    boolean existsByDriverAndEvent(Driver driver, Event event);
}
