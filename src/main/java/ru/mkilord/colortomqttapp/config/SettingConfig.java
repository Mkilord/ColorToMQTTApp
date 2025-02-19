package ru.mkilord.colortomqttapp.config;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mkilord.colortomqttapp.params.Param;

import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;


@Getter
@Configuration
public class SettingConfig {

    String settingsFilePath = Path.of(".").toAbsolutePath().resolve("settings.txt").normalize().toString();

    @Bean
    public Map<String, Param> getDefaultSettings() {
        return Stream.of(
                Param.builder().key("key1").value("dfjdk").desc("fdkjfk").build(),
                Param.builder().key("key2").value("2").build(),
                Param.builder().key("key3").value("true").build(),
                Param.builder().key("key4").value("dfjdk").desc("fdkjfk").build()
        ).collect(toMap(Param::key, identity()));
    }
}
