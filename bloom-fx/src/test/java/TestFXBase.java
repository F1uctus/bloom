import com.f1uctus.bloom.application.JavaFxWeaverApplication;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.concurrent.TimeoutException;

@SpringBootTest(classes = JavaFxWeaverApplication.class)
@ExtendWith({ ApplicationExtension.class })
public abstract class TestFXBase extends ApplicationTest {
    @Autowired
    protected ConfigurableApplicationContext context;

    @BeforeAll
    public static void setupHeadlessMode() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
    }

    protected void loadController(Stage stage, Class<?> controllerClass) {
        var fxw = new FxWeaver(context::getBean, context::close);
        var cav = fxw.load(controllerClass);
        stage.setScene(new Scene((Parent) cav.getView().orElseThrow()));
        stage.show();
    }

    @AfterEach
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[0]);
        release(new MouseButton[0]);
    }

    @SuppressWarnings("unchecked")
    public <T extends Node> T find(String query, Class<T> clazz) {
        return (T) lookup(query).queryAll().iterator().next();
    }
}