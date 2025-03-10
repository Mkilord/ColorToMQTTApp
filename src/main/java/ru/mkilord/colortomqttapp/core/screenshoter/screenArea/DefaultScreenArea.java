package ru.mkilord.colortomqttapp.core.screenshoter.screenArea;

import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class DefaultScreenArea implements ScreenArea {
    public static final String SCREEN_HEIGHT_KEY = "screenHeight";
    public static final String SCREEN_WIGHT_KEY = "screenWight";

    Dimension size;

    public DefaultScreenArea(Properties properties) {
        var height = Integer.parseInt(properties.getProperty(SCREEN_HEIGHT_KEY));
        var width = Integer.parseInt(properties.getProperty(SCREEN_WIGHT_KEY));
        this.size = new Dimension(width, height);
    }

    @Override
    public Rectangle getScreenArea() {
        var resolution = Toolkit.getDefaultToolkit().getScreenSize();
        var x = (resolution.width - size.width) / 2;
        var y = (resolution.height - size.height) / 2;
        return new Rectangle(x, y, size.width, size.height);
    }
}
