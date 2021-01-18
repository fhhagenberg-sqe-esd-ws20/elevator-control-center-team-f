/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.elevator.utils.SmartList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

class SmartListTests {

    @Test
    @SuppressWarnings("unchecked")
    void testAdd() throws Exception {
        SmartList<Boolean> l = new SmartList<Boolean>();
        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<Boolean>)pl.getNewValue()).size() - ((ArrayList<Boolean>)pl.getOldValue()).size()) ; });
        l.add(true);
        assertTrue(hasHanged.get());
        assertEquals(1, sizeDiff.get());
    }
    
    @Test
    void testEquals() throws Exception {
        SmartList<Boolean> l = new SmartList<Boolean>();
        SmartList<Boolean> l2 = new SmartList<Boolean>();
        SmartList<Boolean> l3 = l;

        assertTrue(l.equals(l3));
        assertFalse(l.equals(l2));
    }
    
    @Test
    void testHashCode() throws Exception {
        SmartList<Boolean> l1 = new SmartList<Boolean>();
        ArrayList<Boolean> l2 = new ArrayList<Boolean>();

        assertEquals(l1.hashCode(), l2.hashCode());
	}

    @Test
    @SuppressWarnings("unchecked")
    void testAddByIndex() throws Exception {
        SmartList<Boolean> l = new SmartList<Boolean>();
        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<Boolean>)pl.getNewValue()).size() - ((ArrayList<Boolean>)pl.getOldValue()).size()) ; });
        l.add(0, true);
        assertTrue(hasHanged.get());
        assertEquals(1, sizeDiff.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testAddAll() throws Exception {
        ArrayList<Integer> arrlist2 = new ArrayList<Integer>();
        arrlist2.add(1);
        arrlist2.add(2);
        arrlist2.add(3);
        arrlist2.add(4);

        SmartList<Integer> l = new SmartList<Integer>();
        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<Integer>)pl.getNewValue()).size() - ((ArrayList<Integer>)pl.getOldValue()).size()) ; });
        l.addAll(arrlist2);
        assertTrue(hasHanged.get());
        assertEquals(4, sizeDiff.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testAddAllByIndex() throws Exception {
        ArrayList<Integer> arrlist2 = new ArrayList<Integer>();
        arrlist2.add(1);
        arrlist2.add(2);
        arrlist2.add(3);
        arrlist2.add(4);

        SmartList<Integer> l = new SmartList<Integer>();
        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<Integer>)pl.getNewValue()).size() - ((ArrayList<Integer>)pl.getOldValue()).size()) ; });
        l.addAll(0, arrlist2);
        assertTrue(hasHanged.get());
        assertEquals(4, sizeDiff.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testAddAllCleaar() throws Exception {
        ArrayList<Integer> arrlist2 = new ArrayList<Integer>();
        arrlist2.add(1);
        arrlist2.add(2);
        arrlist2.add(3);
        arrlist2.add(4);

        SmartList<Integer> l = new SmartList<Integer>();
        l.addAll(arrlist2);

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<Integer>)pl.getNewValue()).size() - ((ArrayList<Integer>)pl.getOldValue()).size()) ; });
        
        l.clear();
        assertTrue(hasHanged.get());
        assertEquals(-4, sizeDiff.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testRemoveByIndex() throws Exception {
        ArrayList<Integer> arrlist2 = new ArrayList<Integer>();
        arrlist2.add(1);
        arrlist2.add(2);
        arrlist2.add(3);
        arrlist2.add(4);

        SmartList<Integer> l = new SmartList<Integer>();
        l.addAll(arrlist2);

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<Integer>)pl.getNewValue()).size() - ((ArrayList<Integer>)pl.getOldValue()).size()) ; });
        
        l.remove(2);
        assertTrue(hasHanged.get());
        assertEquals(-1, sizeDiff.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testRemoveByObject() throws Exception {
        ArrayList<Integer> arrlist2 = new ArrayList<Integer>();
        arrlist2.add(1);
        arrlist2.add(2);
        arrlist2.add(3);
        arrlist2.add(4);

        SmartList<Integer> l = new SmartList<Integer>();
        l.addAll(arrlist2);

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<Integer>)pl.getNewValue()).size() - ((ArrayList<Integer>)pl.getOldValue()).size()) ; });
        
        l.remove(arrlist2.get(2));
        assertTrue(hasHanged.get());
        assertEquals(-1, sizeDiff.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testRemoveAll() throws Exception {
        ArrayList<Integer> arrlist1 = new ArrayList<Integer>();
        arrlist1.add(1);
        arrlist1.add(2);

        ArrayList<Integer> arrlist2 = new ArrayList<Integer>();
        arrlist2.add(3);
        arrlist2.add(4);

        SmartList<Integer> l = new SmartList<Integer>();
        l.addAll(arrlist1);
        l.addAll(arrlist2);

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<Integer>)pl.getNewValue()).size() - ((ArrayList<Integer>)pl.getOldValue()).size()) ; });
        
        l.removeAll(arrlist1);
        assertTrue(hasHanged.get());
        assertEquals(-2, sizeDiff.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testRetainAll() throws Exception {
        ArrayList<Integer> arrlist1 = new ArrayList<Integer>();
        arrlist1.add(1);
        arrlist1.add(2);


        SmartList<Integer> l = new SmartList<Integer>();
        l.add(1);
        l.add(2);
        l.add(3);

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<Integer>)pl.getNewValue()).size() - ((ArrayList<Integer>)pl.getOldValue()).size()) ; });
        
        l.retainAll(arrlist1);
        assertTrue(hasHanged.get());
        assertEquals(-1, sizeDiff.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testRelaceAll() throws Exception {
        SmartList<String> l = new SmartList<String>();
        l.add("A");
        l.add("B");

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<String>)pl.getNewValue()).size() - ((ArrayList<String>)pl.getOldValue()).size()) ; });
        
        l.replaceAll(e -> e.toLowerCase());
        assertTrue(hasHanged.get());
        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));
        assertEquals(0, sizeDiff.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testRemoveIf() throws Exception {
        SmartList<String> l = new SmartList<String>();
        l.add("A");
        l.add("B");

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<String>)pl.getNewValue()).size() - ((ArrayList<String>)pl.getOldValue()).size()) ; });
        
        l.removeIf(n -> (n == "B"));
        assertTrue(hasHanged.get());
        assertEquals(-1, sizeDiff.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testSet() throws Exception {
        SmartList<String> l = new SmartList<String>();
        l.add("A");
        l.add("B");

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);
        l.addPropertyChangeListener(pl -> { hasHanged.set(true); sizeDiff.set(((ArrayList<String>)pl.getNewValue()).size() - ((ArrayList<String>)pl.getOldValue()).size()) ; });
        
        l.set(1, "C");
        assertTrue(hasHanged.get());
        assertEquals("C", l.get(1));
        assertEquals(0, sizeDiff.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testRemovePropertyChangeListener() throws Exception {
        SmartList<String> l = new SmartList<String>();
        l.add("A");
        l.add("B");

        AtomicReference<Boolean> hasHanged = new AtomicReference<Boolean>(); hasHanged.set(false);
        AtomicReference<Integer> sizeDiff = new AtomicReference<Integer>(); sizeDiff.set(0);

        PropertyChangeListener pl = (new PropertyChangeListener(){
            @Override public void propertyChange( PropertyChangeEvent e ){
                hasHanged.set(true); 
                sizeDiff.set(((ArrayList<String>)e.getNewValue()).size() - ((ArrayList<String>)e.getOldValue()).size()); 
            }
        });

        l.addPropertyChangeListener(pl);
        l.removePropertyChangeListener(pl);
        
        l.set(1, "C");
        assertFalse(hasHanged.get());
        assertEquals("C", l.get(1));
        assertEquals(0, sizeDiff.get());
    }
}