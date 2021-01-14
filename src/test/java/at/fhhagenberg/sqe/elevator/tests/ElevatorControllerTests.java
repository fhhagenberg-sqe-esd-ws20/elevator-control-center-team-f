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
import at.fhhagenberg.sqe.elevator.mock.ElevatorWrapperTestImpl;
import at.fhhagenberg.sqe.elevator.mock.MockElevator;
import at.fhhagenberg.sqe.elevator.model.BuildingModelImpl;
import at.fhhagenberg.sqe.elevator.model.ElevatorModelImpl;
import at.fhhagenberg.sqe.elevator.model.FloorModelImpl;
import at.fhhagenberg.sqe.elevator.model.IBuildingModel;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel;
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
        IElevatorWrapper w = new ElevatorWrapperTestImpl(e);
        IElevatorController c = new ElevatorControllerImpl(w, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl());

        c.setCommittedDirection(0, 2);
        assertEquals(2, e.getCommittedDirection(0));
        c.setCommittedDirection(0, 1);
        assertEquals(1, e.getCommittedDirection(0));
    }

    @Test
    void testSetCommitedDirException() throws Exception {
        MockElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperTestImpl(e);
        IElevatorController c = new ElevatorControllerImpl(w, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl());
        e.setShouldThrow(true);

        c.setCommittedDirection(0, 2);

        assertEquals("setCommittedDirection", c.getBuilding().getError());
        assertEquals(false, c.getBuilding().getConnectionState());
    }

    @Test
    void testServicesFloor() throws Exception {
        IElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperTestImpl(e);
        IElevatorController c = new ElevatorControllerImpl(w, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl());

        c.setServicesFloors(0, 1, true);
        assertTrue(e.getServicesFloors(0, 1));
        c.setServicesFloors(0, 1, false);
        assertFalse(e.getServicesFloors(0, 1));
    }

    @Test
    void testServicesFloorException() throws Exception {
        MockElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperTestImpl(e);
        IElevatorController c = new ElevatorControllerImpl(w, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl());
        e.setShouldThrow(true);

        c.setServicesFloors(0, 1, true);

        assertEquals("setServicesFloors", c.getBuilding().getError());
        assertEquals(false, c.getBuilding().getConnectionState());
    }

    @Test
    void testSetTarget() throws Exception {
        IElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperTestImpl(e);
        IElevatorController c = new ElevatorControllerImpl(w, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl());
        
        c.setTarget(0, 1);
        assertEquals(1, e.getTarget(0));
        c.setTarget(0, 2);
        assertEquals(2, e.getTarget(0));

        c.getBuilding().getElevators().get(0).setAutomaticMode(true);
        c.setTarget(0, 1);
        assertNotEquals(1, e.getTarget(0));
        assertEquals(2, e.getTarget(0));
    }

    @Test
    void testSetTargetException() throws Exception {
        MockElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperTestImpl(e);
        IElevatorController c = new ElevatorControllerImpl(w, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl());
        e.setShouldThrow(true);

        c.setTarget(0, 1);
        assertEquals("setTarget", c.getBuilding().getError());
        assertEquals(false, c.getBuilding().getConnectionState());
    }

    @Test
    void testCTorWithException() throws Exception {
        MockElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperTestImpl(e);
        e.setShouldThrow(true);
        IElevatorController c = new ElevatorControllerImpl(w, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl());

        assertEquals(false, c.getBuilding().getConnectionState());
    }

    @Test
    void testCTorWithInvalidParams() {
        assertThrows(NullPointerException.class, () -> new ElevatorControllerImpl(null, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl()));
        assertThrows(NullPointerException.class, () -> new ElevatorControllerImpl(new ElevatorWrapperTestImpl(), null, new ElevatorModelImpl(), new FloorModelImpl()));
        assertThrows(NullPointerException.class, () -> new ElevatorControllerImpl(new ElevatorWrapperTestImpl(), new BuildingModelImpl(), null, new FloorModelImpl()));
        assertThrows(NullPointerException.class, () -> new ElevatorControllerImpl(new ElevatorWrapperTestImpl(), new BuildingModelImpl(), new ElevatorModelImpl(), null));
    }

    @Test
    void testFillModelWithException() throws Exception {
        MockElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperTestImpl(e);
        IElevatorController c = new ElevatorControllerImpl(w, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl());
        e.setShouldThrow(true);

        c.fillModel();

        assertEquals(false, c.getBuilding().getConnectionState());
    }

    @Test
    void testGetBuilding() throws Exception {
        IElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperTestImpl(e);
        IBuildingModel b = new BuildingModelImpl(1,1);
        IElevatorController c = new ElevatorControllerImpl(w, b, new ElevatorModelImpl(), new FloorModelImpl());
        assertNotEquals(null, c.getBuilding());
    }

    @Test
    void testSetAutomaticMode() throws Exception {
        IElevator e = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
        IElevatorWrapper w = new ElevatorWrapperTestImpl(e);
        IBuildingModel b = new BuildingModelImpl(1,1);
        IElevatorController c = new ElevatorControllerImpl(w, b, new ElevatorModelImpl(), new FloorModelImpl());
        
        IElevatorModel eimp =  c.getBuilding().getElevators().get(0);
        c.setAutomaticMode(0, true);
        assertTrue(eimp.getAutomaticMode());
        c.setAutomaticMode(0, false);
        assertFalse(eimp.getAutomaticMode());
    }

}