package ru.mkilord.colortomqttapp.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;
import ru.mkilord.colortomqttapp.settings.SettingsService;

@Log4j2
@Route("home")
public class MainView extends VerticalLayout {
    public MainView(SettingsService settingsService) {
        UI.getCurrent().getPage().setTitle("ColorToMQTTApp");
        log.debug("Setting up UI with: {}", settingsService.getSetting("clientId"));
        var headerLayout = new HorizontalLayout();
        headerLayout.addClassName("headerLayout");
        var appName = new H1("ColorToMQTTApp");
        var settingsButton = new Button(VaadinIcon.SERVER.create(),
                buttonClickEvent -> UI.getCurrent().navigate(SettingsView.class));
        headerLayout.add(appName, settingsButton);

        var nameField = new TextField("Введите имя");
        var leftText = new TextField("Left Text");
        nameField.setWidth("100%");
        var rightText = new TextField("Right Text");
        var horLayout = new HorizontalLayout(leftText, rightText);

        FormLayout formLayout = new FormLayout();

        TextField firstName = new TextField("Имя");
        TextField lastName = new TextField("Фамилия");
        EmailField email = new EmailField("Email");

        formLayout.add(firstName, lastName, email);

        var button = new Button("Поздороваться", (buttonClickEvent -> {
            String name = nameField.getValue();
            Notification.show("Привет, " + name + "!");
        }));
        add(headerLayout, nameField, button, horLayout, formLayout);
    }
}
