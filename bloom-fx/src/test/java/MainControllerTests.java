import com.f1uctus.bloom.application.controllers.main.MainController;
import com.f1uctus.bloom.application.controllers.main.MainController.Fields;
import com.f1uctus.bloom.core.events.LoginEvent;
import com.f1uctus.bloom.core.persistence.repositories.UserRepository;
import com.f1uctus.bloom.plugins.coreinterface.events.Event;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testfx.api.FxRobot;

import static javafx.scene.input.MouseButton.PRIMARY;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.WindowMatchers.isShowing;
import static org.testfx.matcher.control.TableViewMatchers.hasNumRows;
import static org.testfx.matcher.control.TableViewMatchers.hasTableCell;

public class MainControllerTests extends TestFXBase {
    @Autowired
    UserRepository users;

    @Override public void start(Stage stage) {
        context.publishEvent(new LoginEvent(users.findAll().get(0)));

        loadController(stage, MainController.class);
    }

    @Test
    void view_contains_basic_controls() {
        verifyThat("#" + Fields.commandBox, Node::isVisible);
        verifyThat("#" + Fields.logArea, Node::isVisible);
    }

    @Test
    void new_events_appear_in_log_view() {
        final var bogus = "Test event";
        context.publishEvent(new Event() {
            @Override public String toString() {
                return bogus;
            }
        });
        verifyThat("#logArea", hasNumRows(1));
        verifyThat("#logArea", hasTableCell("Received an event: " + bogus));
    }

    @Test
    void workflows_window_opens(FxRobot robot) {
        robot.clickOn("#edit-menu", PRIMARY);
        robot.clickOn("#workflows-menu-item", PRIMARY);
        verifyThat(window("Workflows"), isShowing());
    }

    @Test
    void plugins_window_opens(FxRobot robot) {
        robot.clickOn("#edit-menu", PRIMARY);
        robot.clickOn("#plugins-menu-item", PRIMARY);
        verifyThat(window("Settings"), isShowing());
    }
}
