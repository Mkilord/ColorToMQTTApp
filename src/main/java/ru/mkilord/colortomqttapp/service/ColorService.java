package ru.mkilord.colortomqttapp.service;

import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public interface ColorService {
    boolean isStart();

    void start();

    void stop();

    Color getCurrentColor();
}
