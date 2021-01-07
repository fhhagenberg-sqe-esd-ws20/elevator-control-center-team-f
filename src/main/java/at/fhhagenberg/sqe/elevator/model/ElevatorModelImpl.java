/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.model;


import java.util.Arrays;
import java.util.Collections;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import at.fhhagenberg.sqe.elevator.utils.SmartList;

/**
 * This class represents the state of an elevator.
 */
public class ElevatorModelImpl implements IElevatorModel {
    CommitedDirection m_CommitedDirection = CommitedDirection.UNCOMMITED;
    DoorStatus m_DoorStatus = DoorStatus.CLOSED;
    int m_Accell = 0;
    int m_Capacity = 1;
    SmartList<Boolean> m_Buttons = null;
    int m_FloorPos = 0;
    int m_Speed = 0;
    int m_Target = 0;
    int m_Weight = 0;
    int m_Num = 0;
    int m_Pos = 0;

    IBuildingModel m_Building;

    private PropertyChangeSupport m_Changes = new PropertyChangeSupport(this);

    /**
     * CTor
     * Creates new elevator model object.
     * @param personCapacity Maximum capacity (in persons) this elevator can handle.
     * @param b Building this elevator is located in.
     * @throws ElevatorInvalidCapacityException
     * @throws ElevatorInvalidNumberException
     * @throws NullPointerException
     */
    public ElevatorModelImpl(int personCapacity, int num, IBuildingModel b) throws NullPointerException, ElevatorInvalidNumberException, ElevatorInvalidCapacityException {

        if(personCapacity < 1)
            throw new ElevatorInvalidCapacityException("Elevator must be allowed to transport at least one person!");

        if(num < 0)
            throw new ElevatorInvalidNumberException("Elevator number must be positive!");

        if(b == null)
            throw new NullPointerException("Invalid building passed!");
        
        if(b.getFloors() == null)
            throw new NullPointerException("Floors not initialized! Make sure IBuildingModel has valid floors!");
        
        m_Building = b;
        m_Num = num;
        m_Capacity = personCapacity;
        m_Buttons = new SmartList<Boolean>();
        m_Buttons.addAll(Arrays.asList(new Boolean[m_Building.getFloors().size()]));
        m_Buttons.setPropertyChangedName("m_Buttons");
        Collections.fill(m_Buttons, Boolean.FALSE);
    }

    public ElevatorModelImpl(){}

    @Override
    public IElevatorModel createElevatorModel(int personCapacity, int num, IBuildingModel b) throws NullPointerException, ElevatorInvalidNumberException, ElevatorInvalidCapacityException{
        ElevatorModelImpl em = new ElevatorModelImpl(personCapacity, num, b);
        for(PropertyChangeListener l : m_Changes.getPropertyChangeListeners())
            em.addPropertyChangeListener(l);
        return em;
    }
    
    @Override
    public int getNum(){
        return m_Num;
    }

    @Override
    public CommitedDirection getCommitedDirection(){
        return m_CommitedDirection;
    }

    @Override
    public void setCommitedDirection(CommitedDirection d){
        CommitedDirection oldVal = m_CommitedDirection;
        m_CommitedDirection = d;
        m_Changes.firePropertyChange("m_CommitedDirection", oldVal, m_CommitedDirection);
    }

    @Override
    public int getAccel(){
        return m_Accell;
    }

    @Override
    public void setAccel(int a) {
        int oldVal = m_Accell;
        m_Accell = a;
        m_Changes.firePropertyChange("m_Accell", oldVal, m_Accell);
    }

    @Override
    public SmartList<Boolean> getButtons(){
        return m_Buttons;
    }

    @Override
    public DoorStatus getDoorStatus(){
        return m_DoorStatus;
    }

    @Override
    public void setDoorStatus(DoorStatus d){
        DoorStatus oldVal = m_DoorStatus;
        m_DoorStatus = d;
        m_Changes.firePropertyChange("m_DoorStatus", oldVal, m_DoorStatus);
    }

    @Override
    public int getFloorPos(){
        return m_FloorPos;
    }

    @Override
    public void setFloorPos(int p) throws ElevatorInvalidFloorPositionException{
        if(p < 0)
            throw new ElevatorInvalidFloorPositionException("Tried to set an negative position!");
        if(p > m_Building.getFloors().size())
            throw new ElevatorInvalidFloorPositionException("Given position is out of range (greater then maximum floor number)!");
        int oldVal = m_FloorPos;
        m_FloorPos = p;
        m_Changes.firePropertyChange("m_FloorPos", oldVal, m_FloorPos);
    }
    
    @Override
    public int getSpeed(){
        return m_Speed;
    }

    @Override
    public void setSpeed(int s) {
        int oldVal = m_Speed;
        m_Speed = s;
        m_Changes.firePropertyChange("m_Speed", oldVal, m_Speed);
    }

    @Override
    public int getWeight(){
        return m_Weight;
    }

    @Override
    public void setWeight(int w) throws ElevatorInvalidWeightException{
        if(w < 0)
            throw new ElevatorInvalidWeightException("Tried to set negative weight!");
        int oldVal = m_Weight;
        m_Weight = w;
        m_Changes.firePropertyChange("m_Weight", oldVal, m_Weight);
    }
    
    @Override
    public int getTarget(){
        return m_Target;
    }

    @Override
    public void setTarget(int t) throws ElevatorInvalidTargetException{
        if(t < 0)
            throw new ElevatorInvalidTargetException("Tried to set an negative position!");
        if(t > m_Building.getFloors().size())
            throw new ElevatorInvalidTargetException("Given position is out of range (greater then maximum floor number)!");
        int oldVal = m_Target;
        m_Target = t;
        m_Changes.firePropertyChange("m_Target", oldVal, m_Target);
    }

    @Override
    public int getCapacity(){
        return m_Capacity;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l){
        m_Changes.addPropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l){
        m_Changes.removePropertyChangeListener(l);
    }

    @Override
    public void setPos(int p) throws ElevatorInvalidPositionException{
        if(p < 0)
            throw new ElevatorInvalidPositionException("Relative position can not be negative!");
        int oldVal = m_Pos;
        m_Pos = p;
        m_Changes.firePropertyChange("m_Pos", oldVal, m_Pos);
    }

    @Override
    public int getPos(){
        return m_Pos;
    }

}
