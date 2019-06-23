package pl.sda.eventlift.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.stakeholders.dto.DriverDTO;
import pl.sda.eventlift.stakeholders.mapper.DriverMapper;
import pl.sda.eventlift.stakeholders.repositories.DriverRepository;
import pl.sda.eventlift.stakeholders.services.StakeholderContext;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class RestServiceImpl implements RestService {

    private DriverRepository driverRepository;
    private DriverMapper driverMapper;
    private StakeholderContext stakeholderContext;

    @Autowired
    public RestServiceImpl(DriverRepository driverRepository,
                           DriverMapper driverMapper,
                           StakeholderContext stakeholderContext) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
        this.stakeholderContext = stakeholderContext;
    }

    @Override
    public Set<DriverDTO> getAllDriversWithActiveOffer() {
        Set<DriverDTO> result = new HashSet<>();
        driverRepository.findAll().stream()
                .filter(d -> d.getInformation().stream()
                        .anyMatch(t ->
                                t.getStartTime().isAfter(LocalDateTime.now()))
                ).forEach(d -> {
            d.getInformation().removeIf(t ->
                    t.getStartTime().isBefore(LocalDateTime.now()) || t.getNumberOfSeats() < 1
            );
            result.add(driverMapper.driverToDriverDTO(d));
        });
        return result;
    }
}
