package ru.mkilord.colortomqttapp.core.processor;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.function.BiConsumer;

import static lombok.AccessLevel.PRIVATE;

@Setter
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ChessProcessor extends Processor {

    int frameSize;

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
