package ru.mkilord.colortomqttapp.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.SettingsService;
import ru.mkilord.colortomqttapp.core.HSBColor;

import java.awt.*;

import static lombok.AccessLevel.PRIVATE;

@Service
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public final class ColorService {

    SettingsService settingsService;

    ScreenCaptureService screenCaptureService;
    ColorDetectorService colorDetectorService;
    ColorChangeService colorChangeService;
    ColorSenderService colorSenderService;
    ColorLimitService colorLimitService;

    @Getter
    @NonFinal
    Color currentColor = Color.BLACK;

    RepeaterService repeaterService;

    public void start() {
        applySettings();
        repeaterService.repeat(this::detectAndSendColor);
    }

    public void stop() {
        repeaterService.stop();
    }

    private void applySettings() {
        var properties = settingsService.loadOrElseLoadDefault();
        screenCaptureService.applySettings(properties);
        colorDetectorService.applySettings(properties);
        colorChangeService.applySettings(properties);
        colorSenderService.applySettings(properties);
        repeaterService.applySettings(properties);
        colorLimitService.applySettings(properties);
    }

    private HSBColor getHSBColorWithLimit(HSBColor hsbColor) {
        return colorLimitService.applyLimit(hsbColor);
    }

    private boolean hasColorChanged(HSBColor hsbColor) {
        return colorChangeService.hasColorChanged(hsbColor);
    }

    private void updateCurrentColorAndSent(HSBColor hsbColor) {
        currentColor = Color.getHSBColor(hsbColor.getHue() / 360, hsbColor.getSaturation() / 100, hsbColor.getBrightness() / 100);
        colorSenderService.send(hsbColor);
    }

    private HSBColor processDetectedColor() {
        var image = screenCaptureService.captureScreen();
        var color = colorDetectorService.detectColor(image);

        return getHSBColorWithLimit(new HSBColor(color));
    }

    private void detectAndSendColor() {
        var hsbColor = processDetectedColor();
        if (hasColorChanged(hsbColor)) updateCurrentColorAndSent(hsbColor);
    }
}