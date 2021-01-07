/**
 * @author Daniel Giritzer, S1810567004
 */

package at.fhhagenberg.sqe.elevator.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import at.fhhagenberg.sqe.elevator.utils.SmartList;

/**
 * This class represents the state of a floor.
 */
public class FloorModelImpl implements IFloorModel {

    boolean m_ButtonDown = false;
    boolean m_ButtonUp = false;
    int m_Num;
    SmartList<IElevatorModel> m_ServicedElevators = new SmartList<IElevatorModel>();

    private PropertyChangeSupport m_Changes = new PropertyChangeSupport(this);

    /**
     * CTor
     * @param num Number of this floor
     * @throws FloorInvalidFloorException
     */
    public FloorModelImpl(int num) throws FloorInvalidFloorException {
        if(num < 0)
            throw new FloorInvalidFloorException("Floor number must be positive!");
        m_Num = num;
        m_ServicedElevators.setPropertyChangedName("m_ServicedElevators");
    }

    public FloorModelImpl(){}

    @Override
    public IFloorModel createFloorModel(int num) throws FloorInvalidFloorException {
        FloorModelImpl fm = new FloorModelImpl(num);
        for(PropertyChangeListener l : m_Changes.getPropertyChangeListeners())
            fm.addPropertyChangeListener(l);
        return fm;
    }

    @Override 
    public int getNum(){
        return m_Num;
    }

    @Override
    public boolean getButtonDownPressed(){
        return m_ButtonDown;
    }

    @Override
    public void setButtonDownPressed(boolean s){
        boolean oldVal = m_ButtonDown;
        m_ButtonDown = s;
        m_Changes.firePropertyChange("m_ButtonDown", oldVal, m_ButtonDown);
    }

    @Override
    public boolean getButtonUpPressed(){
        return m_ButtonUp;
    }

    @Override
    public void setButtonUpPressed(boolean s){
        boolean oldVal = m_ButtonUp;
        m_ButtonUp = s;
        m_Changes.firePropertyChange("m_ButtonUp", oldVal, m_ButtonUp);
    }

    @Override
    public SmartList<IElevatorModel> getServicedElevators(){
        return m_ServicedElevators;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l){
        m_Changes.addPropertyChangeListener(l);
        m_ServicedElevators.addPropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l){
        m_Changes.removePropertyChangeListener(l);
        m_ServicedElevators.removePropertyChangeListener(l);
    }

}
