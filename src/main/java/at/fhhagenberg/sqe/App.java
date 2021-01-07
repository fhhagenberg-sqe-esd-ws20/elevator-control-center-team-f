package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.rmi.RemoteException;

import at.fhhagenberg.sqe.elevator.controller.ElevatorControllerImpl;
import at.fhhagenberg.sqe.elevator.controller.IElevatorController;
import at.fhhagenberg.sqe.elevator.gui.ElevatorGUI;
import at.fhhagenberg.sqe.elevator.gui.IElevatorGUI;
import at.fhhagenberg.sqe.elevator.model.BuildingModelImpl;
import at.fhhagenberg.sqe.elevator.model.ElevatorModelImpl;
import at.fhhagenberg.sqe.elevator.model.FloorModelImpl;
import at.fhhagenberg.sqe.elevator.wrappers.IElevatorWrapper;

/**
 * JavaFX App
 */
public class App extends Application {


    private IElevatorWrapper m_Elevator;


    @Override
    public void init() throws Exception {
        // set m_Elevator here;
    }

    @Override
    public void start(Stage stage) {
        IElevatorController controller = new ElevatorControllerImpl(m_Elevator, new BuildingModelImpl(), new ElevatorModelImpl(), new FloorModelImpl()); 
        IElevatorGUI gui = new ElevatorGUI(controller);
        stage.setScene(gui.getScene());
        stage.show();
    }

    /**
     * Allow injecting custom Elevator implementations.
     * @param e custom Elevator implementation
     */
    public void setElevator(IElevatorWrapper e){
        m_Elevator = e;
    }

    public static void main(String[] args) {
        launch();
    }

}