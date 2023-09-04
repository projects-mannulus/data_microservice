package com.smartuis.messages.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.smartuis.messages.mappers.MessageMapper;
import com.smartuis.messages.service.interfaces.DeviceMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartuis.messages.documents.DeviceMessage;
import com.smartuis.messages.dto.DeviceMessageDetail;
import com.smartuis.messages.repository.DeviceMessageRepository;

@Service
public class DeviceMessageServiceImpl implements DeviceMessageService {

    @Autowired
    private DeviceMessageRepository repository;

    @Override
    public List<DeviceMessageDetail> findByDeviceUUID(String uuid, Date initialDate, Date finalDate) {
        List<DeviceMessage> db_list;

        if(initialDate != null && finalDate != null){
            db_list = repository.findBydeviceUUIDAndTimeStampBetween(uuid, initialDate, finalDate);
        } else if (initialDate != null){
            db_list = repository.findBydeviceUUIDAndTimeStampGreaterThanEqual(uuid, initialDate);
        } else {
            db_list = repository.findBydeviceUUID(uuid);
        }
        List<DeviceMessageDetail> list_messages =
                db_list.stream().map(MessageMapper.INSTANCE::toDeviceMessage).collect(Collectors.toList());

        return list_messages;
    }


}
