package at.fhhagenberg.sqe.elevator.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.elevator.wrappers.ElevatorWrapperImpl;
import at.fhhagenberg.sqe.elevator.wrappers.IElevatorWrapper;

public class ElevatorWrapperTests {
	private static final Integer ELEVATOR_CAPACITY = 10;
	private static final Integer NUM_ELEVATORS = 2;
	private static final Integer NUM_FLOORS = 3;
	private static final Integer FLOOR_HEIGHT = 5;

	private static final int ELEVATOR_0 = 0;
	private static final int ELEVATOR_1 = 1;

	public static final int FLOOR_0 = 0;
	public static final int FLOOR_1 = 1;
	public static final int FLOOR_2 = 2;

	private MockElevator mockObj;
	private IElevatorWrapper wrapper;

	@BeforeEach
	public void setup() {
		mockObj = new MockElevator(NUM_ELEVATORS, NUM_FLOORS, FLOOR_HEIGHT, ELEVATOR_CAPACITY);
		wrapper = new ElevatorWrapperImpl(mockObj);
	}

	@Test
	void testNumFloors() throws Exception {
		assertEquals(NUM_FLOORS, wrapper.getFloorNum());
	}

	@Test
	void testNumElevators() throws Exception {
		assertEquals(NUM_ELEVATORS, wrapper.getElevatorNum());
	}

	@Test
	void testCommittedDirectionInit() throws Exception {
		assertEquals(MockElevator.ELEVATOR_DIRECTION_UNCOMMITTED, wrapper.getCommittedDirection(ELEVATOR_0));
		assertEquals(MockElevator.ELEVATOR_DIRECTION_UNCOMMITTED, wrapper.getCommittedDirection(ELEVATOR_1));
	}

	@Test
	void testCommittedDirectionSetGet() throws Exception {
		wrapper.setCommittedDirection(ELEVATOR_0, IElevatorWrapper.ELEVATOR_DIRECTION_DOWN);
		wrapper.setCommittedDirection(ELEVATOR_1, IElevatorWrapper.ELEVATOR_DIRECTION_UP);

		assertEquals(MockElevator.ELEVATOR_DIRECTION_DOWN, wrapper.getCommittedDirection(ELEVATOR_0));
		assertEquals(MockElevator.ELEVATOR_DIRECTION_UP, wrapper.getCommittedDirection(ELEVATOR_1));

		wrapper.setCommittedDirection(ELEVATOR_0, IElevatorWrapper.ELEVATOR_DIRECTION_UNCOMMITTED);
		wrapper.setCommittedDirection(ELEVATOR_1, IElevatorWrapper.ELEVATOR_DIRECTION_UNCOMMITTED);

		assertEquals(MockElevator.ELEVATOR_DIRECTION_UNCOMMITTED, wrapper.getCommittedDirection(ELEVATOR_0));
		assertEquals(MockElevator.ELEVATOR_DIRECTION_UNCOMMITTED, wrapper.getCommittedDirection(ELEVATOR_1));
	}

	@Test
	void testCommittedDirectionInvalid() throws Exception {
		assertThrows(MockElevatorException.class, () -> {
			wrapper.getCommittedDirection(ELEVATOR_0 - 1);
		});

		assertThrows(MockElevatorException.class, () -> {
			wrapper.getCommittedDirection(NUM_ELEVATORS);
		});

		assertThrows(MockElevatorException.class, () -> {
			wrapper.setCommittedDirection(ELEVATOR_0, MockElevator.ELEVATOR_DIRECTION_UP - 1);
		});

		assertThrows(MockElevatorException.class, () -> {
			wrapper.setCommittedDirection(ELEVATOR_0, MockElevator.ELEVATOR_DIRECTION_UNCOMMITTED + 1);
		});
	}

	@Test
	void testTargetInit() throws Exception {
		assertEquals(FLOOR_0, wrapper.getTarget(ELEVATOR_0));
		assertEquals(FLOOR_0, wrapper.getTarget(ELEVATOR_1));
	}

	@Test
	void testTargetSetGet() throws Exception {
		wrapper.setTarget(ELEVATOR_0, FLOOR_1);
		wrapper.setTarget(ELEVATOR_1, FLOOR_2);

		assertEquals(FLOOR_1, wrapper.getTarget(ELEVATOR_0));
		assertEquals(FLOOR_2, wrapper.getTarget(ELEVATOR_1));
	}

