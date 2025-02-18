package ru.mkilord.colortomqttapp.processor;

import java.util.function.BiConsumer;

public class ChessProcessor extends Processor {

    public ChessProcessor(int frameSize) {
        super(frameSize);
    }

    @Override
    public void process(int width, int height, BiConsumer<Integer, Integer> action) {
        for (int y = 0; y < height; y += frameSize) {
            int startX = (y / frameSize) % 2 == 0 ? frameSize : 0;
            for (int x = startX; x < width; x += frameSize * 2) {
                action.accept(x, y);
            }
        }
    }
}
