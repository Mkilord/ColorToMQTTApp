package ru.mkilord.colortomqttapp.core.processor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;

@RequiredArgsConstructor
@Getter
public abstract class Processor {
    public abstract void process(int width, int height, BiConsumer<Integer, Integer> action);
}
