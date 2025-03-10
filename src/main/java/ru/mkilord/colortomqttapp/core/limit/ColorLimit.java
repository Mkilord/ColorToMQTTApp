package ru.mkilord.colortomqttapp.core.limit;

import ru.mkilord.colortomqttapp.core.HSBColor;

public interface ColorLimit {
    HSBColor applyFor(HSBColor color);
}
