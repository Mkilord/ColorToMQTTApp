package ru.mkilord.colortomqttapp.params;

public interface Verify {
    boolean verify(String value) throws ParamException;
}
