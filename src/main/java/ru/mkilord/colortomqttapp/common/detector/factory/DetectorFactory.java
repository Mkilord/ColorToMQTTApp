package ru.mkilord.colortomqttapp.common.detector.factory;

import org.springframework.stereotype.Component;
import ru.mkilord.colortomqttapp.common.detector.AverageColorDetector;
import ru.mkilord.colortomqttapp.common.detector.Detector;
import ru.mkilord.colortomqttapp.common.detector.processor.Processor;

@Component
public class DetectorFactory {
    public static final String AVERAGE_COLOR_DETECTOR = "averageColorDetector";

    public static Detector get(String type, Processor processor) {
        if (type.equals(AVERAGE_COLOR_DETECTOR)) return new AverageColorDetector(processor);
        throw new IllegalArgumentException("Unknown detector type: " + type);
    }
}
