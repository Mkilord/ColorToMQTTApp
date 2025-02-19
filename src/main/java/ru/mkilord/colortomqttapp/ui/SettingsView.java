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
import ru.mkilord.colortomqttapp.params.Param;
import ru.mkilord.colortomqttapp.settings.Settings;
import ru.mkilord.colortomqttapp.settings.SettingsService;

import java.util.Map;

@Route("settings")
public class SettingsView extends VerticalLayout {

    SettingsService settingsService;
    Settings settings;

    Button saveButton;

    public SettingsView(SettingsService settingsService) {
        this.settingsService = settingsService;
        this.settings = settingsService.getSettings();

        bindView();
    }

    private void bindView() {
        add(createHeader());

        var settingsLayout = new VerticalLayout(getItems(settings.params()));

        add(settingsLayout);
        add(restoreButton(settingsLayout));
        Button saveButton = new Button("Save");
        saveButton.setVisible(true);
        saveButton.addClickListener(e -> {
            settingsService.save();
            saveButton.setVisible(false);
            Notification.show("Settings saved");
        });
        add(saveButton);
    }

    private void showSaveButton() {
        saveButton.setVisible(true);

    }


    private Button restoreButton(VerticalLayout settingsLayout) {
        return new Button("Restore settings",
                event -> openDialog(settingsLayout));
    }

    private HorizontalLayout createHeader() {
        var headerLayout = new HorizontalLayout();
        var title = new H1("Settings");

        var backButton = new Button("Back", VaadinIcon.ARROW_LEFT.create(),
                e -> UI.getCurrent().navigate("home"));

        headerLayout.add(backButton, title);

        return headerLayout;
    }

    private void openDialog(VerticalLayout settingsLayout) {
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
            settingsService.restoreToDefaultSettingsFromConfig();
            settings = settingsService.getSettings();
            settingsLayout.add(getItems(settings.params()));
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

    private Component[] getItems(Map<String, Param> settings) {
//        return settings
//                .values()
//                .stream()
//                .map(this::getItem)
//                .toArray(Component[]::new);
        return new Component[] {};
    }

//    private Component getItem(Param param) {
//        var content = new HorizontalLayout();
//        Component component = null;
//        if (param instanceof BoolParam boolParam)
//            component = getBoolItem(boolParam);
//        else if (param instanceof IntParam intParam)
//            component = getIntItem(intParam);
//        else if (param instanceof StrParam strParam)
//            component = getStrItem(strParam);
//
//        content.add(component);
//
//        content.add(new Button(VaadinIcon.INFO.create(), buttonClickEvent -> Notification.show(param.getDesc())));
//
//        return content;
//    }
//
//    private Component getStrItem(StrParam param) {
//        var content = getItemContentLayoutWithName(param.getKey());
//        var text = new TextField();
//        text.setValue(param.getValue());
//        text.addValueChangeListener(event -> {
//            String value = event.getValue();
//            var updateParam = StrParam.getNewWithValue(param, value);
//            settings.updateSetting(updateParam);
//            showSaveButton();
//        });
//        content.add(text);
//        return content;
//    }


//    private Component getIntItem(IntParam param) {
//        var content = getItemContentLayoutWithName(param.getKey());
//        var numberField = new NumberField();
//        if (Objects.nonNull(param.getMin()))
//            numberField.setMin(param.getMin());
//        if (Objects.nonNull(param.getMax()))
//            numberField.setMax(param.getMax());
//        numberField.setStep(1);
//        numberField.setValue(Double.valueOf(param.getValue()));
//        numberField.addValueChangeListener(event -> {
//            String value = String.valueOf(event.getValue());
//            var updateParam = IntParam.getNewWithValue(param, value);
//            settings.updateSetting(updateParam);
//            showSaveButton();
//        });
//        content.add(numberField);
//        return content;
//    }

    private HorizontalLayout getItemContentLayoutWithName(String name) {
        return new HorizontalLayout(new NativeLabel(name));
    }

//    private Component getBoolItem(BoolParam param) {
//        var content = getItemContentLayoutWithName(param.getKey());
//        var comboBox = new ComboBox<>();
//        comboBox.setItems("true", "false");
//        comboBox.setValue("true");
//        comboBox.addValueChangeListener(event -> {
//            String value = (String) event.getValue();
//            var updatedParam = BoolParam.getNewWithValue(param, value);
//            settings.updateSetting(updatedParam);
//            showSaveButton();
//        });
//        content.add(comboBox);
//        return content;
//    }
}
