package ru.mkilord.colortomqttapp.core.screenshoter;

import java.awt.*;

public class ScreenTools {
    public static Rectangle createCenteredAreaWithSize(Dimension size) {
        var resolution = Toolkit.getDefaultToolkit().getScreenSize();
        var x = (resolution.width - size.width) / 2;
        var y = (resolution.height - size.height) / 2;
        return new Rectangle(x, y, size.width, size.height);
    }
}
