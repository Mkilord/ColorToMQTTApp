package ru.mkilord.colortomqttapp.settings;

import java.util.Collections;
import java.util.Map;

public record Settings(Map<String, String> params) {

    @Override
    public Map<String, String> params() {
        return Collections.unmodifiableMap(params);
    }

    public void putSetting(String key, String value) {
        params.put(key, value);
    }

    public String getSetting(String key) {
        return params.get(key);
    }
}
