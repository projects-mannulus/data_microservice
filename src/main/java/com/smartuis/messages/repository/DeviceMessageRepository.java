package com.smartuis.messages.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.smartuis.messages.documents.DeviceMessage;


public interface DeviceMessageRepository extends MongoRepository<DeviceMessage, String> {

    public List<DeviceMessage> findBydeviceUUID(String deviceUUID);

    public List<DeviceMessage> findBydeviceUUIDAndTimeStampBetween(String deviceUUID, Date initialDate, Date finalDate);

    public List<DeviceMessage> findBydeviceUUIDAndTimeStampGreaterThanEqual(String deviceUUID, Date initialDate);

//    public List<DeviceMessage> findBydeviceUUIDAndTimeStampLessThanEqual(String deviceUUID, String finalDate);

    public List<DeviceMessage> findBytopic(String topic);
    
}

