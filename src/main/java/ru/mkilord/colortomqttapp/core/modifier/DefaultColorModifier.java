package ru.mkilord.colortomqttapp.core.modifier;

import lombok.experimental.FieldDefaults;
import ru.mkilord.colortomqttapp.core.HSBColor;

import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DefaultColorModifier implements ColorModifier {

    public static final String MODIFY_HUE_KEY = "modifyHue";
    public static final String MODIFY_SATURATION_KEY = "modifySaturation";
    public static final String MODIFY_BRIGHTNESS_KEY = "modifyBrightness";

    float modifyHue;
    float modifySaturation;
    float modifyBrightness;

    public DefaultColorModifier(Properties properties) {
        this.modifyHue = Float.parseFloat(properties.getProperty(MODIFY_HUE_KEY));
        this.modifySaturation = Float.parseFloat(properties.getProperty(MODIFY_SATURATION_KEY));
        this.modifyBrightness = Float.parseFloat(properties.getProperty(MODIFY_BRIGHTNESS_KEY));
    }

    private float applyModifyFor(float value, float modification, float max) {
        float modifiedValue = value + modification;
        return Math.min(Math.max(0, modifiedValue), max);
    }

    @Override
    public HSBColor modify(HSBColor color) {
        var h = applyModifyFor(color.getHue(), modifyHue, 360);
        var s = applyModifyFor(color.getSaturation(), modifySaturation, 100);
        var b = applyModifyFor(color.getBrightness(), modifyBrightness, 100);
        return new HSBColor(h, s, b);
    }
}
