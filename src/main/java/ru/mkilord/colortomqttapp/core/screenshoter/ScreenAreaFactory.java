package ru.mkilord.colortomqttapp.core.screenshoter;

import java.awt.*;

public class ScreenAreaFactory {
    public static final String CENTERED_AREA = "centeredArea";

    public static Rectangle get(String type, Dimension size) {
        if (CENTERED_AREA.equals(type)) {
            return ScreenArea.createCenteredAreaWithSize(size);
        }
        throw new IllegalArgumentException("Unknown screen area: " + type);
    }
}
