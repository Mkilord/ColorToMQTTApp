package ru.mkilord.colortomqttapp.core.publisher;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import ru.mkilord.colortomqttapp.core.HSBColor;

import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(level = PRIVATE)
public final class MQTTColorPublisher implements ColorPublisher {
    final String broker, topic, username, password;
    @Getter
    MqttClient client;

    public MQTTColorPublisher(Properties properties) {
        this.broker = properties.getProperty("broker");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
        this.topic = properties.getProperty("topic");
        compileOptionAndTryConnect();
    }

    @Override
    public void publish(HSBColor color) {
        var message = createMessage(color);
        tryPublish(message);
    }

    private void tryPublish(MqttMessage message) {
        try {
            client.publish(topic, message);
        } catch (MqttException e) {
            log.error("Error sending message!", e);
        }
    }

    private MqttMessage createMessage(HSBColor color) {
        var strMessage = """
                {"hue":%.0f,"sat":%.0f,"brightness":%.0f}"""
                .formatted(color.getHue(), color.getSaturation(), color.getBrightness());
        log.debug(strMessage);
        var message = new MqttMessage(strMessage.getBytes());
        message.setQos(0);
        return message;
    }

    private void compileOptionAndTryConnect() {
        var options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        tryConnect(options);
    }

    private void tryConnect(MqttConnectOptions options) {
        try {
            client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
            client.connect(options);
            log.info("Connected to broker: " + broker);
        } catch (MqttException e) {
            log.error(e.getMessage());
        }
    }
}
