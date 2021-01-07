/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.model;

import java.util.ArrayList;
import java.beans.PropertyChangeListener;

/**
 * Interface which describes how objects describing a building model should look like.
 */
public interface IBuildingModel {


    /**
     * Exception to be thrown when trying to set a invalid floor height.
     */
    public class BuildingInvalidFloorHeightException extends Exception{
        public BuildingInvalidFloorHeightException(String errorMessage) {
            super(errorMessage);
        }
    };

    /**
     * Exception to be thrown when trying to set a invalid clock tick value.
     */
    public class BuildingInvalidClockTickException extends Exception{
        public BuildingInvalidClockTickException(String errorMessage) {
            super(errorMessage);
        }
    };

    /**
     * Create instance of implementation class.
     * @param clockTick Current clock tick.
     * @param floorHeight The hight of the floors in ft.
     * @return Instance of implementation class.
     * @throws BuildingInvalidFloorHeightException
     * @throws BuildingInvalidClockTickException
     */
    IBuildingModel createBuildingModel(long clockTick, int floorHeight) throws BuildingInvalidFloorHeightException, BuildingInvalidClockTickException;

    /**
     * @return The clock tick of this elevator control system.
     */
    public long getClockTick();

    /**
     * Allows setting a new ClockTick;
     * @param t Clock Tick to set.
     */
    public void setClockTick(long t);

    /**
     * Allows setting the connection state between control center and hardware.
     * @param s New state to set.
     */
    public void setConnectionState(boolean s);

    /**
     * @return Current connection state between control center and hardware.
     */
    public boolean getConnectionState();

    /**
     * @return The hight of the floors in the building in ft.
     */
    public int getFloorHeight();

    /**
     * @return List of all avaiable elevators.
     */
    ArrayList<IElevatorModel> getElevators();

    /**
     * @return List of all avaiable floors.
     */
    ArrayList<IFloorModel> getFloors();

    /**
     * Allows adding a PropertyChangeListener which fires an event when any of the properties changes.
     * @param l PropertyChangeListener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener l);
}
