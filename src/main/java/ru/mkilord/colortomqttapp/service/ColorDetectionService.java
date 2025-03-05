package ru.mkilord.colortomqttapp.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.BindSettings;
import ru.mkilord.colortomqttapp.config.SettingsService;
import ru.mkilord.colortomqttapp.core.detector.ColorChangeMatcher;
import ru.mkilord.colortomqttapp.core.detector.Detector;
import ru.mkilord.colortomqttapp.core.processor.ProcessorFactory;
import ru.mkilord.colortomqttapp.core.screenshoter.ScreenShooter;
import ru.mkilord.colortomqttapp.core.screenshoter.ScreenTools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;
import static ru.mkilord.colortomqttapp.core.detector.DetectorFactory.AVERAGE_COLOR_DETECTOR;
import static ru.mkilord.colortomqttapp.core.detector.DetectorFactory.get;
import static ru.mkilord.colortomqttapp.core.processor.ProcessorFactory.CHESS_PROCESSOR;

@Service
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor(access = PRIVATE)
public class ColorDetectionService {

    SettingsService settingsService;

    ScreenCaptureService screenCaptureService;
    ColorProcessorService colorProcessorService;
    ColorChangeService colorChangeService;
    ColorSenderService colorSenderService;

    ScreenShooter screenShooter;
    @NonFinal
    Properties properties;
    @NonFinal
    Dimension screenSize;
    @NonFinal
    Detector detector;
    @NonFinal
    @Getter
    Color currentColor = new Color(0xD0000000, false);

    AtomicBoolean isRunning = new AtomicBoolean(false);
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @NonFinal
    ScheduledFuture<?> futureTask;

    public void start0() {
        applySettings();
        if(isRunning.get()) return;
        isRunning.set(true);
        futureTask = scheduler.scheduleAtFixedRate(()->{

        })
    }

    private void applySettings() {
        this.properties = settingsService.loadOrElseLoadDefault();
        screenCaptureService.applySettings(properties);
        colorProcessorService.applySettings(properties);
        colorChangeService.applySettings(properties);
        colorSenderService.applySettings(properties);
    }

    public void start() {
        log.debug("Starting color detection service");
        this.properties = settingsService.loadOrElseLoadDefault();
        screenCaptureService.applySettings(properties);

        this.screenSize = bindScreenSize();
        this.detector = bindDetector();

        if (isNull(futureTask) || futureTask.isCancelled()) {
            isRunning.set(true);
            futureTask = scheduler.scheduleAtFixedRate(() -> {
                if (isRunning.get()) {
                    Color color = detectColor();
                    if (!ColorChangeMatcher.hasColorChanged(currentColor, color, 50)) return;
                    currentColor = color;
                    sendToServer(color);
                    return;
                }
                futureTask.cancel(false);
            }, 0, 500, TimeUnit.MILLISECONDS);
        }
    }



    private void detectAndSendColor() {
        if (isRunning.get()) {
            var image = screenCaptureService.captureScreen();
            var color = colorProcessorService.detectColor(image);
            if (colorChangeService.hasColorChanged(color))
                colorSenderService.send(color);
            return;
        }
        futureTask.cancel(false);
    }

    private Detector bindDetector() {
        var processor = ProcessorFactory.get(CHESS_PROCESSOR, properties);
        detector = get(AVERAGE_COLOR_DETECTOR, processor);
        return detector;
    }

    private Dimension bindScreenSize() {
        return new Dimension(Integer.parseInt(properties.getProperty("screenHeight")), Integer.parseInt(properties.getProperty("screenWight")));
    }

    public void stop() {
        log.debug("Stopping color detection service");
        isRunning.set(false);
        if (nonNull(futureTask)) futureTask.cancel(false);
    }

    private BufferedImage getScreenshot() {
        return screenShooter.getScreenshot(ScreenTools.createCenteredAreaWithSize(screenSize));
    }

    private Color detectColor() {
        var image = getScreenshot();
        var color = detector.detect(image);
        log.debug("Detected color: {}", color);
        return color;
    }

    private void sendToServer(Color color) {
        // Отправка цвета на сервер
    }


}