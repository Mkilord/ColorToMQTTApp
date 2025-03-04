package ru.mkilord.colortomqttapp.common.detector;

import lombok.extern.log4j.Log4j2;
import ru.mkilord.colortomqttapp.common.detector.processor.Processor;

import java.awt.*;
import java.awt.image.BufferedImage;

@Log4j2
public class AverageColorDetector extends Detector {
    public AverageColorDetector(Processor processor) {
        super(processor);
    }

    public Color detect(BufferedImage image) {
        class rgbCount {
            long red, green, blue;
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

        log.debug("Average color R:{}, G:{}, B:{}", averageRed, averageGreen, averageBlue);

        return new Color(averageRed, averageGreen, averageBlue);
    }
}