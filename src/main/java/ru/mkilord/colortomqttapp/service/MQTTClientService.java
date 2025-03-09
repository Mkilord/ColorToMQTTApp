package ru.mkilord.colortomqttapp.service;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.BindSettings;
import ru.mkilord.colortomqttapp.core.HSBColor;

import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@Service
@FieldDefaults(level = PRIVATE)
public class MQTTClientService implements BindSettings {

    String broker, topic, username, password;

    MqttClient client;

    private void connect() {
        var options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        tryConnect(options);
    }

    public void sendColor(HSBColor color) {
        var strMessage = """
                {"hue":%.0f,"sat":%.0f,"brightness":%.0f}"""
                .formatted(color.getHue(), color.getSaturation(), color.getBrightness());
        var message = new MqttMessage(strMessage.getBytes());
        message.setQos(0);
        try {
            client.publish(topic, message);
            log.info("Message sent {}", strMessage);
        } catch (MqttException e) {
            log.error("Error sending message {}", strMessage, e);
        }
    }

    @Override
    public void applySettings(Properties props) {
        this.broker = props.getProperty("broker");
        this.username = props.getProperty("username");
        this.password = props.getProperty("password");
        this.topic = props.getProperty("topic");
        connect();
    }

    private void tryConnect(MqttConnectOptions options) {
        try {
            client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
            client.connect(options);
            log.info("Connected to broker: " + broker);
        } catch (MqttException e) {
            log.error(e);
        }
    }
}
