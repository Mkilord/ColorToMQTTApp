package ru.mkilord.colortomqttapp.controller;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequestMapping("/settings")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public class SettingsController {
    Properties prop;

    @GetMapping
    public String settingsPage(Model model) {
        model.addAttribute("mqttServer", prop.get("text"));
        model.addAttribute("interval", prop.get("maxHSB"));
        return "settings";
    }

    @PostMapping
    public String updateSettings(@RequestParam String mqttServer, @RequestParam int interval) {
//        this.mqttServer = mqttServer;
//        this.interval = interval;
        return "redirect:/settings";
    }
}
