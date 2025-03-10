package ru.mkilord.colortomqttapp.core.detector;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import ru.mkilord.colortomqttapp.core.AbstractFactory;
import ru.mkilord.colortomqttapp.core.processor.Processor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class AverageColorDetector implements ColorDetector {
    Processor processor;

    public AverageColorDetector(Properties properties) {
        var processorAbstractFactory = new AbstractFactory<Processor>();
        this.processor = processorAbstractFactory.get(Processor.PROCESSOR_KEY, properties);
    }

    public Color detect(BufferedImage image) {
        class rgbCount {
            float red, green, blue;
            int count;
        }
        var rgbCount = new rgbCount();

        processor.process(image.getWidth(), image.getHeight(), (x, y) -> {
            var pixel = new Color(image.getRGB(x, y));
            rgbCount.red += pixel.getRed();
            rgbCount.green += pixel.getGreen();
            rgbCount.blue += pixel.getBlue();
            rgbCount.count++;
        });

        var averageRed = (int) (rgbCount.red / rgbCount.count);
        var averageGreen = (int) (rgbCount.green / rgbCount.count);
        var averageBlue = (int) (rgbCount.blue / rgbCount.count);

        var color = new Color(averageRed, averageGreen, averageBlue);
        log.debug("Average color R:{}, G:{}, B:{}", color.getRed(), color.getGreen(), color.getBlue());
        return color;
    }
}