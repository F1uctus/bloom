import com.f1uctus.bloom.application.controllers.welcome.WelcomeController;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class WelcomeControllerTests extends TestFXBase {
    @Override public void start(Stage stage) {
        loadController(stage, WelcomeController.class);
    }

    @Test
    void view_should_contain_application_header() {
        verifyThat(".label", hasText("Bloom"));
    }

    @Test
    void view_should_contain_speech_plugin_auth_button() {
        verifyThat(".toggle-button", hasText("Speech"));
    }
}
