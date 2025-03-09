package ru.mkilord.colortomqttapp.core.detector;

import lombok.RequiredArgsConstructor;
import ru.mkilord.colortomqttapp.core.processor.Processor;

import java.awt.*;
import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public abstract class Detector {
    protected final Processor processor;

    public abstract Color detect(BufferedImage image);
}
