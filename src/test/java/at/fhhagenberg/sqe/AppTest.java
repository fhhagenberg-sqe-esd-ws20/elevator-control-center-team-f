package at.fhhagenberg.sqe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import at.fhhagenberg.sqe.elevator.mock.MockElevator;

@ExtendWith(ApplicationExtension.class)
public class AppTest {
    
    private static final Integer ELEVATOR_CAPACITY = 10;
	private static final Integer NUM_ELEVATORS = 2;
	private static final Integer NUM_FLOORS = 3;
	private static final Integer FLOOR_HEIGHT = 5;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) {
        var app = new App();
        app.setElevator(new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY)); // inject mocked elevator
        app.start(stage);
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testButtonWithText(FxRobot robot) {
        //FxAssert.verifyThat(".button", LabeledMatchers.hasText("Click me!"));
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testButtonClick(FxRobot robot) {
        // when:
        //robot.clickOn(".button");

        // or (lookup by css class):
        //FxAssert.verifyThat(".button", LabeledMatchers.hasText("Clicked!"));
    }
}