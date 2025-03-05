package ru.mkilord.colortomqttapp.core.detector;

import ru.mkilord.colortomqttapp.core.processor.Processor;

public class DetectorFactory {
    public static final String AVERAGE_COLOR_DETECTOR = "averageColorDetector";

    public static Detector get(String type, Processor processor) {
        if (type.equals(AVERAGE_COLOR_DETECTOR)) return new AverageColorDetector(processor);
        throw new IllegalArgumentException("Unknown detector type: " + type);
    }
}
