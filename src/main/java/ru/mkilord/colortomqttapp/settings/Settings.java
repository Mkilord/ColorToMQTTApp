package ru.mkilord.colortomqttapp.settings;

import ru.mkilord.colortomqttapp.params.Param;

import java.util.Collections;
import java.util.Map;

public record Settings(Map<String, Param> params) {

    @Override
    public Map<String, Param> params() {
        return Collections.unmodifiableMap(params);
    }

    public void updateSetting(Param updatedParam) {
        params.put(updatedParam.key(), updatedParam);
    }

    public Param getSetting(String key) {
        return params.get(key);
    }
}
