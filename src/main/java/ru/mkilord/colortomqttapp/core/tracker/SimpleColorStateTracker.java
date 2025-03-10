package ru.mkilord.colortomqttapp.core.tracker;

import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public final class SimpleColorStateTracker extends ColorStateTracker {

    Color currentColor = Color.BLACK;

    @Override
    public boolean hasColorChanged(Color color) {
        var hasChanged = !Objects.equals(currentColor, color);
        if (hasChanged) this.currentColor = color;
        return hasChanged;
    }

}
