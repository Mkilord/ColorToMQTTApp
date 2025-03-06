package ru.mkilord.colortomqttapp.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.SettingsService;
import ru.mkilord.colortomqttapp.core.HSBColor;

import static lombok.AccessLevel.PRIVATE;

@Service
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor(access = PRIVATE)
public final class ColorService {

    SettingsService settingsService;

    ScreenCaptureService screenCaptureService;
    ColorDetectorService colorDetectorService;
    ColorChangeService colorChangeService;
    ColorSenderService colorSenderService;


    RepeaterService repeaterService;

    public void start() {
        applySettings();
        repeaterService.repeat(this::detectAndSendColor);
    }

    public void stop() {
        repeaterService.stop();
    }

    public HSBColor getCurrentColor() {
        return colorChangeService.getCurrentColor();
    }

    private void applySettings() {
        var properties = settingsService.loadOrElseLoadDefault();
        screenCaptureService.applySettings(properties);
        colorDetectorService.applySettings(properties);
        colorChangeService.applySettings(properties);
        colorSenderService.applySettings(properties);
        repeaterService.applySettings(properties);
    }

    private void detectAndSendColor() {
        var image = screenCaptureService.captureScreen();
        var color = colorDetectorService.detectColor(image);
        if (colorChangeService.hasColorChanged(color))
            colorSenderService.send(color);
    }
}