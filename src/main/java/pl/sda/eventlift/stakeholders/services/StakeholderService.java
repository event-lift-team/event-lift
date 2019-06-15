package pl.sda.eventlift.stakeholders.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.stakeholders.repositories.StakeholderRepository;

import java.util.Set;

@Service
public class StakeholderService {

    @Autowired
    private StakeholderRepository stakeholderRepository;

    public Set<Event> getStakeholderEvents(Long stakeholderId){
        if(stakeholderRepository.findById(stakeholderId).isPresent()) {
            return stakeholderRepository.findById(stakeholderId).get().getEvents();
        } else {
            throw new RuntimeException("podany użytkownik nie ma przypisanych wydarzeń");
        }
    }
}
