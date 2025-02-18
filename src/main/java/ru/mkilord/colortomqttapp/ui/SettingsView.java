package ru.mkilord.colortomqttapp.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.mkilord.colortomqttapp.config.SettingsConfig;
import ru.mkilord.colortomqttapp.settings.SettingsService;

import java.util.Map;

@Route("settings")
public class SettingsView extends VerticalLayout {
    public SettingsView(SettingsService settingsService, SettingsConfig settingsConfig) {

        add(createHeader());

        var settings = settingsService.getSettings();
        var settingsLayout = new VerticalLayout(getItems(settings.params()));

        add(settingsLayout);
        add(restoreButton(settingsLayout, settingsConfig));
    }

    private Button restoreButton(VerticalLayout settingsLayout, SettingsConfig settingsConfig) {
        return new Button("Restore settings",
                event -> openDialog(settingsLayout, settingsConfig));
    }

    private HorizontalLayout createHeader() {
        var headerLayout = new HorizontalLayout();
        var title = new H1("Settings");

        var backButton = new Button("Back", VaadinIcon.ARROW_LEFT.create(),
                e -> UI.getCurrent().navigate("home"));

        headerLayout.add(backButton, title);

        return headerLayout;
    }

    private void openDialog(VerticalLayout settingsLayout, SettingsConfig settingsConfig) {
        Dialog dialog = new Dialog();
        dialog.setModal(true);
        dialog.setCloseOnEsc(true);
//        dialog.setCloseOnOutsideClick(false); // Запрет закрытия кликом вне окна

        VerticalLayout content = new VerticalLayout();
        content.add(new NativeLabel("Восстановить настройки по умолчанию?"));

        var buttonsLayout = new HorizontalLayout();
        Button closeButton = new Button("Нет", e -> dialog.close());
        Button acceptButton = new Button("Да", e -> {
            settingsLayout.removeAll();
            settingsLayout.add(getItems(settingsConfig.getSettings()));
            dialog.close();
        });
        buttonsLayout.add(closeButton, acceptButton);

        dialog.add(content, buttonsLayout);
        dialog.open(); // Открываем диалог
    }

    private Component createItem(String name, String value) {

        var tfName = new TextField();
        tfName.setValue(name);
        tfName.setReadOnly(true);

        var tfValue = new TextField();
        tfValue.setValue(value);
        tfValue.setReadOnly(true);

        var acceptButton = new Button("Accept");
        acceptButton.setVisible(false);
        acceptButton.addClickListener(e -> {
            acceptButton.setVisible(false);
            tfValue.setReadOnly(true);
            Notification.show("Param was changed!");
        });

        var changeButton = new Button();
        changeButton.setIcon(VaadinIcon.EDIT.create());
        changeButton.addClickListener(e -> {
            acceptButton.setVisible(true);
            tfValue.setReadOnly(false);
        });

        return new HorizontalLayout(tfName, tfValue, changeButton, acceptButton);
    }

    private Component[] getItems(Map<String, String> settings) {
        return settings
                .entrySet()
                .stream()
                .map(entry -> createItem(entry.getKey(), entry.getValue()))
                .toArray(Component[]::new);
    }
}
