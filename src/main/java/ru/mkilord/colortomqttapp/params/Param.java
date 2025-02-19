package ru.mkilord.colortomqttapp.params;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record Param(String key, String value, String desc) {
    public ParamBuilder copyBuilder() {
        return Param.builder()
                .key(this.key)
                .value(this.value)
                .desc(this.desc);
    }
}
