package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import sqelevator.IElevator;
import at.fhhagenberg.sqe.elevator.mock.MockElevator;
import at.fhhagenberg.sqe.elevator.wrappers.ElevatorWrapperImpl;
import at.fhhagenberg.sqe.elevator.wrappers.IElevatorWrapper;

@ExtendWith(ApplicationExtension.class)
public class AppTest {
    
    private static final Integer ELEVATOR_CAPACITY = 10;
	private static final Integer NUM_ELEVATORS = 3;
	private static final Integer NUM_FLOORS = 10;
	private static final Integer FLOOR_HEIGHT = 5;

    private IElevatorWrapper m_Elevator;
    private MockElevator m_Mock;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) {
        var app = new App();
        m_Mock = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        m_Elevator = new ElevatorWrapperImpl(m_Mock);
        app.setElevator(m_Elevator); // inject mocked elevator into main application
        app.start(stage);
    }


    @Test
    public void testElevator0Stats(FxRobot robot) throws Exception {
        robot.clickOn("#m_rbSelectElevator_0");

        verifyThat("#lbStatsWeight_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorWeight(0))));
		verifyThat("#lbStatsSpeed_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorSpeed(0))));
		verifyThat("#lbStatsTarget_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getTarget(0))));
		//verifyThat("#lbStatsDoor_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorWeight(0))));
        //verifyThat("#lbStatsDir_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorWeight(0))));
        verifyThat("#lbStatsCap_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorCapacity(0))));
    }

    @Test
    public void testElevator1Stats(FxRobot robot) throws Exception {
        robot.clickOn("#m_rbSelectElevator_1");
        verifyThat("#lbStatsWeight_1", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorWeight(1))));
		verifyThat("#lbStatsSpeed_1", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorSpeed(1))));
		verifyThat("#lbStatsTarget_1", LabeledMatchers.hasText(Integer.toString(m_Elevator.getTarget(1))));
		//verifyThat("#lbStatsDoor_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorWeight(0))));
        //verifyThat("#lbStatsDir_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorWeight(0))));
        verifyThat("#lbStatsCap_1", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorCapacity(1))));
    }

    @Test
    public void testToTestStuffRemoveMe(FxRobot robot) throws Exception {
        m_Mock.getElevators().get(0).setFloorButtonActive(3, true);
        m_Mock.getElevators().get(1).setFloorButtonActive(2, true);
        m_Mock.getFloors().get(0).setDownButtonActive(true);
        m_Mock.getFloors().get(4).setUpButtonActive(true);
        robot.sleep(1000;
        m_Mock.getElevators().get(0).setFloorButtonActive(3, false);
        m_Mock.getElevators().get(1).setFloorButtonActive(2, false);
        m_Mock.getFloors().get(0).setDownButtonActive(false);
        m_Mock.getFloors().get(4).setUpButtonActive(false);
        robot.sleep(1000);
      //  robot.clickOn("#cbNavigateFloor_1");
    }

    @Test
    public void testSimpleBackendFrontend(FxRobot robot) throws Exception{
        m_Mock.getElevators().get(0).setServicesFloors(2, false);
        robot.sleep(200); // ==> polling update interval
        verifyThat("#chkServiced_0_2", (CheckBox c) -> !c.isSelected());
        m_Mock.getElevators().get(0).setServicesFloors(2, true);
        robot.sleep(200);
        verifyThat("#chkServiced_0_2", (CheckBox c) -> c.isSelected());
    }

    @Test
    public void testSimpleFrontendBackend(FxRobot robot) throws Exception{
        robot.clickOn("#chkServiced_0_3");
        assertFalse(m_Mock.getElevators().get(0).getServicesFloors(3));
        robot.clickOn("#chkServiced_0_3");
        assertTrue(m_Mock.getElevators().get(0).getServicesFloors(3));
    }
}