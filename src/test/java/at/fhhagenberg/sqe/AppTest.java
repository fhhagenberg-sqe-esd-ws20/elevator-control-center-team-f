package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

import java.io.Console;
import java.rmi.RemoteException;

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
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import sqelevator.IElevator;
import at.fhhagenberg.sqe.elevator.mock.ElevatorWrapperTestImpl;
import at.fhhagenberg.sqe.elevator.mock.MockElevator;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel;
import at.fhhagenberg.sqe.elevator.wrappers.IElevatorWrapper;

@ExtendWith(ApplicationExtension.class)
public class AppTest {
    
	private App app;
    private static final Integer ELEVATOR_CAPACITY = 10;
	private static final Integer NUM_ELEVATORS = 3;
	private static final Integer NUM_FLOORS = 10;
	private static final Integer FLOOR_HEIGHT = 5;
	

    private IElevatorWrapper m_Elevator;
    private MockElevator m_Mock;
    
    
    private String ConvertDoorStatusToString(int doorStatus)
    {
    	
    	switch (doorStatus)
    	{
    		case IElevator.ELEVATOR_DOORS_CLOSED:
    			return "Closed";
    		
    		case IElevator.ELEVATOR_DOORS_CLOSING:
    			return "Closing";
    			
    		case IElevator.ELEVATOR_DOORS_OPEN:
    			return "Open";
    			
    		case IElevator.ELEVATOR_DOORS_OPENING:
    			return "Opening";
    			
    			default:
    			  return "Undefined Door State";
    	}
    }
    
