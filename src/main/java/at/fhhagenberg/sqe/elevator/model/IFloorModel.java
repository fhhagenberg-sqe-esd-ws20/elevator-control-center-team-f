/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.model;

import java.beans.PropertyChangeListener;
import at.fhhagenberg.sqe.elevator.utils.SmartList;

/**
 * Interface which describes how objects describing a floor should look like.
 */
public interface IFloorModel {
    
    /**
     * Exception to be thrown when trying to set an invalid floor number.
     */
    public class FloorInvalidFloorException extends Exception{
        public FloorInvalidFloorException(String errorMessage) {
            super(errorMessage);
        }
    };


    /**
     * Create instance of implementation class.
     * @param num Number of this floor.
     * @return Instance of implementation class.
     * @throws FloorInvalidFloorException
     */
    public IFloorModel createFloorModel(int num) throws FloorInvalidFloorException;

    /**
     * @return True if the down button is pressed, false otherwise.
     */
    public boolean getButtonDownPressed();

    /**
     * @return Number of this floor.
     */
    public int getNum();

    /**
     * Allows setting the state of the down button.
     * @param s New state to set, true if pressed, false otherwise.
     */
    public void setButtonDownPressed(boolean s);

    /**
     * @return True if the up button is pressed, false otherwise.
     */
    public boolean getButtonUpPressed();

    /**
     * Allows setting the state of the up button.
     * @param s New state to set, true if pressed, false otherwise.
     */
    public void setButtonUpPressed(boolean s);

    /**
     * @return List of elevators, which will service this floor.
     */
    public SmartList<IElevatorModel> getServicedElevators();

    /**
     * Allows adding a PropertyChangeListener which fires an event when the button down property changes.
     * @param l PropertyChangeListener to add.
     */
    public void addButtonDownPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added button down propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeButtonDownPropertyChangeListener(PropertyChangeListener l);

    /**
     * Allows adding a PropertyChangeListener which fires an event when the button up property changes.
     * @param l PropertyChangeListener to add.
     */
    public void addButtonUpPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added button up propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeUpDownPropertyChangeListener(PropertyChangeListener l);

    /**
     * Allows adding a PropertyChangeListener which fires an event when the list of serviced elevators changes.
     * @param l PropertyChangeListener to add.
     */
    public void addServicedElevatorsPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a formerly added serviced elevators propertychangedlistener.
     * @param l PropertyChangeListener to remove.
     */
    public void removeServicedElevatorsPropertyChangeListener(PropertyChangeListener l);

}
