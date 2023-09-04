package com.smartuis.messages.controller;

import java.util.Date;
import java.util.List;

import com.smartuis.messages.dto.ResponseTopicDevice;
import com.smartuis.messages.utils.IConstants;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartuis.messages.service.interfaces.DeviceMessageService;
import com.smartuis.messages.dto.DeviceMessageDetail;

@RestController
@RequestMapping("/api/device/message")
public class DeviceMessageController {

    @Autowired
    private DeviceMessageService service;

    /**
     * Obtiene los mensajes de un dispositivo con determinados criterios de consulta.
     * <p>
     * tener en cuenta que si se envia la fecha inicial y no la final, se obtendran los mensajes.
     *
     * @param uuid        identificador del dispositivo.
     * @param initialDate fecha inicial de los mensajes. (opcional)
     * @param finalDate   fecha final de los mensajes. (opcional)
     * @return
     */
    @GetMapping
    public List<DeviceMessageDetail> deviceMessage(
            @RequestParam String uuid,
            @RequestParam(required = false) @DateTimeFormat(pattern = IConstants.formatDateTime) Date initialDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = IConstants.formatDateTime) Date finalDate
    ) {
        return service.findByDeviceUUID(uuid, initialDate, finalDate);
    }

    /**
     * le indica a un dispositivo que se publique la informacion en el broker de smart campus
     * en determinado topico.
     *
     * @param uuid identificador del dispositivo.
     * @return topico en el que se publicara la informacion del dispositivo.
     * @apiNote el dispositivo debe estar conectado al gateway.
     * @apiNote el dispositivo sigue publicando en el broker de su gateway.
     */
    @GetMapping("subscribe")
    public ResponseEntity<ResponseTopicDevice> devicePublishInBrokerServer(@RequestParam String uuid) {
        //se envia un mensaje al gateway del dispositivo para que el dispositivo publique
        //directamente en el broker de smart campus
        //y al usuario se le indica cual es el topico en el que se publicara la informacion de ese dispositivo.
        //TODD: implementar
        return new ResponseEntity<>(new ResponseTopicDevice("device-" + uuid), HttpStatus.OK);
    }
}
