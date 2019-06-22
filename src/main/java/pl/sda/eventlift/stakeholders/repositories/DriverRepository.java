package pl.sda.eventlift.stakeholders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.eventlift.stakeholders.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    Driver findByEmail(String email);

    boolean existsByEmail(String email);
}
