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

import at.fhhagenberg.sqe.elevator.model.ElevatorModelImpl;
import at.fhhagenberg.sqe.elevator.model.FloorModelImpl;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel;
import at.fhhagenberg.sqe.elevator.model.IFloorModel;
import at.fhhagenberg.sqe.elevator.model.IFloorModel.FloorInvalidFloorException;

public class FloorModelTests {

    @Test
    void testCreateFloorModel() throws Exception {
        IFloorModel f = new FloorModelImpl();
        IFloorModel t = f.createFloorModel(1);
        assertEquals(1, t.getNum());
    }
    
    @Test
    void testCreateFloorModelFail() {
        IFloorModel f = new FloorModelImpl();
        assertThrows(FloorInvalidFloorException.class, () -> {f.createFloorModel(-1);});
    }

	@Test
    void testButtonDown() throws Exception {
        IFloorModel f = new FloorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Boolean> newVal = new AtomicReference<Boolean>(); newVal.set(false);

        f.addButtonDownPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((boolean)pl.getNewValue());});

        IFloorModel t = f.createFloorModel(1);

        assertFalse(t.getButtonDownPressed());
        t.setButtonDownPressed(true);
        assertTrue(hasHanged.get());
        assertTrue(t.getButtonDownPressed());
        assertTrue(newVal.get());
    }

	@Test
    void testButtonUp() throws Exception {
        IFloorModel f = new FloorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Boolean> newVal = new AtomicReference<Boolean>(); newVal.set(false);

        f.addButtonUpPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((boolean)pl.getNewValue());});

        IFloorModel t = f.createFloorModel(1);

        assertFalse(t.getButtonUpPressed());
        t.setButtonUpPressed(true);
        assertTrue(hasHanged.get());
        assertTrue(t.getButtonUpPressed());
        assertTrue(newVal.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testServicedElevators() throws Exception {
        IFloorModel f = new FloorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<ArrayList<IElevatorModel>> newVal = new AtomicReference<ArrayList<IElevatorModel>>();

        f.addServicedElevatorsPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((ArrayList<IElevatorModel>)pl.getNewValue());});

        IFloorModel t = f.createFloorModel(1);

        t.getServicedElevators().add(new ElevatorModelImpl());

        assertTrue(hasHanged.get());
        assertEquals(t.getServicedElevators(), newVal.get());
        assertEquals(1 ,newVal.get().size());
    }

	@Test
    void testRemovePropertyChangeListener() throws Exception {
        IFloorModel f = new FloorModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);

        PropertyChangeListener pl = (new PropertyChangeListener(){
            @Override public void propertyChange( PropertyChangeEvent e ){
                hasHanged.set(true);  
            }
        });

        f.addButtonDownPropertyChangeListener(pl);
        f.addButtonUpPropertyChangeListener(pl);
        f.addServicedElevatorsPropertyChangeListener(pl);
        f.removeButtonDownPropertyChangeListener(pl);
        f.removeButtonUpPropertyChangeListener(pl);
        f.removeServicedElevatorsPropertyChangeListener(pl);
        
        f.setButtonDownPressed(true);
        f.setButtonUpPressed(true);
        f.getServicedElevators().add(new ElevatorModelImpl());

        assertFalse(hasHanged.get());
    }
}