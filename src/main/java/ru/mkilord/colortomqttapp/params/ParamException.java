package ru.mkilord.colortomqttapp.params;

public class ParamException extends Exception {
    public ParamException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}