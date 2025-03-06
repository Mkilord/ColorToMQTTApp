package ru.mkilord.colortomqttapp.core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class HSBColor {
    float hue;
    float saturation;
    float brightness;

    public float[] getFloatArray() {
        return new float[]{hue, saturation, brightness};
    }

    public int[] getIntArray() {
        return new int[]{(int) hue, (int) saturation, (int) brightness};
    }
}
