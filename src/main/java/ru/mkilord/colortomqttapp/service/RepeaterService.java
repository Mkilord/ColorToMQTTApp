package ru.mkilord.colortomqttapp.service;

import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.config.BindSettings;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE)
public final class RepeaterService implements BindSettings {

    final AtomicBoolean isRunning = new AtomicBoolean(false);
    final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    ScheduledFuture<?> futureTask;
    int updatePeriod;

    @Override
    public void applySettings(Properties props) {
        this.updatePeriod = Integer.parseInt(props.getProperty("updatePeriod"));
    }

    public void repeat(Runnable runnable) {
        if (isRunning.get()) return;
        if (isNull(futureTask) || futureTask.isCancelled()) {
            isRunning.set(true);
            futureTask = scheduler.scheduleAtFixedRate(() -> {
                if (isRunning.get()) {
                    runnable.run();
                    return;
                }
                futureTask.cancel(false);
            }, 0, updatePeriod, TimeUnit.MILLISECONDS);
        }
    }

    public void stop() {
        isRunning.set(false);
        if (nonNull(futureTask)) futureTask.cancel(false);
        scheduler.shutdown();
    }
}
