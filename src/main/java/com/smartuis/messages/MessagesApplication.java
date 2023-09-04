package com.smartuis.messages;


import com.smartuis.messages.utils.IEnviroments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import com.smartuis.messages.service.interfaces.DeviceMessageMqttService;


@SpringBootApplication
public class MessagesApplication {

//    private String brokerIp = "tcp://"+System.getenv("BROKER_IP")+":1883";
//    //private String brokerIp = "tcp://localhost:1883";
//    private String clientId = "serviceMessageClient";
//    private String topic = "device-messages";

    @Autowired
    private DeviceMessageMqttService mqttService;

    @Autowired
    private IEnviroments enviroments;


	public static void main(String[] args) {
		SpringApplication.run(MessagesApplication.class, args);
	}

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        enviroments.brokerUri,
                        enviroments.clientId,
                        enviroments.topic);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            private final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                LOGGER.info("MQTT RECIBE MESSAGE: "+message.toString());
                mqttService.saveMqttDeviceMessage(message);
            }

        };
    }

	

}
