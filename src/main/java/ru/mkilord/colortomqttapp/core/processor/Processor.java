package ru.mkilord.colortomqttapp.core.processor;

import java.util.function.BiConsumer;

public interface Processor {
    void process(int width, int height, BiConsumer<Integer, Integer> action);
}
