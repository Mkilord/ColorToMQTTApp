package ru.mkilord.colortomqttapp.detector;

import lombok.RequiredArgsConstructor;
import ru.mkilord.colortomqttapp.processor.Processor;

import java.awt.*;
import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public abstract class Detector {
    protected final Processor processor;
    public abstract Color detect(BufferedImage image);
}
