package ru.mkilord.colortomqttapp.core.tracker;

import java.awt.*;

public interface ColorStateTracker {
    boolean hasColorChanged(Color color);
    Color getCurrentColor();
}
