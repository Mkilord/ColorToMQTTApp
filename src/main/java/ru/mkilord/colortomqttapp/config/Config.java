package ru.mkilord.colortomqttapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mkilord.colortomqttapp.core.screenshoter.ScreenShooter;

import java.awt.*;

@Configuration
public class Config {
    @Bean
    public ScreenShooter screenShooter() throws AWTException {
        return new ScreenShooter(new Robot());
    }
}
