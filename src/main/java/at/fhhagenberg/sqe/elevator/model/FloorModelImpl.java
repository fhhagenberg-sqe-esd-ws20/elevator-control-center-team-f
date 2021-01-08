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

    private boolean m_ButtonDown = false;
    private boolean m_ButtonUp = false;
    private int m_Num;
    private SmartList<IElevatorModel> m_ServicedElevators = new SmartList<IElevatorModel>();

    private PropertyChangeSupport m_ChangesBtnDown = new PropertyChangeSupport(this);
    private PropertyChangeSupport m_ChangesBtnUp = new PropertyChangeSupport(this);

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
        for(PropertyChangeListener l : m_ChangesBtnDown.getPropertyChangeListeners())
            fm.addButtonDownPropertyChangeListener(l);
        for(PropertyChangeListener l : m_ChangesBtnUp.getPropertyChangeListeners())
            fm.addButtonUpPropertyChangeListener(l);
        for(PropertyChangeListener l : m_ServicedElevators.getPropertyChangeListeners())
            fm.addServicedElevatorsPropertyChangeListener(l);
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
        m_ChangesBtnDown.firePropertyChange("m_ButtonDown", oldVal, m_ButtonDown);
    }

    @Override
    public boolean getButtonUpPressed(){
        return m_ButtonUp;
    }

    @Override
    public void setButtonUpPressed(boolean s){
        boolean oldVal = m_ButtonUp;
        m_ButtonUp = s;
        m_ChangesBtnUp.firePropertyChange("m_ButtonUp", oldVal, m_ButtonUp);
    }

    @Override
    public SmartList<IElevatorModel> getServicedElevators(){
        return m_ServicedElevators;
    }

    @Override
    public void addButtonDownPropertyChangeListener(PropertyChangeListener l){
        m_ChangesBtnDown.addPropertyChangeListener(l);
    }

    @Override
    public void removeButtonDownPropertyChangeListener(PropertyChangeListener l){
        m_ChangesBtnDown.removePropertyChangeListener(l);
    }

    @Override
    public void addButtonUpPropertyChangeListener(PropertyChangeListener l){
        m_ChangesBtnUp.addPropertyChangeListener(l);
    }

    @Override
    public void removeUpDownPropertyChangeListener(PropertyChangeListener l){
        m_ChangesBtnUp.removePropertyChangeListener(l);
    }

    @Override
    public void addServicedElevatorsPropertyChangeListener(PropertyChangeListener l){
        m_ServicedElevators.addPropertyChangeListener(l);
    }

    @Override
    public void removeServicedElevatorsPropertyChangeListener(PropertyChangeListener l){
        m_ServicedElevators.removePropertyChangeListener(l);
    }

}