	@Test
	void testServicesFloorsInit() throws Exception {
		assertEquals(true, wrapper.getServicesFloors(ELEVATOR_0, FLOOR_0));
		assertEquals(true, wrapper.getServicesFloors(ELEVATOR_1, FLOOR_0));

		assertEquals(true, wrapper.getServicesFloors(ELEVATOR_0, FLOOR_1));
		assertEquals(true, wrapper.getServicesFloors(ELEVATOR_1, FLOOR_1));

		assertEquals(true, wrapper.getServicesFloors(ELEVATOR_0, FLOOR_2));
		assertEquals(true, wrapper.getServicesFloors(ELEVATOR_1, FLOOR_2));
	}

	@Test
	void testServicesFloorsSetGet() throws Exception {
		wrapper.setServicesFloors(ELEVATOR_0, FLOOR_0, false);
		assertEquals(false, wrapper.getServicesFloors(ELEVATOR_0, FLOOR_0));
		assertEquals(true, wrapper.getServicesFloors(ELEVATOR_1, FLOOR_0));

		wrapper.setServicesFloors(ELEVATOR_1, FLOOR_1, false);
		assertEquals(true, wrapper.getServicesFloors(ELEVATOR_0, FLOOR_1));
		assertEquals(false, wrapper.getServicesFloors(ELEVATOR_1, FLOOR_1));

		wrapper.setServicesFloors(ELEVATOR_0, FLOOR_0, false);
		wrapper.setServicesFloors(ELEVATOR_0, FLOOR_1, false);
		wrapper.setServicesFloors(ELEVATOR_0, FLOOR_2, false);
		assertEquals(false, wrapper.getServicesFloors(ELEVATOR_0, FLOOR_0));
		assertEquals(false, wrapper.getServicesFloors(ELEVATOR_0, FLOOR_1));
		assertEquals(false, wrapper.getServicesFloors(ELEVATOR_0, FLOOR_2));
	}

	@Test
	void testServicesFloorsInvalid() throws Exception {
		assertThrows(MockElevatorException.class, () -> {
			wrapper.getServicesFloors(ELEVATOR_0 - 1, FLOOR_0);
		});

		assertThrows(MockElevatorException.class, () -> {
			wrapper.getServicesFloors(NUM_ELEVATORS, FLOOR_0);
		});

		assertThrows(MockElevatorException.class, () -> {
			wrapper.getServicesFloors(ELEVATOR_0, FLOOR_0 - 1);
		});

		assertThrows(MockElevatorException.class, () -> {
			wrapper.getServicesFloors(ELEVATOR_0, NUM_FLOORS);
		});
	}

	@Test
	void testDoorStatusSetGet() throws Exception {
		mockObj.getElevators().get(ELEVATOR_0).setDoorStatus(MockElevator.ELEVATOR_DOORS_OPENING);
		mockObj.getElevators().get(ELEVATOR_1).setDoorStatus(MockElevator.ELEVATOR_DOORS_CLOSING);

		assertEquals(MockElevator.ELEVATOR_DOORS_OPENING, wrapper.getElevatorDoorStatus(ELEVATOR_0));
		assertEquals(MockElevator.ELEVATOR_DOORS_CLOSING, wrapper.getElevatorDoorStatus(ELEVATOR_1));

		mockObj.getElevators().get(ELEVATOR_0).setDoorStatus(MockElevator.ELEVATOR_DOORS_OPEN);
		mockObj.getElevators().get(ELEVATOR_1).setDoorStatus(MockElevator.ELEVATOR_DOORS_CLOSED);

		assertEquals(MockElevator.ELEVATOR_DOORS_OPEN, wrapper.getElevatorDoorStatus(ELEVATOR_0));
		assertEquals(MockElevator.ELEVATOR_DOORS_CLOSED, wrapper.getElevatorDoorStatus(ELEVATOR_1));
	}

	@Test
	void testDoorStatusInvalid() throws Exception {
		assertThrows(MockElevatorException.class, () -> {
			mockObj.getElevators().get(ELEVATOR_0).setDoorStatus(MockElevator.ELEVATOR_DOORS_OPEN - 1);
		});

		assertThrows(MockElevatorException.class, () -> {
			mockObj.getElevators().get(ELEVATOR_0).setDoorStatus(MockElevator.ELEVATOR_DOORS_CLOSING + 1);
		});
	}

