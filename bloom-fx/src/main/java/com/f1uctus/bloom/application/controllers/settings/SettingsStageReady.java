package com.f1uctus.bloom.application.controllers.settings;

import com.f1uctus.bloom.application.controllers.StageReadyEvent;
import com.f1uctus.bloom.core.persistence.models.User;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SettingsStageReady implements StageReadyEvent<SettingsController> {
    Class<SettingsController> controllerClass = SettingsController.class;
    Stage stage;
    User user;
    SettingsController.Tab tab;

    public SettingsStageReady(User user) {
        this(user, SettingsController.Tab.MAIN);
    }

    public SettingsStageReady(User user, SettingsController.Tab tab) {
        stage = new Stage();
        stage.setTitle("Settings");
        stage.initModality(Modality.APPLICATION_MODAL);
        this.user = user;
        this.tab = tab;
    }

    @Override public void setupController(SettingsController controller) {
        controller.setTab(tab);
        controller.afterSetup();
    }
}
