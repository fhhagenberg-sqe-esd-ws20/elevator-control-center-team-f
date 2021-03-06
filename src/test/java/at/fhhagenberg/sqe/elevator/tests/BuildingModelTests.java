/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.elevator.model.BuildingModelImpl;
import at.fhhagenberg.sqe.elevator.model.IBuildingModel;
import at.fhhagenberg.sqe.elevator.model.IBuildingModel.BuildingInvalidClockTickException;
import at.fhhagenberg.sqe.elevator.model.IBuildingModel.BuildingInvalidFloorHeightException;


class BuildingModelTests {

    @Test
    void testCreateBuildingModel() throws Exception {
        IBuildingModel b = new BuildingModelImpl();
        IBuildingModel t = b.createBuildingModel(2, 5);
        assertEquals(5, t.getFloorHeight());
        assertEquals(2, t.getClockTick());
    }
    
    @Test
    void testCreateBuildingModelFail() {
        IBuildingModel b = new BuildingModelImpl();
        assertThrows(BuildingInvalidFloorHeightException.class, () -> {b.createBuildingModel(2, 0);});
        assertThrows(BuildingInvalidClockTickException.class, () -> {b.createBuildingModel(-1, 5);});
    }

    @Test
    void testGetFooors() {
        IBuildingModel b = new BuildingModelImpl();
        assertNotEquals(null, b.getFloors());
    }

    @Test
    void testGetElevators() {
        IBuildingModel b = new BuildingModelImpl();
        assertNotEquals(null, b.getElevators());
    }

    @Test
    void testConnectionState() throws Exception {
        IBuildingModel b = new BuildingModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Boolean> newVal = new AtomicReference<Boolean>(); newVal.set(false);

        b.addConnectionStatePropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((boolean)pl.getNewValue());});

        assertEquals(false, b.getConnectionState());
        b.setConnectionState(true);
        assertTrue(hasHanged.get());
        assertEquals(true, b.getConnectionState());
        assertEquals(true, newVal.get());
    }

    @Test
    void testErrorState() throws Exception {
        IBuildingModel b = new BuildingModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<String> newVal = new AtomicReference<String>(); newVal.set("");

        b.addErrorPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((String)pl.getNewValue());});

        assertEquals("", b.getError());
        b.setError("FEHLER");;
        assertTrue(hasHanged.get());
        assertEquals("FEHLER", b.getError());
        assertEquals("FEHLER", newVal.get());
    }

    @Test
    void testClockTick() throws Exception {
        IBuildingModel b = new BuildingModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Long> newVal = new AtomicReference<Long>();

        b.addClockTickPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Long)pl.getNewValue());});

        assertEquals(0, b.getClockTick());
        b.setClockTick(100);
        assertTrue(hasHanged.get());
        assertEquals(100, b.getClockTick());
        assertEquals(100, newVal.get());
    }

    @Test
    void testClockTickFails() {
        IBuildingModel b = new BuildingModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Long> newVal = new AtomicReference<Long>();

        b.addClockTickPropertyChangeListener(pl -> {hasHanged.set(true); newVal.set((Long)pl.getNewValue());});
        assertThrows(BuildingInvalidClockTickException.class, () -> {b.setClockTick(-1);;});
    }

	@Test
    void testRemovePropertyChangeListener() throws Exception {
        IBuildingModel b = new BuildingModelImpl();

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);

        PropertyChangeListener pl = (new PropertyChangeListener(){
            @Override public void propertyChange( PropertyChangeEvent e ){
                hasHanged.set(true);  
            }
        });

        b.addClockTickPropertyChangeListener(pl);
        b.addConnectionStatePropertyChangeListener(pl);
        b.addErrorPropertyChangeListener(pl);
        b.removeClockTickPropertyChangeListener(pl);
        b.removeConnectionStatePropertyChangeListener(pl);
        b.removeErrorPropertyChangeListener(pl);
        
        b.setClockTick(1);
        b.setConnectionState(true);
        b.setError("error");

        assertFalse(hasHanged.get());
    }

	@Test
    void testCopyListenerViaSimpleFactoryCreate() throws Exception {
        IBuildingModel b = new BuildingModelImpl();

        AtomicReference<Integer> numChanged = new AtomicReference<Integer>(); numChanged.set(0);

        PropertyChangeListener pl = (new PropertyChangeListener(){
            @Override public void propertyChange( PropertyChangeEvent e ){
                numChanged.set(numChanged.get() + 1);  
            }
        });

        b.addClockTickPropertyChangeListener(pl);
        b.addConnectionStatePropertyChangeListener(pl);
        b.addErrorPropertyChangeListener(pl);

        IBuildingModel c = b.createBuildingModel(2, 2);
        
        c.setClockTick(1);
        c.setConnectionState(true);
        c.setError("error");

        assertEquals(3, numChanged.get());
    }
}