	@Test
	void testFloorButtonSetGet() throws Exception {
		mockObj.getElevators().get(ELEVATOR_0).setFloorButtonActive(FLOOR_0, true);
		mockObj.getElevators().get(ELEVATOR_1).setFloorButtonActive(FLOOR_1, true);

		assertEquals(true, wrapper.getElevatorButton(ELEVATOR_0, FLOOR_0));
		assertEquals(false, wrapper.getElevatorButton(ELEVATOR_0, FLOOR_1));
		assertEquals(false, wrapper.getElevatorButton(ELEVATOR_0, FLOOR_2));

		assertEquals(false, wrapper.getElevatorButton(ELEVATOR_1, FLOOR_0));
		assertEquals(true, wrapper.getElevatorButton(ELEVATOR_1, FLOOR_1));
		assertEquals(false, wrapper.getElevatorButton(ELEVATOR_1, FLOOR_2));
	}

	@Test
	void testFloorButtonInvalid() throws Exception {
		assertThrows(MockElevatorException.class, () -> {
			mockObj.getElevators().get(ELEVATOR_0).setFloorButtonActive(FLOOR_0 - 1, true);
		});

		assertThrows(MockElevatorException.class, () -> {
			mockObj.getElevators().get(ELEVATOR_1).setFloorButtonActive(FLOOR_2 + 1, true);
		});
	}

	@Test
	void testElevatorPositionGet() throws Exception {
		assertEquals(0, wrapper.getElevatorPosition(ELEVATOR_0));
		assertEquals(0, wrapper.getElevatorPosition(ELEVATOR_1));

		wrapper.setTarget(ELEVATOR_0, FLOOR_1);
		assertEquals(FLOOR_HEIGHT * FLOOR_1, wrapper.getElevatorPosition(ELEVATOR_0));

		wrapper.setTarget(ELEVATOR_1, FLOOR_2);
		assertEquals(FLOOR_HEIGHT * FLOOR_2, wrapper.getElevatorPosition(ELEVATOR_1));
	}

	@Test
	void testClockTickGet() throws Exception {
		assertEquals(MockElevator.CLOCK_TICK_MOCK_VALUE, wrapper.getClockTick());
	}

	@Test
	void testFloorHeightGet() throws Exception {
		assertEquals(FLOOR_HEIGHT, wrapper.getFloorHeight());
	}
	
	@Test
	void testAccGetSet() throws Exception
	{
		assertEquals(MockElevator.ELEVATOR_ACCELERATION_MOCK_VALUE,wrapper.getElevatorAccel(ELEVATOR_0));
		assertEquals(MockElevator.ELEVATOR_ACCELERATION_MOCK_VALUE,wrapper.getElevatorAccel(ELEVATOR_1));
		mockObj.getElevators().get(ELEVATOR_0).setAcceleration(-10);
		mockObj.getElevators().get(ELEVATOR_1).setAcceleration(-10);
		assertEquals(-10,wrapper.getElevatorAccel(ELEVATOR_0));
		assertEquals(-10,wrapper.getElevatorAccel(ELEVATOR_1));
	}
	
	@Test
	void testWeighGetSet() throws Exception
	{
		assertEquals(MockElevator.ELEVATOR_WEIGHT_MOCK_VALUE,wrapper.getElevatorWeight(ELEVATOR_0));
		assertEquals(MockElevator.ELEVATOR_WEIGHT_MOCK_VALUE,wrapper.getElevatorWeight(ELEVATOR_1));
		mockObj.getElevators().get(ELEVATOR_0).setWeight(100);
		mockObj.getElevators().get(ELEVATOR_1).setWeight(100);
		assertEquals(100,wrapper.getElevatorWeight(ELEVATOR_0));
		assertEquals(100,wrapper.getElevatorWeight(ELEVATOR_1));
	}
	
	@Test
	void testSpeedGetSet() throws Exception
	{
		assertEquals(MockElevator.ELEVATOR_SPEED_MOCK_VALUE,wrapper.getElevatorSpeed(ELEVATOR_0));
		assertEquals(MockElevator.ELEVATOR_SPEED_MOCK_VALUE,wrapper.getElevatorSpeed(ELEVATOR_1));
		mockObj.getElevators().get(ELEVATOR_0).setSpeed(100);
		mockObj.getElevators().get(ELEVATOR_1).setSpeed(100);
		assertEquals(100,wrapper.getElevatorSpeed(ELEVATOR_0));
		assertEquals(100,wrapper.getElevatorSpeed(ELEVATOR_1));
	}

}