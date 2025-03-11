package ru.mkilord.colortomqttapp.service.impl;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mkilord.colortomqttapp.service.RepeaterService;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@FieldDefaults(level = PRIVATE)
public final class RepeatServiceImpl implements RepeaterService {

    final AtomicBoolean isRunning = new AtomicBoolean(false);
    final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    ScheduledFuture<?> futureTask;
    final int updatePeriod;

    public RepeatServiceImpl(Properties properties) {
        this.updatePeriod = Integer.parseInt(properties.getProperty("updatePeriod"));
    }
    public boolean isRunning() {
        return isRunning.get();
    }

    @Override
    public void repeat(Runnable runnable) {
        if (isRunning.get()) return;
        if (isNull(futureTask) || futureTask.isCancelled()) {
            isRunning.set(true);
            futureTask = scheduler.scheduleAtFixedRate(()-> repeatRunnable(runnable), 0, updatePeriod, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void stop() {
        isRunning.set(false);
        if (nonNull(futureTask)) futureTask.cancel(false);
    }

    private void repeatRunnable(Runnable runnable) {
        if (isRunning.get()) {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            return;
        }
        futureTask.cancel(false);
    }
}
