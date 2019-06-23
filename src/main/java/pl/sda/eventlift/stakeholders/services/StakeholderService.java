package pl.sda.eventlift.stakeholders.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.events.services.EventsService;
import pl.sda.eventlift.information.services.TransportInfoService;
import pl.sda.eventlift.stakeholders.model.Driver;
import pl.sda.eventlift.stakeholders.model.Hitchhiker;
import pl.sda.eventlift.stakeholders.model.Stakeholder;
import pl.sda.eventlift.stakeholders.repositories.StakeholderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StakeholderService {

    @Autowired
    private StakeholderRepository stakeholderRepository;

    @Autowired
    private TransportInfoService transportInfoService;

    public Set<Event> getActualStakeholderEvents(Long stakeholderId, String stakeholderStatus) {
        if (stakeholderRepository.findById(stakeholderId).isPresent()) {
            Set<Event> events = new HashSet<>();
            if (stakeholderStatus.equals("Driver") && stakeholderRepository.findById(stakeholderId).get().getDriver() != null) {
                Driver driver = stakeholderRepository.findById(stakeholderId).get().getDriver();
                events.addAll(driver.getEvents().stream()
                        .filter(event -> transportInfoService.getDriverTransportInfo(event.getUuid(), driver.getId()).getStartTime().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toSet()));
            }
            if (stakeholderStatus.equals("Hitch-hiker") && stakeholderRepository.findById(stakeholderId).get().getHitchhiker() != null) {
                Hitchhiker hitchhiker = stakeholderRepository.findById(stakeholderId).get().getHitchhiker();
                events.addAll(hitchhiker.getDrivers().stream()
                        .map(driver -> driver.getEvents().stream()
                                .filter(event -> transportInfoService.getDriverTransportInfo(event.getUuid(), driver.getId()).getStartTime().isAfter(LocalDateTime.now()))
                                .collect(Collectors.toSet()))
                .findFirst().orElseGet(Collections::emptySet));
            }
            return events;
        }
        return null;
    }
}
