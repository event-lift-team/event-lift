package pl.sda.eventlift.stakeholders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.eventlift.stakeholders.model.Stakeholder;

public interface StakeholderRepository extends JpaRepository<Stakeholder, Long> {

    Stakeholder findByFirstNameAndLastName(String firstName, String lastName);

    Stakeholder findByEmail(String email);

    boolean existsByEmail(String email);
}
