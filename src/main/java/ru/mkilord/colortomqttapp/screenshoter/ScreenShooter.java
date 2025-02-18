package ru.mkilord.colortomqttapp.screenshoter;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.awt.*;
import java.awt.image.BufferedImage;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ScreenShooter {
    Robot robot;

    public BufferedImage getScreenshot(Rectangle screenArea) {
        log.debug("Get screenshot.");
        return robot.createScreenCapture(screenArea);
    }
}
