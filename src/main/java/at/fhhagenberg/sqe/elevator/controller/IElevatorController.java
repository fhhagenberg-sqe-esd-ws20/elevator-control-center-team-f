/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.controller;

import at.fhhagenberg.sqe.elevator.model.IBuildingModel;

/**
 * Controller interface for sending commands to the elevator(s).
 */
public interface IElevatorController {

    /**
     * Sets the committed direction of the specified elevator (up / down / uncommitted). 
     * @param elevatorNumber elevator number whose committed direction is being set
     * @param direction direction being set where up=0, down=1 and uncommitted=2
     */
    public void setCommittedDirection(int elevatorNumber, int direction);

    /**
     * Sets whether or not the specified elevator will service the specified floor (yes/no). 
     * @param elevatorNumber elevator number whose service is being defined
     * @param floor floor whose service by the specified elevator is being set
     * @param service indicates whether the floor is serviced by the specified elevator (yes=true,no=false)
     */
    public void setServicesFloors(int elevatorNumber, int floor, boolean service);

    /**
     * Sets the floor target of the specified elevator. 
     * @param elevatorNumber elevator number whose target floor is being set
     * @param target floor number which the specified elevator is to target
     */
    public void setTarget(int elevatorNumber, int target);

    /**
     * Disables or enables automatic mode for a given elevator. 
     * @param elevatorNumber elevator number to change the mode for.
     * @param mode true for automatic mode false otherwise.
     */
    public void setAutomaticMode(int elevatorNumber, boolean mode);

    /**
     * Try to restore a failsafe state after loosing connection.
     */
    public void maintainConnection();

    /**
     * @return Building which is controlled by this controller.
     */
    public IBuildingModel getBuilding();
}
