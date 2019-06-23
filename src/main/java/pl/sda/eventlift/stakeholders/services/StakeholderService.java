package pl.sda.eventlift.stakeholders.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.stakeholders.repositories.StakeholderRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class StakeholderService {

    private StakeholderRepository stakeholderRepository;

    @Autowired
    public StakeholderService(StakeholderRepository stakeholderRepository) {
        this.stakeholderRepository = stakeholderRepository;
    }

    //todo only active
    public Set<Event> getStakeholderEvents(Long stakeholderId, String stakeholderStatus) {
        if (stakeholderRepository.findById(stakeholderId).isPresent()) {
            Set<Event> events = new HashSet<>();
            if (stakeholderStatus.equals("Driver") && stakeholderRepository.findById(stakeholderId).get().getDriver() != null)
                events.addAll(stakeholderRepository.findById(stakeholderId).get().getDriver().getEvents());
            if (stakeholderStatus.equals("Hitch-hiker") && stakeholderRepository.findById(stakeholderId).get().getHitchhiker() != null) {
                events.addAll(stakeholderRepository.findById(stakeholderId).get().getHitchhiker().getEvents());
            }
            return events;
        }
        return null;
    }
}
