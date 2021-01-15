/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.model;

import java.beans.PropertyChangeListener;
import at.fhhagenberg.sqe.elevator.utils.SmartList;

/**
 * Interface which describes how objects describing an elevator model should look like.
 */
public interface IElevatorModel {

    /**
     * Enum describing the commited direction.
     */
    public enum CommitedDirection {
        UP, DOWN, UNCOMMITED
    };

    /**
     * Enum describing the current state of the elevators doors.
     */
    public enum DoorStatus {
        OPEN, CLOSED, OPENING, CLOSING
    };

    /**
     * Exception to be thrown when trying to set an invalid floor position.
     */
    @SuppressWarnings("serial")
    public class ElevatorInvalidFloorPositionException extends Exception{
        public ElevatorInvalidFloorPositionException(String errorMessage) {
            super(errorMessage);
        }
    };

    /**
     * Exception to be thrown when trying to set an invalid elevator position.
     */
    @SuppressWarnings("serial")
    public class ElevatorInvalidPositionException extends Exception{
        public ElevatorInvalidPositionException(String errorMessage) {
            super(errorMessage);
        }
    };

    /**
     * Exception to be thrown when trying to set an invalid weight value.
     */
    @SuppressWarnings("serial")
    public class ElevatorInvalidWeightException extends Exception{
        public ElevatorInvalidWeightException(String errorMessage) {
            super(errorMessage);
        }
    };

    /**
     * Exception to be thrown when trying to set an invalid target value.
     */
    @SuppressWarnings("serial")
    public class ElevatorInvalidTargetException extends Exception{
        public ElevatorInvalidTargetException(String errorMessage) {
            super(errorMessage);
        }
    };

    /**
     * Exception to be thrown when trying to set an invalid target value.
     */
    @SuppressWarnings("serial")
    public class ElevatorInvalidCapacityException extends Exception{
        public ElevatorInvalidCapacityException(String errorMessage) {
            super(errorMessage);
        }
    };

    /**
     * Exception to be thrown when trying to set an invalid elevator number.
     */
    @SuppressWarnings("serial")
    public class ElevatorInvalidNumberException extends Exception{
        public ElevatorInvalidNumberException(String errorMessage) {
            super(errorMessage);
        }
    };

    /**
     * Create instance of implementation class. Listeners of current object are assiged to returned object aswell.
     * @param personCapacity Maximum capacity (in persons) this elevator can handle.
     * @param b Building this elevator is located in.
     * @return Instance of implementation class.
     * @throws ElevatorInvalidCapacityException
     * @throws ElevatorInvalidNumberException
     * @throws NullPointerException
     */
    public IElevatorModel createElevatorModel(int personCapacity, int num, IBuildingModel b) throws NullPointerException, ElevatorInvalidNumberException, ElevatorInvalidCapacityException;
    
    /**
     * @return Number of this elevator.
     */
    public int getNum();

    /**
     * @return The elevators commited direction.
     */
    public CommitedDirection getCommitedDirection();

    /**
     * Allows setting the commited direction of the elevator.
     * @param d Action to set.
     */
    public void setCommitedDirection(CommitedDirection d);

    /**
     * @return The elevators acceleration in feet per sec^2. alue is positive when accelerating and negative when decelerating.
     */
    public int getAccel();

    /**
     * Allows changing the elevators acceleration.
     * @param a Acceleration to set (feet per sec^2.). Value is positive when accelerating and negative when decelerating.
     */
    public void setAccel(int a);

    /**
     * @return A ascending sorted list with all button states. (true if pressed, false otherwise) 
     */
    public SmartList<Boolean> getButtons();

    /**
     * @return The current state of the elevators doors.
     */
    public DoorStatus getDoorStatus();

    /**
     * Allows setting the state of the elevators doors.
     * @param d New state to be set.
     */
    public void setDoorStatus(DoorStatus d);

    /**
     * @return The current floor position this elevator is at.
     */
    public int getFloorPos();

    /**
     * Allows changing the floor position this elevator is at.
     * @param p New floor position to set. (number between 0 and getFloorNum())
     * @throws ElevatorInvalidFloorPositionException
     */
    public void setFloorPos(int p) throws ElevatorInvalidFloorPositionException;
    
    /**
     * @return Current elevator speed in m/s. Positive speed indicates an upward movement, negative speed downward movement.
     */
    public int getSpeed();

