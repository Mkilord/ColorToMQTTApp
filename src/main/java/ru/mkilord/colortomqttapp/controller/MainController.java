package ru.mkilord.colortomqttapp.controller;

import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequestMapping("/")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MainController {
    @GetMapping
    public String index(Model model) {
        model.addAttribute("color", "#FFFFFF");
        return "index";
    }

    @ResponseBody
    @PostMapping("/start")
    public String startColorDetection() {
        return "startColorDetection";
    }

    @ResponseBody
    @PostMapping("/stop")
    public String stopColorDetection() {
        return "stopColorDetection";
    }

    @GetMapping("/color")
    @ResponseBody
    public String getColor() {
        return "#111111";
    }
}
