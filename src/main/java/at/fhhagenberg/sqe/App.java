package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sqelevator.IElevator;

import java.rmi.Naming;
import java.rmi.RemoteException;

import at.fhhagenberg.sqe.elevator.controller.ElevatorControllerImpl;
import at.fhhagenberg.sqe.elevator.controller.IElevatorController;
import at.fhhagenberg.sqe.elevator.gui.ElevatorGUI;
import at.fhhagenberg.sqe.elevator.gui.IElevatorGUI;
import at.fhhagenberg.sqe.elevator.model.BuildingModelImpl;
import at.fhhagenberg.sqe.elevator.model.ElevatorModelImpl;
import at.fhhagenberg.sqe.elevator.model.FloorModelImpl;
import at.fhhagenberg.sqe.elevator.wrappers.ElevatorWrapperImpl;
import at.fhhagenberg.sqe.elevator.wrappers.IElevatorWrapper;

/**
 * JavaFX App
 */
public class App extends Application {


    private IElevatorWrapper m_Elevator;
    private BuildingModelImpl m_BuildingModel;
    private ElevatorModelImpl m_ElevatorModel;
    private FloorModelImpl m_FloorModelImpl;
    private ElevatorControllerImpl m_ElevatorController;


    @Override
    public void init() throws Exception {
        m_Elevator = new ElevatorWrapperImpl((IElevator) Naming.lookup("rmi://localhost/ElevatorSim"));
    }

    @Override
    public void start(Stage stage) {
    	m_BuildingModel = new BuildingModelImpl();
    	m_ElevatorModel = new ElevatorModelImpl();
    	m_FloorModelImpl = new FloorModelImpl();
    	m_ElevatorController = new ElevatorControllerImpl(m_Elevator, m_BuildingModel,  m_ElevatorModel, m_FloorModelImpl); 
    	m_ElevatorController.startPolling();
        IElevatorGUI gui = new ElevatorGUI(m_ElevatorController);
        stage.setScene(gui.getScene());
        stage.setTitle("Elevator Control Center [Team F]");
        stage.show();
    }
    
    public ElevatorControllerImpl getController()
    {
    	return m_ElevatorController;
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