    /**
     * Allows setting the current elevator speed.
     * @param s New speed in m/s. Positive speed indicates an upward movement, negative speed downward movement.
     */
    public void setSpeed(int s);

    /**
     * @return The weight this elevator is currently carrying (in lbs).
     */
    public int getWeight();

    /**
     * Allows changing the weight this elevator is currently carrying.
     * @param w New weight on the elevator in lbs.
     * @throws ElevatorInvalidWeightException
     */
    public void setWeight(int w) throws ElevatorInvalidWeightException;
    
    /**
     * @return The floor currently targeted by the elevator (Future floorPos).
     */
    public int getTarget();

    /**
     * Allows changing the floor target of this elevator.
     * @param p New floor target to set. (number between 0 and getFloorNum())
     * @throws ElevatorInvalidTargetException
     */ 
    public void setTarget(int t) throws ElevatorInvalidTargetException;

    /**
     * @return Maximum capacity (in persons) this elevator can handle.
     */
    public int getCapacity();

    /**
     * Allows adding a PropertyChangeListener which fires an event when the CommitedDirection property changes.
     * @param l PropertyChangeListener to add.
     */
    public void addCommitedDirectionPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added CommitedDirection propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeCommitedDirectionPropertyChangeListener(PropertyChangeListener l);

    /**
     * Allows adding a PropertyChangeListener which fires an event when the DoorStatus property changes.
     * @param l PropertyChangeListener to add.
     */
    public void addDoorStatusPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added DoorStatus propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeDoorStatusPropertyChangeListener(PropertyChangeListener l);

    /**
     * Allows adding a PropertyChangeListener which fires an event when the acceleration property changes.
     * @param l PropertyChangeListener to add.
     */
    public void addAccelerationPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added acceleration propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeAccelerationPropertyChangeListener(PropertyChangeListener l);

    /**
     * Allows adding a PropertyChangeListener which fires an event when any the state of any of the elevator floor buttons changes.
     * @param l PropertyChangeListener to add.
     */
    public void addButtonStatusPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added button status propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeButtonStatusPropertyChangeListener(PropertyChangeListener l);

    /**
     * Allows adding a PropertyChangeListener which fires an event when the floor position changes.
     * @param l PropertyChangeListener to add.
     */
    public void addFloorPositionPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added floor position propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeFloorPositionPropertyChangeListener(PropertyChangeListener l);

    /**
     * Allows adding a PropertyChangeListener which fires an event when the elevator speed changes.
     * @param l PropertyChangeListener to add.
     */
    public void addSpeedPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added speed propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeSpeedPropertyChangeListener(PropertyChangeListener l);

    /**
     * Allows adding a PropertyChangeListener which fires an event when the load weight changes.
     * @param l PropertyChangeListener to add.
     */
    public void addWeightPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added weight propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeWeightPropertyChangeListener(PropertyChangeListener l);

    /**
     * Allows adding a PropertyChangeListener which fires an event when the elevator target changes.
     * @param l PropertyChangeListener to add.
     */
    public void addTargetPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added target propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeTargetPropertyChangeListener(PropertyChangeListener l);

    /**
     * Allows adding a PropertyChangeListener which fires an event when the position property changes.
     * @param l PropertyChangeListener to add.
     */
    public void addPositionPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added position propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removePositionPropertyChangeListener(PropertyChangeListener l);


    /**
     * Sets elevator position in feet from the bottom of the building. 
     * @param p Elevator position in feet from the bottom of the building;
     * @throws ElevatorInvalidPositionException
     */
    public void setPos(int p) throws ElevatorInvalidPositionException;

    /**
     * @return Current elevator position in feet from the bottom of the building. 
     */
    public int getPos();

    /**
     * @return true if this elevator operates in automatic mode, false otherwise.
     */
    public boolean getAutomaticMode();

    /**
     * Allows setting the operation mode of this elevator.
     * @param m true if this elevator should operate in automatic mode, false otherwise.
     */
    public void setAutomaticMode(boolean m);

    /**
     * Allows adding a PropertyChangeListener which fires an event when the automatic mode property changes.
     * @param l PropertyChangeListener to add.
     */
    public void addAutomaticModePropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added automatic mode propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeAutomaticModePropertyChangeListener(PropertyChangeListener l);

    /**
     * @return In automatic mode the number of the floor this elevator should navigate to.
     * In manual mode the current floor position.
     */
    public int getNextTargetFloor();
}
