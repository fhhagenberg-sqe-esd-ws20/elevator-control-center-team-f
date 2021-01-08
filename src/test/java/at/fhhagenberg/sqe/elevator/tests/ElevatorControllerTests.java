/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.elevator.controller.ElevatorControllerImpl;
import at.fhhagenberg.sqe.elevator.controller.IElevatorController;
import at.fhhagenberg.sqe.elevator.mock.MockElevator;
import at.fhhagenberg.sqe.elevator.model.BuildingModelImpl;
import at.fhhagenberg.sqe.elevator.model.ElevatorModelImpl;
import at.fhhagenberg.sqe.elevator.model.FloorModelImpl;
import at.fhhagenberg.sqe.elevator.model.IBuildingModel;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel.CommitedDirection;
import at.fhhagenberg.sqe.elevator.wrappers.ElevatorWrapperImpl;
import at.fhhagenberg.sqe.elevator.wrappers.IElevatorWrapper;
import sqelevator.IElevator;


public class ElevatorControllerTests {

    private static final Integer ELEVATOR_CAPACITY = 10;
	private static final Integer NUM_ELEVATORS = 2;
	private static final Integer NUM_FLOORS = 3;
	private static final Integer FLOOR_HEIGHT = 5;

    @Test
    void testSetCommitedDir() throws Exception {
        IElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperImpl(e);
        IElevatorController c = new ElevatorControllerImpl(w, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl());

        c.setCommittedDirection(0, 2);
        assertEquals(2, e.getCommittedDirection(0));
        c.setCommittedDirection(0, 1);
        assertEquals(1, e.getCommittedDirection(0));
    }

    @Test
    void testServicesFloor() throws Exception {
        IElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperImpl(e);
        IElevatorController c = new ElevatorControllerImpl(w, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl());

        c.setServicesFloors(0, 1, true);
        assertTrue(e.getServicesFloors(0, 1));
        c.setServicesFloors(0, 1, false);
        assertFalse(e.getServicesFloors(0, 1));
    }

    @Test
    void testSetTarget() throws Exception {
        IElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperImpl(e);
        IElevatorController c = new ElevatorControllerImpl(w, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl());

        c.setTarget(0, 1);
        assertEquals(1, e.getTarget(0));
        c.setTarget(0, 2);
        assertEquals(2, e.getTarget(0));
    }

    @Test
    void testGetBuilding() throws Exception {
        IElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperImpl(e);
        IBuildingModel b = new BuildingModelImpl(1,1);
        IElevatorController c = new ElevatorControllerImpl(w, b, new ElevatorModelImpl(), new FloorModelImpl());
        assertNotEquals(null, c.getBuilding());
    }

    @Test
    void testSetAutomaticMode() throws Exception {
        IElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperImpl(e);
        IBuildingModel b = new BuildingModelImpl(1,1);
        IElevatorController c = new ElevatorControllerImpl(w, b, new ElevatorModelImpl(), new FloorModelImpl());

        IElevatorModel eimp =  c.getBuilding().getElevators().get(0);
        c.setAutomaticMode(0, true);
        assertTrue(eimp.getAutomaticMode());
        c.setAutomaticMode(0, false);
        assertFalse(eimp.getAutomaticMode());
    }

}