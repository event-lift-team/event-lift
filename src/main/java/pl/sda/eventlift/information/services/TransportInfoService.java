package pl.sda.eventlift.information.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.events.repositories.EventRepository;
import pl.sda.eventlift.information.dto.TransportInfoDTO;
import pl.sda.eventlift.information.model.TransportInfo;
import pl.sda.eventlift.information.repositories.TransportInfoRepository;
import pl.sda.eventlift.stakeholders.model.Driver;
import pl.sda.eventlift.stakeholders.repositories.DriverRepository;

@Service
public class TransportInfoService {

    private TransportInfoRepository transportInfoRepository;
    private EventRepository eventRepository;
    private DriverRepository driverRepository;

    @Autowired
    public TransportInfoService(TransportInfoRepository transportInfoRepository,
                                EventRepository eventRepository,
                                DriverRepository driverRepository) {
        this.transportInfoRepository = transportInfoRepository;
        this.eventRepository = eventRepository;
        this.driverRepository = driverRepository;
    }

    public TransportInfo getDriverTransportInfo(String eventDtoId, Long driverId) {
        Driver driver = driverRepository.findById(driverId).get();
        Event event = eventRepository.findByUuid(eventDtoId);
        return transportInfoRepository.findByDriverAndEvent(driver, event);
    }

    public void reduceNoOfSeats(String eventDtoId, Long driverId) {
        Driver driver = driverRepository.findById(driverId).get();
        Event event = eventRepository.findByUuid(eventDtoId);
        TransportInfo information = transportInfoRepository.findByDriverAndEvent(driver, event);
        information.setNumberOfSeats(information.getNumberOfSeats() - 1);
        transportInfoRepository.save(information);
    }

    public void createInformationAndEventDbRelation(TransportInfoDTO dto, String eventDtoId, String stakeholderDtoId) {
        if (driverRepository.existsByEmail(stakeholderDtoId) && eventRepository.existsByUuid(eventDtoId)) {
            Driver driver = driverRepository.findByEmail(stakeholderDtoId);
            Event event = eventRepository.findByUuid(eventDtoId);
            if (transportInfoRepository.existsByDriverAndEvent(driver, event)) {
                TransportInfo information = transportInfoRepository.findByDriverAndEvent(driver, event);
                information.setStartLocation(dto.getStartLocation());
                information.setNumberOfSeats(dto.getNumberOfSeats());
                information.setStartTime(dto.getStartTime());
                information.setAdditionalInformation(StringUtils.isBlank(dto.getAdditionalInformation()) ? "Kierowca nie podał dodatkowych informacji" : dto.getAdditionalInformation());
                transportInfoRepository.save(information);
            } else {
                TransportInfo information = informationToEntity(dto);
                information.setEvent(eventRepository.findByUuid(eventDtoId));
                information.setDriver(driverRepository.findByEmail(stakeholderDtoId));
                transportInfoRepository.save(information);
            }

        }
    }

    private TransportInfo informationToEntity(TransportInfoDTO dto) {
        return TransportInfo.builder()
                .startLocation(dto.getStartLocation())
                .numberOfSeats(dto.getNumberOfSeats())
                .startTime(dto.getStartTime())
                .additionalInformation(StringUtils.isBlank(dto.getAdditionalInformation()) ? "Kierowca nie podał dodatkowych informacji" : dto.getAdditionalInformation()).build();
    }


}
