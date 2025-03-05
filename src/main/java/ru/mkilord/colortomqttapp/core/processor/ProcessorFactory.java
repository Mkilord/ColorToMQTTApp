package ru.mkilord.colortomqttapp.core.processor;

import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class ProcessorFactory {
    public static final String CHESS_PROCESSOR = "chessProcessor";

    public static Processor get(String processorName, Properties properties) {
        if (processorName.equals(CHESS_PROCESSOR))
            return new ChessProcessor(Integer.parseInt(properties.getProperty("cellSize")));
        throw new IllegalArgumentException("Unknown processor: " + processorName);
    }
}
