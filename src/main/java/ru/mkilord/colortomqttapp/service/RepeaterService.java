package ru.mkilord.colortomqttapp.service;

import org.springframework.stereotype.Service;

@Service
public interface RepeaterService {
    void repeat(Runnable runnable);
    void stop();
}
