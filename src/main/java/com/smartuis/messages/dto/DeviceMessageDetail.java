package com.smartuis.messages.dto;


import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartuis.messages.documents.DeviceMessage;

import com.smartuis.messages.utils.IConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceMessageDetail {

     /*Identificador unico del dispositivo */
     private String deviceUUID;

     /*Topico del mensaje */
     private String topic;
 
     /*Fecha de Creación del Mensaje */
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = IConstants.formatDateTime)
     private Date timeStamp;
 
     /*Mensaje*/
     private Map<String,Object> values;
 
     /*Estado del Dispositivo (opcional)*/
     private String status;
     
     /*Indica si el mensaje es una alerta (opcional)*/
     private Boolean alert;
    
}
