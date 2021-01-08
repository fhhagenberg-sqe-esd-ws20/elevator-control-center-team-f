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
    SmartList<Boolean> m_Buttons = new SmartList<Boolean>();
    int m_FloorPos = 0;
    int m_Speed = 0;
    int m_Target = 0;
    int m_Weight = 0;
    int m_Num = 0;
    int m_Pos = 0;
    boolean m_AutomaticMode = false;

    IBuildingModel m_Building;

    private PropertyChangeSupport m_ChangesCommitedDirection = new PropertyChangeSupport(this);
    private PropertyChangeSupport m_ChangesDoorStatus  = new PropertyChangeSupport(this);
    private PropertyChangeSupport m_ChangesAccell = new PropertyChangeSupport(this);
    private PropertyChangeSupport m_ChangesFloorPos = new PropertyChangeSupport(this);
    private PropertyChangeSupport m_ChangesSpeed = new PropertyChangeSupport(this);
    private PropertyChangeSupport m_ChangesTarget = new PropertyChangeSupport(this);
    private PropertyChangeSupport m_ChangesWeight = new PropertyChangeSupport(this);
    private PropertyChangeSupport m_ChangesPos = new PropertyChangeSupport(this);
    private PropertyChangeSupport m_ChangesAutomaticMode = new PropertyChangeSupport(this);

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
        m_Buttons.addAll(Arrays.asList(new Boolean[m_Building.getFloors().size()]));
        m_Buttons.setPropertyChangedName("m_Buttons");
        Collections.fill(m_Buttons, Boolean.FALSE);
    }

    public ElevatorModelImpl(){}

    @Override
    public IElevatorModel createElevatorModel(int personCapacity, int num, IBuildingModel b) throws NullPointerException, ElevatorInvalidNumberException, ElevatorInvalidCapacityException{
        ElevatorModelImpl em = new ElevatorModelImpl(personCapacity, num, b);
        for(PropertyChangeListener l : m_ChangesAccell.getPropertyChangeListeners())
            em.addAccelerationPropertyChangeListener(l);
        for(PropertyChangeListener l : m_ChangesCommitedDirection.getPropertyChangeListeners())
            em.addCommitedDirectionPropertyChangeListener(l);
        for(PropertyChangeListener l : m_ChangesDoorStatus.getPropertyChangeListeners())
            em.addDoorStatusPropertyChangeListener(l);
        for(PropertyChangeListener l : m_Buttons.getPropertyChangeListeners())
            em.addButtonStatusPropertyChangeListener(l);
        for(PropertyChangeListener l : m_ChangesFloorPos.getPropertyChangeListeners())
            em.addFloorPositionPropertyChangeListener(l);
        for(PropertyChangeListener l : m_ChangesPos.getPropertyChangeListeners())
            em.addPositionPropertyChangeListener(l);
        for(PropertyChangeListener l : m_ChangesSpeed.getPropertyChangeListeners())
            em.addSpeedPropertyChangeListener(l);;
        for(PropertyChangeListener l : m_ChangesTarget.getPropertyChangeListeners())
            em.addTargetPropertyChangeListener(l);
        for(PropertyChangeListener l : m_ChangesWeight.getPropertyChangeListeners())
            em.addWeightPropertyChangeListener(l);
        for(PropertyChangeListener l : m_ChangesAutomaticMode.getPropertyChangeListeners())
            em.addAutomaticModePropertyChangeListener(l);
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
        m_ChangesCommitedDirection.firePropertyChange("m_CommitedDirection", oldVal, m_CommitedDirection);
    }

    @Override
    public int getAccel(){
        return m_Accell;
    }

    @Override
    public void setAccel(int a) {
        int oldVal = m_Accell;
        m_Accell = a;
        m_ChangesAccell.firePropertyChange("m_Accell", oldVal, m_Accell);
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
        m_ChangesDoorStatus.firePropertyChange("m_DoorStatus", oldVal, m_DoorStatus);
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
        m_ChangesFloorPos.firePropertyChange("m_FloorPos", oldVal, m_FloorPos);
    }
    
    @Override
    public int getSpeed(){
        return m_Speed;
    }

    @Override
    public void setSpeed(int s) {
        int oldVal = m_Speed;
        m_Speed = s;
        m_ChangesSpeed.firePropertyChange("m_Speed", oldVal, m_Speed);
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
        m_ChangesWeight.firePropertyChange("m_Weight", oldVal, m_Weight);
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
        m_ChangesTarget.firePropertyChange("m_Target", oldVal, m_Target);
    }

    @Override
    public int getCapacity(){
        return m_Capacity;
    }

    @Override
    public void addCommitedDirectionPropertyChangeListener(PropertyChangeListener l){
        m_ChangesCommitedDirection.addPropertyChangeListener(l);
    }

    @Override
    public void removeCommitedDirectionPropertyChangeListener(PropertyChangeListener l){
        m_ChangesCommitedDirection.removePropertyChangeListener(l);
    }

    @Override
    public void addDoorStatusPropertyChangeListener(PropertyChangeListener l){
        m_ChangesDoorStatus.addPropertyChangeListener(l);
    }

    @Override
    public void removeDoorStatusDirectionPropertyChangeListener(PropertyChangeListener l){
        m_ChangesDoorStatus.removePropertyChangeListener(l);
    }

    @Override
    public void addAccelerationPropertyChangeListener(PropertyChangeListener l){
        m_ChangesAccell.addPropertyChangeListener(l);
    }

    @Override
    public void removeAccelerationPropertyChangeListener(PropertyChangeListener l){
        m_ChangesAccell.removePropertyChangeListener(l);
    }

    @Override
    public void addButtonStatusPropertyChangeListener(PropertyChangeListener l){
        m_Buttons.addPropertyChangeListener(l);
    }

    @Override
    public void removeButtonStatusPropertyChangeListener(PropertyChangeListener l){
        m_Buttons.removePropertyChangeListener(l);
    }

    @Override
    public void addFloorPositionPropertyChangeListener(PropertyChangeListener l){
        m_ChangesFloorPos.addPropertyChangeListener(l);
    }

    @Override
    public void removeFloorPositionPropertyChangeListener(PropertyChangeListener l){
        m_ChangesFloorPos.removePropertyChangeListener(l);
    }


    @Override
    public void addSpeedPropertyChangeListener(PropertyChangeListener l){
        m_ChangesSpeed.addPropertyChangeListener(l);
    }


    @Override
    public void removeSpeedPropertyChangeListener(PropertyChangeListener l){
        m_ChangesSpeed.removePropertyChangeListener(l);
    }


    @Override
    public void addWeightPropertyChangeListener(PropertyChangeListener l){
        m_ChangesWeight.addPropertyChangeListener(l);
    }


    @Override
    public void removeWeightPropertyChangeListener(PropertyChangeListener l){
        m_ChangesWeight.removePropertyChangeListener(l);
    }


    @Override
    public void addTargetPropertyChangeListener(PropertyChangeListener l){
        m_ChangesTarget.addPropertyChangeListener(l);
    }

    @Override
    public void removeTargetPropertyChangeListener(PropertyChangeListener l){
        m_ChangesTarget.removePropertyChangeListener(l);
    }

    @Override
    public void addPositionPropertyChangeListener(PropertyChangeListener l){
        m_ChangesPos.addPropertyChangeListener(l);
    }

    @Override
    public void removePositionPropertyChangeListener(PropertyChangeListener l){
        m_ChangesPos.removePropertyChangeListener(l);
    }

    @Override
    public void addAutomaticModePropertyChangeListener(PropertyChangeListener l){
        m_ChangesAutomaticMode.addPropertyChangeListener(l);
    }

    @Override
    public void removeAutomaticModePropertyChangeListener(PropertyChangeListener l){
        m_ChangesAutomaticMode.removePropertyChangeListener(l);
    }

    @Override
    public void setPos(int p) throws ElevatorInvalidPositionException{
        if(p < 0)
            throw new ElevatorInvalidPositionException("Relative position can not be negative!");
        int oldVal = m_Pos;
        m_Pos = p;
        m_ChangesPos.firePropertyChange("m_Pos", oldVal, m_Pos);
    }

    @Override
    public int getPos(){
        return m_Pos;
    }

    @Override
    public boolean getAutomaticMode(){
        return m_AutomaticMode;
    }

    @Override
    public void setAutomaticMode(boolean m){
       boolean oldVal = m_AutomaticMode;
       m_AutomaticMode = m; 
       m_ChangesAutomaticMode.firePropertyChange("m_AutomaticMode", oldVal, m_AutomaticMode);
    }

    @Override
    public int getNextTargetFloor(){
        if(m_AutomaticMode){
            return m_FloorPos; // implement algorithm here
        }
        else{
            return m_FloorPos;
        }
    }

}
