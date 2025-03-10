package ru.mkilord.colortomqttapp.core.publisher;

import ru.mkilord.colortomqttapp.core.HSBColor;

public interface ColorPublisher {
    void publish(HSBColor color);
}
