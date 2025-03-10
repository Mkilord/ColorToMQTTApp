package ru.mkilord.colortomqttapp.core.detector;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface ColorDetector {
    String DETECTOR_KEY = "detector";

    Color detect(BufferedImage image);
}
