package ru.mkilord.colortomqttapp.service;

import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public interface ColorService {
    void start();

    void stop();

    Color getCurrentColor();
}