    private String ConvertDirectionToString(int dir)
    {
    	
    	switch (dir)
    	{
    		case IElevator.ELEVATOR_DIRECTION_DOWN:
    			return "Down";
    		
    		case IElevator.ELEVATOR_DIRECTION_UP:
    			return "Up";
    			
    		case IElevator.ELEVATOR_DIRECTION_UNCOMMITTED:
    			return "Uncommited";
    			  			
    			default:
    			  return "Undefined Direction";
    	}
    }

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) {
        app = new App();
        m_Mock = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        m_Elevator = new ElevatorWrapperTestImpl(m_Mock);
        app.setElevator(m_Elevator); // inject mocked elevator into main application
        app.start(stage);
    }


    // -- GUI Element IDs:
    // (E->elevatorNum, F->FloorNum)
    //
    // ElevatorPanel: 
    // --------------
    // #upBtnArrow_E_F (polygon)
    // #downBtnArrow_E_F (polygon)
    // #chkServiced_E_F (checkbox)
    // #btnFloor_E_F (button, displays pressed floors in orange)
    // #btnAutomaticMode_E (button)
    // #btnManualMode_E (button)
    // #sliPosition_E (slider)
    // #upArrowComittedDir_E (polygon)
    // #downArrowComittedDir_E (polygon)
    //
    // ElevatorStatusPanel:
    // --------------------
    // #lbStatsWeight_E (label)
    // #lbStatsSpeed_E (label)
    // #lbStatsTarget_E (label)
    // #lbStatsDoor_E (label)
    // #lbStatsDir_E (label)
    // #lbStatsCap_E (label)
    //
    // ElevatorSelectionPanel:
    // -----------------------
    // #m_rbSelectElevator_E (radiobutton)
    //
    // ManualModePanel:
    // ----------------
    // #cbNavigateFloor_E (combobox)
    //
    // ErrorsPanel:
    // ------------
    // #m_taErrorMessage (textarea)
    
    @Test
    public void testElevator0Stats(FxRobot robot) throws Exception {
        robot.clickOn("#m_rbSelectElevator_0");

        verifyThat("#lbStatsWeight_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorWeight(0))));
		verifyThat("#lbStatsSpeed_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorSpeed(0))));
		verifyThat("#lbStatsTarget_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getTarget(0))));
		verifyThat("#lbStatsDoor_0", LabeledMatchers.hasText(ConvertDoorStatusToString(m_Elevator.getElevatorDoorStatus(0))));
        verifyThat("#lbStatsDir_0", LabeledMatchers.hasText(ConvertDirectionToString(m_Elevator.getCommittedDirection(0))));
        verifyThat("#lbStatsCap_0", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorCapacity(0))));
    }

    @Test
    public void testElevator1Stats(FxRobot robot) throws Exception {
        robot.clickOn("#m_rbSelectElevator_1");
        verifyThat("#lbStatsWeight_1", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorWeight(1))));
		verifyThat("#lbStatsSpeed_1", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorSpeed(1))));
		verifyThat("#lbStatsTarget_1", LabeledMatchers.hasText(Integer.toString(m_Elevator.getTarget(1))));
		verifyThat("#lbStatsDoor_0", LabeledMatchers.hasText(ConvertDoorStatusToString(m_Elevator.getElevatorDoorStatus(1))));
        verifyThat("#lbStatsDir_0", LabeledMatchers.hasText(ConvertDirectionToString(m_Elevator.getCommittedDirection(1))));
        verifyThat("#lbStatsCap_1", LabeledMatchers.hasText(Integer.toString(m_Elevator.getElevatorCapacity(1))));
    }
    
    @Test
    public void testSimpleFrontendUpdated(FxRobot robot) throws Exception{
        m_Mock.getElevators().get(0).setServicesFloors(2, false);
        robot.sleep(200); // ==> polling update interval
        verifyThat("#chkServiced_0_2", (CheckBox c) -> !c.isSelected());
        m_Mock.getElevators().get(0).setServicesFloors(2, true);
        robot.sleep(200);
        verifyThat("#chkServiced_0_2", (CheckBox c) -> c.isSelected());
    }
    
    @Test
    public void testFrontendUpdateSetDirection(FxRobot robot) throws Exception{
    	m_Mock.getElevators().get(0).setDirection(IElevatorWrapper.ELEVATOR_DIRECTION_UP);
    	robot.sleep(200);
    	verifyThat("#upArrowComittedDir_0", (Polygon p) -> p.getFill().equals(Color.BLUE));
    	verifyThat("#lbStatsDir_0", LabeledMatchers.hasText(ConvertDirectionToString(IElevatorWrapper.ELEVATOR_DIRECTION_UP)));
      	m_Mock.getElevators().get(0).setDirection(IElevatorWrapper.ELEVATOR_DIRECTION_DOWN);
    	robot.sleep(200);
    	verifyThat("#downArrowComittedDir_0", (Polygon p) -> p.getFill().equals(Color.BLUE));
    	verifyThat("#lbStatsDir_0", LabeledMatchers.hasText(ConvertDirectionToString(IElevatorWrapper.ELEVATOR_DIRECTION_DOWN)));
    }
    
    @Test
    public void testFrontendUpdateSetSpeed(FxRobot robot) throws Exception{
    	m_Mock.getElevators().get(0).setSpeed(-5);
    	
    	// this wait is required due to network delay (polling=200ms)
    	robot.sleep(200);
    	verifyThat("#lbStatsSpeed_0", LabeledMatchers.hasText(Integer.toString(-5)));
    }
    
    @Test
    public void testFrontendTargetFloor(FxRobot robot) throws Exception{
    
    	
    	// this wait is required due to network delay (polling=200ms)
    	m_Mock.getElevators().get(0).setTargetFloor(1);
    	m_Mock.getElevators().get(0).setCurrentFloor(1);
    	robot.sleep(2000);
    
    	verifyThat("#sliPosition_0", (Slider s) -> s.getValue() ==((double)1));
    }
    
    @Test
    public void testErrorString(FxRobot robot) throws Exception{
    	
    	verifyThat("#m_taErrorMessage", (TextArea t) -> t.getText().equals(""));
    	app.getController().getBuilding().setError("Test Error Occured");
    	robot.sleep(200);

    	verifyThat("#m_taErrorMessage", (TextArea t) -> t.getText().equals("\nTest Error Occured"));
    }

    @Test
    public void testSimpleBackendUpdated(FxRobot robot) throws Exception{
        robot.clickOn("#chkServiced_0_3");
        assertFalse(m_Mock.getElevators().get(0).getServicesFloors(3));
        robot.clickOn("#chkServiced_0_3");
        assertTrue(m_Mock.getElevators().get(0).getServicesFloors(3));
    }
    
    public void testBackendUpdatedOn(FxRobot robot) throws Exception{
        robot.clickOn("#chkServiced_0_3");
        assertFalse(m_Mock.getElevators().get(0).getServicesFloors(3));
        robot.clickOn("#chkServiced_0_3");
        assertTrue(m_Mock.getElevators().get(0).getServicesFloors(3));
    }
    
   
}