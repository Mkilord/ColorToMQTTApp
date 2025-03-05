package ru.mkilord.colortomqttapp.service;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RepeaterService {

    AtomicBoolean isRunning = new AtomicBoolean(false);
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    @NonFinal
    ScheduledFuture<?> futureTask;

    public void start(Runnable runnable) {
        if (isRunning.get()) return;
        if (isNull(futureTask) || futureTask.isCancelled()) {
            isRunning.set(true);
            futureTask = scheduler.scheduleAtFixedRate(() -> {
                if (isRunning.get()) {
                    runnable.run();
                    return;
                }
                futureTask.cancel(false);
            }, 0, 500, TimeUnit.MILLISECONDS);
        }
    }

    public void stop() {
        isRunning.set(false);
        if (nonNull(futureTask)) futureTask.cancel(false);
        scheduler.shutdown();
    }

}
