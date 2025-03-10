package ru.mkilord.colortomqttapp.core.tracker;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.awt.*;

import static lombok.AccessLevel.PRIVATE;

@Setter
@Getter
@FieldDefaults(level = PRIVATE)
public abstract class ColorStateTracker {

    public static String STATE_TRACKER_KEY = "stateTracker";
    Color currentColor = Color.BLACK;

    public abstract boolean hasColorChanged(Color color);
}
