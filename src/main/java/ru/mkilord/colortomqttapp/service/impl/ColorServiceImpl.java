package ru.mkilord.colortomqttapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.SettingsService;
import ru.mkilord.colortomqttapp.core.AbstractFactory;
import ru.mkilord.colortomqttapp.core.HSBColor;
import ru.mkilord.colortomqttapp.core.detector.ColorDetector;
import ru.mkilord.colortomqttapp.core.limit.ColorLimit;
import ru.mkilord.colortomqttapp.core.limit.DefaultColorLimit;
import ru.mkilord.colortomqttapp.core.modifier.ColorModifier;
import ru.mkilord.colortomqttapp.core.modifier.DefaultColorModifier;
import ru.mkilord.colortomqttapp.core.publisher.ColorPublisher;
import ru.mkilord.colortomqttapp.core.publisher.MQTTColorPublisher;
import ru.mkilord.colortomqttapp.core.screenshoter.DefaultScreenShooter;
import ru.mkilord.colortomqttapp.core.screenshoter.ScreenShooter;
import ru.mkilord.colortomqttapp.core.tracker.ColorStateTracker;
import ru.mkilord.colortomqttapp.service.ColorService;

import java.awt.*;

import static lombok.AccessLevel.PRIVATE;

@Service
@Log4j2
@FieldDefaults(level = PRIVATE)
@RequiredArgsConstructor
public final class ColorServiceImpl implements ColorService {

    final SettingsService settingsService;

    RepeatServiceImpl repeatServiceImpl;
    ScreenShooter screenShooter;

    ColorDetector colorDetector;
    ColorStateTracker colorTracker;
    ColorLimit colorLimit;
    ColorPublisher colorPublisher;
    ColorModifier colorModifier;

    public void start() {
        bind();
        repeatServiceImpl.repeat(this::process);
    }

    public void stop() {
        repeatServiceImpl.stop();
    }

    public Color getCurrentColor() {
        return colorTracker.getCurrentColor();
    }

    private void bind() {
        var properties = settingsService.loadOrElseLoadDefault();

        this.colorModifier = new DefaultColorModifier(properties);
        this.screenShooter = new DefaultScreenShooter(properties);
        this.colorDetector = new AbstractFactory<ColorDetector>().get(ColorDetector.DETECTOR_KEY, properties);
        this.colorLimit = new DefaultColorLimit(properties);
        this.colorPublisher = new MQTTColorPublisher(properties);
        this.colorTracker = new AbstractFactory<ColorStateTracker>().get(ColorStateTracker.STATE_TRACKER_KEY, properties);
        this.repeatServiceImpl = new RepeatServiceImpl(properties);
    }

    private void process() {
        var color = colorDetector.detect(screenShooter.getScreenshot());
        if (hasColorChanged(color)) applyLimitAndPublish(new HSBColor(color));
    }

    private boolean hasColorChanged(Color color) {
        return colorTracker.hasColorChanged(color);
    }

    private void applyLimitAndPublish(HSBColor hsbColor) {
        hsbColor = colorModifier.modify(hsbColor);
        hsbColor = colorLimit.applyFor(hsbColor);
        colorPublisher.publish(hsbColor);
    }
}