/**
 * @author Daniel Giritzer, S1810567004
 */

package at.fhhagenberg.sqe.elevator.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Simple wrapper around ArrayList, which allows listening on
 * changes via PropertyChangeListener.
 */
public class SmartList<T> extends ArrayList<T> {

    private PropertyChangeSupport m_Changes = new PropertyChangeSupport(this);
    private String m_PropertyName = "SmartList";

    @Override
    public boolean add(T e) {
        ArrayList<T> oldVal = new ArrayList<>(this);
        boolean ok = super.add(e);
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
        return ok;
    }

    @Override
    public void add(int index, T e) {
        ArrayList<T> oldVal = new ArrayList<>(this);
        super.add(index, e);
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
    }

    @Override 
    public boolean addAll(Collection<? extends T> c){
        ArrayList<T> oldVal = new ArrayList<>(this);
        boolean ok = super.addAll(c);
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
        return ok;
    }

    @Override 
    public boolean addAll(int index, Collection<? extends T> c){
        ArrayList<T> oldVal = new ArrayList<>(this);
        boolean ok = super.addAll(index, c);
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
        return ok;
    }

    @Override
    public void clear() {
        ArrayList<T> oldVal = new ArrayList<>(this);
        super.clear();
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
    }

    @Override
    public T remove(int index) {
        ArrayList<T> oldVal = new ArrayList<>(this);
        T ret = super.remove(index);
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
        return ret;
    }
    
    @Override
    public boolean remove(Object o){
        ArrayList<T> oldVal = new ArrayList<>(this);
        boolean ok = super.remove(o);
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
        return ok;
    }

    @Override
    public boolean removeAll(Collection<?> c){
        ArrayList<T> oldVal = new ArrayList<>(this);
        boolean ok = super.removeAll(c);
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
        return ok;
    }
    
    @Override
    public boolean removeIf(Predicate<? super T> filter){
        ArrayList<T> oldVal = new ArrayList<>(this);
        boolean ok = super.removeIf(filter);
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
        return ok;
    }
    

    @Override
    public void replaceAll(UnaryOperator<T> operator){
        ArrayList<T> oldVal = new ArrayList<>(this);
        super.replaceAll(operator);
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
    }
    
    @Override
    public boolean retainAll(Collection<?> c){
        ArrayList<T> oldVal = new ArrayList<>(this);
        boolean ok = super.retainAll(c);
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
        return ok;
    }
    
    @Override
    public T set(int index, T element){
        ArrayList<T> oldVal = new ArrayList<>(this);
        T ret = super.set(index, element);
        m_Changes.firePropertyChange(m_PropertyName, oldVal, this);
        return ret;
    }

    public void setPropertyChangedName(String s){
        m_PropertyName = s;
    }

    public void addPropertyChangeListener(PropertyChangeListener l){
        m_Changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l){
        m_Changes.removePropertyChangeListener(l);
    }
}