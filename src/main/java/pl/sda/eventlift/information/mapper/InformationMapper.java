package pl.sda.eventlift.information.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.sda.eventlift.information.dto.TransportInfoDTO;
import pl.sda.eventlift.information.model.TransportInfo;

@Mapper
public interface InformationMapper {

    InformationMapper INSTANCE = Mappers.getMapper(InformationMapper.class);

    TransportInfoDTO transportInfoToTransportInfoDTO(TransportInfo transportInfo);

    TransportInfo transportInfoDTOToTransportInfo(TransportInfoDTO transportInfoDTO);
}