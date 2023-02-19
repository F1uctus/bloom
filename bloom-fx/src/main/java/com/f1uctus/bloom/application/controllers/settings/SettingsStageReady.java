package com.f1uctus.bloom.application.controllers.settings;

import com.f1uctus.bloom.application.controllers.StageReadyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SettingsStageReady implements StageReadyEvent<SettingsController> {
    Class<SettingsController> controllerClass = SettingsController.class;
    Stage stage;
    SettingsController.Tab tab;

    public SettingsStageReady() {
        this(SettingsController.Tab.MAIN);
    }

    public SettingsStageReady(SettingsController.Tab tab) {
        stage = new Stage();
        stage.setTitle("Settings");
        stage.initModality(Modality.APPLICATION_MODAL);
        this.tab = tab;
    }

    @Override
    public void setupController(SettingsController controller) {
        controller.setTab(tab);
        controller.afterSetup();
    }
}
