/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.elevator.model.BuildingModelImpl;
import at.fhhagenberg.sqe.elevator.model.ElevatorModelImpl;
import at.fhhagenberg.sqe.elevator.model.FloorModelImpl;
import at.fhhagenberg.sqe.elevator.model.IBuildingModel;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel.ElevatorInvalidCapacityException;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel.ElevatorInvalidFloorPositionException;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel.ElevatorInvalidNumberException;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel.ElevatorInvalidPositionException;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel.ElevatorInvalidTargetException;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel.ElevatorInvalidWeightException;

public class ElevatorModelTests {

    @Test
    void testCreateElavatorModel() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();
        IElevatorModel t = e.createElevatorModel(5, 1, new BuildingModelImpl());
        assertEquals(1, t.getNum());
        assertEquals(5, t.getCapacity());
    }
    
    @Test
    void testCreateElavatorModelFail() {
        IElevatorModel e = new ElevatorModelImpl();
        assertThrows(ElevatorInvalidCapacityException.class, () -> {e.createElevatorModel(-1, 1, new BuildingModelImpl());});
        assertThrows(ElevatorInvalidNumberException.class, () -> {e.createElevatorModel(5, -1, new BuildingModelImpl());});
        assertThrows(NullPointerException.class, () -> {e.createElevatorModel(5, 1, null);});
        assertThrows(NullPointerException.class, () -> {e.createElevatorModel(5, 1, null);});
    }

    @Test
    void testCommitedDirection() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<IElevatorModel.CommitedDirection> newVal = new AtomicReference<IElevatorModel.CommitedDirection>(); newVal.set(IElevatorModel.CommitedDirection.UNCOMMITED);

        e.addCommitedDirectionPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((IElevatorModel.CommitedDirection)pl.getNewValue());});

        IElevatorModel t = e.createElevatorModel(5, 1, new BuildingModelImpl());

        assertEquals(IElevatorModel.CommitedDirection.UNCOMMITED, t.getCommitedDirection());
        t.setCommitedDirection(IElevatorModel.CommitedDirection.UP);
        assertTrue(hasHanged.get());
        assertEquals(IElevatorModel.CommitedDirection.UP, t.getCommitedDirection());
        assertEquals(IElevatorModel.CommitedDirection.UP, newVal.get());
    }

    @Test
    void testAccel() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> newVal = new AtomicReference<Integer>(); newVal.set(0);

        e.addAccelerationPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Integer)pl.getNewValue());});

        IElevatorModel t = e.createElevatorModel(5, 1, new BuildingModelImpl());

        assertEquals(0, t.getAccel());
        t.setAccel(42);
        assertTrue(hasHanged.get());
        assertEquals(42, t.getAccel());
        assertEquals(42, newVal.get());
    }

    @Test
    void testButtons() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<ArrayList<Boolean>> newVal = new AtomicReference<ArrayList<Boolean>>();

        e.addButtonStatusPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((ArrayList<Boolean>)pl.getNewValue());});

        IBuildingModel b = new BuildingModelImpl(); b.getFloors().add(new FloorModelImpl());
        IElevatorModel t = e.createElevatorModel(5, 1, b);

        assertEquals(1, t.getButtons().size());
        assertFalse(t.getButtons().get(0));
        t.getButtons().set(0, true);
        assertTrue(hasHanged.get());
        assertTrue(t.getButtons().get(0));
        assertTrue(newVal.get().get(0));
        assertEquals(t.getButtons(), newVal.get());
    }

    @Test
    void testDoorStatus() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<IElevatorModel.DoorStatus> newVal = new AtomicReference<IElevatorModel.DoorStatus>(); newVal.set(IElevatorModel.DoorStatus.CLOSED);

        e.addDoorStatusPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((IElevatorModel.DoorStatus)pl.getNewValue());});

        IElevatorModel t = e.createElevatorModel(5, 1, new BuildingModelImpl());

        assertEquals(IElevatorModel.DoorStatus.CLOSED, t.getDoorStatus());
        t.setDoorStatus(IElevatorModel.DoorStatus.OPEN);
        assertTrue(hasHanged.get());
        assertEquals(IElevatorModel.DoorStatus.OPEN, t.getDoorStatus());
        assertEquals(IElevatorModel.DoorStatus.OPEN, newVal.get());
    }

    @Test
    void testFloorPos() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> newVal = new AtomicReference<Integer>(); newVal.set(0);

        e.addFloorPositionPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Integer)pl.getNewValue());});

        IBuildingModel b = new BuildingModelImpl(); 
        b.getFloors().add(new FloorModelImpl());
        b.getFloors().add(new FloorModelImpl());
        b.getFloors().add(new FloorModelImpl());
        IElevatorModel t = e.createElevatorModel(5, 1, b);

        assertEquals(0, t.getFloorPos());
        t.setFloorPos(2);
        assertTrue(hasHanged.get());
        assertEquals(2, t.getFloorPos());
        assertEquals(2, newVal.get());
    }

    @Test
    void testFloorPosFails() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> newVal = new AtomicReference<Integer>(); newVal.set(0);

        e.addFloorPositionPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Integer)pl.getNewValue());});

        IElevatorModel t = e.createElevatorModel(5, 1, new BuildingModelImpl());
        assertThrows(ElevatorInvalidFloorPositionException.class, () -> {t.setFloorPos(3);});
        assertThrows(ElevatorInvalidFloorPositionException.class, () -> {t.setFloorPos(-1);});
    }

    @Test
    void testSpeed() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> newVal = new AtomicReference<Integer>(); newVal.set(0);

        e.addSpeedPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Integer)pl.getNewValue());});

        IElevatorModel t = e.createElevatorModel(5, 1, new BuildingModelImpl());

        assertEquals(0, t.getSpeed());
        t.setSpeed(42);
        assertTrue(hasHanged.get());
        assertEquals(42, t.getSpeed());
        assertEquals(42, newVal.get());
    }

    @Test
    void testWeight() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> newVal = new AtomicReference<Integer>(); newVal.set(0);

        e.addWeightPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Integer)pl.getNewValue());});

        IElevatorModel t = e.createElevatorModel(5, 1, new BuildingModelImpl());

        assertEquals(0, t.getWeight());
        t.setWeight(42);
        assertTrue(hasHanged.get());
        assertEquals(42, t.getWeight());
        assertEquals(42, newVal.get());
    }

    @Test
    void testWeightFails() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> newVal = new AtomicReference<Integer>(); newVal.set(0);

        e.addWeightPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Integer)pl.getNewValue());});

        IElevatorModel t = e.createElevatorModel(5, 1, new BuildingModelImpl());

        assertThrows(ElevatorInvalidWeightException.class, () -> {t.setWeight(-1);});
    }

    @Test
    void testTarget() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> newVal = new AtomicReference<Integer>(); newVal.set(0);

        e.addTargetPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Integer)pl.getNewValue());});

        IBuildingModel b = new BuildingModelImpl(); 
        b.getFloors().add(new FloorModelImpl());
        b.getFloors().add(new FloorModelImpl());
        b.getFloors().add(new FloorModelImpl());
        IElevatorModel t = e.createElevatorModel(5, 1, b);

        assertEquals(0, t.getTarget());
        t.setTarget(2);
        assertTrue(hasHanged.get());
        assertEquals(2, t.getTarget());
        assertEquals(2, newVal.get());
    }

    @Test
    void testTargetFails() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> newVal = new AtomicReference<Integer>(); newVal.set(0);

        e.addFloorPositionPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Integer)pl.getNewValue());});

        IElevatorModel t = e.createElevatorModel(5, 1, new BuildingModelImpl());
        assertThrows(ElevatorInvalidTargetException.class, () -> {t.setTarget(3);});
        assertThrows(ElevatorInvalidTargetException.class, () -> {t.setTarget(-1);});
    }

    @Test
    void testPos() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> newVal = new AtomicReference<Integer>(); newVal.set(0);

        e.addPositionPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Integer)pl.getNewValue());});

        IBuildingModel b = new BuildingModelImpl(); 
        b.getFloors().add(new FloorModelImpl());
        b.getFloors().add(new FloorModelImpl());
        b.getFloors().add(new FloorModelImpl());
        IElevatorModel t = e.createElevatorModel(5, 1, b);

        assertEquals(0, t.getPos());
        t.setPos(2);
        assertTrue(hasHanged.get());
        assertEquals(2, t.getPos());
        assertEquals(2, newVal.get());
    }

    @Test
    void testPosFails() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> newVal = new AtomicReference<Integer>(); newVal.set(0);

        e.addPositionPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Integer)pl.getNewValue());});

        IElevatorModel t = e.createElevatorModel(5, 1, new BuildingModelImpl());
        assertThrows(ElevatorInvalidPositionException.class, () -> {t.setPos(-1);});
    }

    @Test
    void testAutomaticMode() throws Exception {
        IElevatorModel e = new ElevatorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Boolean> newVal = new AtomicReference<Boolean>(); newVal.set(false);

        e.addAutomaticModePropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Boolean)pl.getNewValue());});

        IElevatorModel t = e.createElevatorModel(5, 1, new BuildingModelImpl());

        assertFalse(t.getAutomaticMode());
        t.setAutomaticMode(true);
        assertTrue(hasHanged.get());
        assertTrue(t.getAutomaticMode());
        assertTrue(newVal.get());
    }

	@Test
    void testRemovePropertyChangeListener() throws Exception {
        IBuildingModel b = new BuildingModelImpl();
        b.getFloors().add(new FloorModelImpl());
        b.getFloors().add(new FloorModelImpl());
        b.getFloors().add(new FloorModelImpl());
        b.getFloors().add(new FloorModelImpl());
        IElevatorModel e = new ElevatorModelImpl(2, 0, b);

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);

        PropertyChangeListener l = (new PropertyChangeListener(){
            @Override public void propertyChange( PropertyChangeEvent e ){
                hasHanged.set(true);  
            }
        });

        e.addAccelerationPropertyChangeListener(l);
        e.addAutomaticModePropertyChangeListener(l);
        e.addButtonStatusPropertyChangeListener(l);
        e.addCommitedDirectionPropertyChangeListener(l);
        e.addDoorStatusPropertyChangeListener(l);
        e.addFloorPositionPropertyChangeListener(l);
        e.addPositionPropertyChangeListener(l);
        e.addSpeedPropertyChangeListener(l);
        e.addTargetPropertyChangeListener(l);
        e.addWeightPropertyChangeListener(l);

        e.removeAccelerationPropertyChangeListener(l);
        e.removeAutomaticModePropertyChangeListener(l);
        e.removeButtonStatusPropertyChangeListener(l);
        e.removeCommitedDirectionPropertyChangeListener(l);
        e.removeDoorStatusPropertyChangeListener(l);
        e.removeFloorPositionPropertyChangeListener(l);
        e.removePositionPropertyChangeListener(l);
        e.removeSpeedPropertyChangeListener(l);
        e.removeTargetPropertyChangeListener(l);
        e.removeWeightPropertyChangeListener(l);

        e.setAccel(5);
        e.setAutomaticMode(true);
        e.getButtons().set(0, true);
        e.setCommitedDirection(IElevatorModel.CommitedDirection.DOWN);
        e.setDoorStatus(IElevatorModel.DoorStatus.OPENING);
        e.setFloorPos(2);
        e.setPos(2);
        e.setSpeed(2);
        e.setTarget(2);
        e.setWeight(2);

        assertFalse(hasHanged.get());
    }

}