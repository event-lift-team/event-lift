package pl.sda.eventlift.stakeholders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.eventlift.stakeholders.model.Hitchhiker;

public interface HitchhikerRepository extends JpaRepository<Hitchhiker, Long> {

    Hitchhiker findByEmail(String email);

    boolean existsByEmail(String email);
}
