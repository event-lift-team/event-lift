package pl.sda.eventlift.events.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.sda.eventlift.events.dto.EventSimpleDataDTO;
import pl.sda.eventlift.events.model.Event;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    Event eventDTOToEvent(EventSimpleDataDTO eventSimpleDataDTO);

    EventSimpleDataDTO eventToEventDTO(Event event);

}
