/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.gui;

import at.fhhagenberg.sqe.elevator.controller.IElevatorController;
import javafx.scene.Scene;

/**
 * Interface for a View
 */
public interface IElevatorGUI {
	/**
	 * @return The JavaFX scene to be drawn.
	 */
	public Scene getScene();

	/**
	 * Allows setting the views controller.
	 * @param c Controller to set.
	 */
	public void setController(IElevatorController c);
}
