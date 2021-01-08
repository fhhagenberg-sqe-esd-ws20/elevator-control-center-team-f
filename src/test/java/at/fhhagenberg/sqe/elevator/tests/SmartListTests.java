/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;


import at.fhhagenberg.sqe.elevator.utils.SmartList;
import java.util.ArrayList;

public class SmartListTests {

    @Test
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
}