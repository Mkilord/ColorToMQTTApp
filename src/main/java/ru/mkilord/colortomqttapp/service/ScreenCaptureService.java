package ru.mkilord.colortomqttapp.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.core.screenshoter.ScreenShooter;
import ru.mkilord.colortomqttapp.config.BindSettings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;
import static ru.mkilord.colortomqttapp.core.screenshoter.ScreenAreaFactory.get;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public final class ScreenCaptureService implements BindSettings {
    final ScreenShooter screenShooter;

    Rectangle screenArea;

    @Override
    public void applySettings(Properties props) {
        var screenSize = new Dimension(
                Integer.parseInt(props.getProperty("screenHeight")),
                Integer.parseInt(props.getProperty("screenWight"))
        );
        var screenAreaType = props.getProperty("screenAreaType");

        screenArea = get(screenAreaType, screenSize);
    }

    public BufferedImage captureScreen() {
        return screenShooter.getScreenshot(screenArea);
    }
}
