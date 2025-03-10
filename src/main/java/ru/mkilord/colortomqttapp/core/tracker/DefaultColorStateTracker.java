package ru.mkilord.colortomqttapp.core.tracker;

import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.awt.*;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@FieldDefaults(level = PRIVATE)
public final class DefaultColorStateTracker extends ColorStateTracker {

    public static final String SENSITIVITY_KEY = "sensitivity";
    final int sensitivity;

    Color curColor = Color.BLACK;

    public DefaultColorStateTracker(Properties props) {
        this.sensitivity = Integer.parseInt(props.getProperty(SENSITIVITY_KEY));
        if (sensitivity < 0 || sensitivity > 100) {
            throw new IllegalArgumentException("Sensitivity must be from 0 to 100");
        }
    }

    @Override
    public Color getCurrentColor() {
        return curColor;
    }

    public boolean hasColorChanged(Color newColor) {

        var deltaRed = curColor.getRed() - newColor.getRed();
        var deltaGreen = curColor.getGreen() - newColor.getGreen();
        var deltaBlue = curColor.getBlue() - newColor.getBlue();

        var colorDifference = Math.sqrt(deltaRed * deltaRed + deltaGreen * deltaGreen + deltaBlue * deltaBlue);

        var threshold = (255 * sensitivity) / 100.0;

        if (colorDifference > threshold) {
            this.curColor = newColor;
            log.debug("Color has change to: {};", newColor.toString());
            return true;
        }
        return false;
    }
}

