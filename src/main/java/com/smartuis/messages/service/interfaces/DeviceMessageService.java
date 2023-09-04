package com.smartuis.messages.service.interfaces;

import java.util.Date;
import java.util.List;

import com.smartuis.messages.dto.DeviceMessageDetail;

public interface DeviceMessageService {

     /**
     * 
     * Return device messages
     * @return {@link DeviceMesssageDetail}
     */
    List<DeviceMessageDetail> findByDeviceUUID(String uuid, Date initialDate, Date finalDate);
    
}
