package ru.mkilord.colortomqttapp.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.common.detector.Detector;
import ru.mkilord.colortomqttapp.common.detector.processor.factory.ProcessorFactory;
import ru.mkilord.colortomqttapp.common.screenshoter.ScreenShooter;
import ru.mkilord.colortomqttapp.common.screenshoter.ScreenTools;

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
import static ru.mkilord.colortomqttapp.common.detector.factory.DetectorFactory.AVERAGE_COLOR_DETECTOR;
import static ru.mkilord.colortomqttapp.common.detector.factory.DetectorFactory.get;
import static ru.mkilord.colortomqttapp.common.detector.processor.factory.ProcessorFactory.CHESS_PROCESSOR;

@Service
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor(access = PRIVATE)
public class ColorDetectionService {

    ScreenShooter screenShooter;
    SettingsService settingsService;
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

    public void start() {
        this.properties = settingsService.loadOrElseLoadDefault();
        this.screenSize = bindScreenSize();
        this.detector = bindDetector();

        if (isNull(futureTask) || futureTask.isCancelled()) {
            isRunning.set(true);
            futureTask = scheduler.scheduleAtFixedRate(() -> {
                if (isRunning.get()) {
                    Color color = detectColor();
                    currentColor = color;
                    sendToServer(color);
                    return;
                }
                futureTask.cancel(false);
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    private Detector bindDetector() {
        var processor = ProcessorFactory.get(CHESS_PROCESSOR, 20);
        detector = get(AVERAGE_COLOR_DETECTOR, processor);
        return detector;
    }

    private Dimension bindScreenSize() {
        return new Dimension(Integer.parseInt(properties.getProperty("screenHeight")), Integer.parseInt(properties.getProperty("screenWight")));
    }

    public void stop() {
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