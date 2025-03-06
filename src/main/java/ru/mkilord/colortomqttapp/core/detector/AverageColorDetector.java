package ru.mkilord.colortomqttapp.core.detector;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import ru.mkilord.colortomqttapp.core.HSBColor;
import ru.mkilord.colortomqttapp.core.processor.Processor;
import ru.mkilord.colortomqttapp.core.utils.Converter;

import java.awt.*;
import java.awt.image.BufferedImage;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AverageColorDetector extends Detector {
    public AverageColorDetector(Processor processor) {
        super(processor);
    }
    Converter converter = new Converter();

    public HSBColor detect(BufferedImage image) {
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

        var hsbColor = converter.rgbToHSV(averageRed, averageGreen, averageBlue);

        log.debug("Average color h:{}, s:{}, b:{}", hsbColor.getHue(), hsbColor.getSaturation(), hsbColor.getBrightness());
        return hsbColor;
    }
}