package ru.mkilord.colortomqttapp.core.tracker;

import java.awt.*;

public interface ColorStateTracker {
    String STATE_TRACKER_KEY = "stateTracker";

    boolean hasColorChanged(Color color);
    Color getCurrentColor();
}
