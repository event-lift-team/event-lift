package pl.sda.eventlift.stakeholders.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.sda.eventlift.stakeholders.dto.DriverDTO;
import pl.sda.eventlift.stakeholders.model.Driver;

@Mapper
public interface DriverMapper {

    DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);

    Driver driverDTOToDriver(DriverDTO driverDTO);

    DriverDTO driverToDriverDTO(Driver driver);
}
