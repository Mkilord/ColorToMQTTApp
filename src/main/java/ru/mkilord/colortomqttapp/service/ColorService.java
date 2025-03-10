package ru.mkilord.colortomqttapp.service;

import java.awt.*;

public interface ColorService {
    void start();

    void stop();

    Color getCurrentColor();
}
