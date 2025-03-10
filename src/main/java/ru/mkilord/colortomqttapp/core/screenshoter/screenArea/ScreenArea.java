package ru.mkilord.colortomqttapp.core.screenshoter.screenArea;

import java.awt.*;

public interface ScreenArea {
    String SCREEN_AREA_KEY = "screenAreaType";
    Rectangle getScreenArea();
}
