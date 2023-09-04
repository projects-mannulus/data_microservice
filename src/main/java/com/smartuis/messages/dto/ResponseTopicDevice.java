package com.smartuis.messages.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * respuesta del topico en el que se publicara la informacion del dispositivo.
 */
@Data
@AllArgsConstructor
public class ResponseTopicDevice {
    private String topic;
}
