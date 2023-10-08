package com.smartuis.messages.documents;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "messages")
public class DeviceMessage {

    /*Identificador del mensaje */
    public String _id;

    /*Identificador unico del dispositivo */
    public String deviceUUID;

    /*Topico del mensaje */
    public String topic;

    /*Fecha de Creación del Mensaje */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Date timeStamp;

    /*Mensaje del dispositivo clave(String) - Valor(String, Int, etc..)*/
    public Map<String, Object> values;

    /*Estado del Dispositivo (opcional)*/
    public String status;
    
    /*Indica si el mensaje es una alerta (opcional)*/
    public Boolean alert;
    
}
