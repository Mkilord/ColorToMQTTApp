package ru.mkilord.colortomqttapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mkilord.colortomqttapp.params.BoolParam;
import ru.mkilord.colortomqttapp.params.IntParam;
import ru.mkilord.colortomqttapp.params.Param;

import java.util.Set;


@Configuration
public class DefaultSettings {
    @Bean
    public Set<Param<?>> defaultSettings() {
        return Set.of(
                BoolParam.builder().key("key").desc("").value(true).build(),
                IntParam.builder().key("key").value(10).verify(str -> isPositiveInteger(str) && isNumberInRange(str, 0, 10)).build()
                );
    }

    private boolean isNumberInRange(String str, int min, int max) {
        var num = Integer.parseInt(str);
        return num >= min && num <= max;
    }

    private boolean isPositiveInteger(String str) {
        return str != null && str.matches("\\d+");
    }
}
