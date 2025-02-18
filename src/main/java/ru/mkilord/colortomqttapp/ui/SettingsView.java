package ru.mkilord.colortomqttapp.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("settings")
public class SettingsView extends VerticalLayout {
    public SettingsView() {
        add(new H1("Hi this settings tab"));
        add(new Button("Click me to go to back", e -> UI.getCurrent().navigate("home")));
    }
}
