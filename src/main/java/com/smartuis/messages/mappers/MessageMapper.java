package com.smartuis.messages.mappers;

import com.smartuis.messages.documents.DeviceMessage;
import com.smartuis.messages.dto.DeviceMessageDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
    /**
     * Mapper para convertir DeviceMessage al DTO
     */
    @Mapping(target = "deviceUUID", source = "deviceUUID")
    @Mapping(target = "timeStamp", source = "timeStamp")
    DeviceMessageDetail toDeviceMessage(DeviceMessage aEntidad);

    DeviceMessage toDeviceMessageDetail(DeviceMessageDetail aEntidad);
}
