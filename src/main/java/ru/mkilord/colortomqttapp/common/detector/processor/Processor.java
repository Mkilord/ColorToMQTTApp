package ru.mkilord.colortomqttapp.common.detector.processor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;

@RequiredArgsConstructor
@Getter
public abstract class Processor {
    protected final int frameSize;
    public abstract void process(int width, int height, BiConsumer<Integer, Integer> action);
}
