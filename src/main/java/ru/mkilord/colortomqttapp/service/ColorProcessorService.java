package ru.mkilord.colortomqttapp.service;

import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.BindSettings;
import ru.mkilord.colortomqttapp.core.detector.Detector;
import ru.mkilord.colortomqttapp.core.detector.DetectorFactory;
import ru.mkilord.colortomqttapp.core.processor.ProcessorFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Setter
@Service
@FieldDefaults(level = PRIVATE)
public final class ColorProcessorService implements BindSettings {
    Detector detector;

    @Override
    public void applySettings(Properties props) throws RuntimeException {
        var processor = ProcessorFactory.get(props.getProperty("processor"), props);
        this.detector = DetectorFactory.get(props.getProperty("detector"), processor);
    }

    public Color detectColor(BufferedImage image) {
        return detector.detect(image);
    }
}
