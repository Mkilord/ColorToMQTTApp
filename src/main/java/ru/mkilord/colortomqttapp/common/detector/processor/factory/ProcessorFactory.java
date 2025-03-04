package ru.mkilord.colortomqttapp.common.detector.processor.factory;

import org.springframework.stereotype.Component;
import ru.mkilord.colortomqttapp.common.detector.processor.ChessProcessor;
import ru.mkilord.colortomqttapp.common.detector.processor.Processor;

@Component
public class ProcessorFactory {
    public static final String CHESS_PROCESSOR = "chessProcessor";

    public static Processor get(String processorName, int frameSize) {
        if (processorName.equals(CHESS_PROCESSOR)) {
            return new ChessProcessor(frameSize);
        }
        throw new IllegalArgumentException("Unknown processor: " + processorName);
    }
}
