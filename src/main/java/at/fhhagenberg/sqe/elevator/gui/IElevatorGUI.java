package at.fhhagenberg.sqe.elevator.gui;

public interface IElevatorGUI {
    
    /**
	 * Update the position of a given elevator.
	 * @param elevatorNum elevator index (zero based)
	 * @param pos New position
	 */
	public void updateElevatorPosition(int elevatorNum, int pos);
}
