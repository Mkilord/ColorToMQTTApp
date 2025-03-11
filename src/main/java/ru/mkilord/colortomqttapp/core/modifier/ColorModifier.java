package ru.mkilord.colortomqttapp.core.modifier;

import ru.mkilord.colortomqttapp.core.HSBColor;

public interface ColorModifier {
    HSBColor modify(HSBColor color);
}
