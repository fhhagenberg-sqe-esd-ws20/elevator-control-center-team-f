/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.model;
import java.util.ArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This class represents the state of a building.
 */
public class BuildingModelImpl implements IBuildingModel {

    private long m_ClockTick = 1;
    private int m_FloorHeight = 1;
    private boolean m_ConnectionState = false;
    private String m_Error;

    ArrayList<IElevatorModel> m_Elevators = new ArrayList<IElevatorModel>();
    ArrayList<IFloorModel> m_Floors = new ArrayList<IFloorModel>();

    private PropertyChangeSupport m_ChangesClockTick = new PropertyChangeSupport(this);
    private PropertyChangeSupport m_ChangesConnState = new PropertyChangeSupport(this);
    private PropertyChangeSupport m_ChangesError = new PropertyChangeSupport(this);

    /**
     * CTor
     * Creates new building object.
     * @param clockTick Current clock tick.
     * @param floorHeight The hight of the floors in ft.
     * @throws BuildingInvalidFloorHeightException
     * @throws BuildingInvalidClockTickException
     */
    public BuildingModelImpl(long clockTick, int floorHeight) throws BuildingInvalidFloorHeightException, BuildingInvalidClockTickException {

        if(floorHeight < 1)
            throw new BuildingInvalidFloorHeightException("Floors must at least have hight 1!");

        if(clockTick < 0)
            throw new BuildingInvalidClockTickException("Clock tick must be a positive value!");

        m_ClockTick = clockTick;
        m_FloorHeight = floorHeight;
    }

    public BuildingModelImpl(){};

    @Override
    public IBuildingModel createBuildingModel(long clockTick, int floorHeight) throws BuildingInvalidFloorHeightException, BuildingInvalidClockTickException{
        BuildingModelImpl bm = new BuildingModelImpl(clockTick, floorHeight);
        for(PropertyChangeListener l : m_ChangesClockTick.getPropertyChangeListeners())
            bm.addClockTickPropertyChangeListener(l);
        for(PropertyChangeListener l : m_ChangesConnState.getPropertyChangeListeners())
            bm.addConnectionStatePropertyChangeListener(l);
        for(PropertyChangeListener l : m_ChangesError.getPropertyChangeListeners())
            bm.addErrorPropertyChangeListener(l);
        return bm;
    }

    @Override
    public ArrayList<IElevatorModel> getElevators(){
        return m_Elevators;
    }

    @Override
    public ArrayList<IFloorModel> getFloors(){
        return m_Floors;
    }

    @Override
    public long getClockTick(){
        return m_ClockTick;
    }

    @Override
    public void setConnectionState(boolean s){
        boolean oldVal = m_ConnectionState;
        m_ConnectionState = s;
        m_ChangesConnState.firePropertyChange("m_ConnectionState", oldVal, m_ConnectionState);
    }

    @Override
    public void setClockTick(long l) throws BuildingInvalidClockTickException{
        if(l < 0)
            throw new BuildingInvalidClockTickException("Clock tick must be a positive value!");
        long oldVal = m_ClockTick;
        m_ClockTick = l;
        m_ChangesClockTick.firePropertyChange("m_ClockTick", oldVal, m_ClockTick);
    }

    @Override
    public int getFloorHeight(){
        return m_FloorHeight;
    }

    @Override
    public void addClockTickPropertyChangeListener(PropertyChangeListener l){
        m_ChangesClockTick.addPropertyChangeListener(l);
    }

    @Override
    public void removeClockTickPropertyChangeListener(PropertyChangeListener l){
        m_ChangesClockTick.removePropertyChangeListener(l);
    }

    @Override
    public void addConnectionStatePropertyChangeListener(PropertyChangeListener l){
        m_ChangesConnState.addPropertyChangeListener(l);
    }

    @Override
    public void removeConnectionStatePropertyChangeListener(PropertyChangeListener l){
        m_ChangesConnState.removePropertyChangeListener(l);
    }

    @Override
    public boolean getConnectionState(){
        return m_ConnectionState;
    }

    @Override 
    public void setError(String e){
        String oldVal = m_Error;
        m_Error = e;
        m_ChangesClockTick.firePropertyChange("m_Error", oldVal, m_Error);
    }

    @Override
    public String getError(){
        return m_Error;
    }

    @Override
    public void addErrorPropertyChangeListener(PropertyChangeListener l){
        m_ChangesError.addPropertyChangeListener(l);
    }

    @Override
    public void removeErrorPropertyChangeListener(PropertyChangeListener l){
        m_ChangesError.removePropertyChangeListener(l);
    }

}
