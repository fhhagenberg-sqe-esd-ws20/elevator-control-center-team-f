package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import at.fhhagenberg.sqe.elevator.controller.ElevatorControllerImpl;
import at.fhhagenberg.sqe.elevator.controller.IElevatorController;
import at.fhhagenberg.sqe.elevator.gui.ElevatorGUI;
import at.fhhagenberg.sqe.elevator.gui.IElevatorGUI;
import at.fhhagenberg.sqe.elevator.model.BuildingModelImpl;
import at.fhhagenberg.sqe.elevator.model.ElevatorModelImpl;
import at.fhhagenberg.sqe.elevator.model.FloorModelImpl;
import at.fhhagenberg.sqe.elevator.model.IBuildingModel;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel;
import at.fhhagenberg.sqe.elevator.model.IFloorModel;
import at.fhhagenberg.sqe.elevator.wrappers.ElevatorWrapperImpl;
import at.fhhagenberg.sqe.elevator.wrappers.IElevatorWrapper;

/**
 * JavaFX App
 */
public class App extends Application {
	private interface OnStopOperator{
		public void exec();
	}

    private IElevatorWrapper m_Elevator;
    private IBuildingModel m_BuildingModel;
    private IElevatorModel m_ElevatorModel;
    private IFloorModel m_FloorModel;
    private IElevatorController m_ElevatorController;
    private OnStopOperator m_OnStopFunction;

    @Override
    public void init() throws Exception {
        m_Elevator = new ElevatorWrapperImpl();
    	m_BuildingModel = new BuildingModelImpl();
    	m_ElevatorModel = new ElevatorModelImpl();
    	m_FloorModel = new FloorModelImpl();
        ElevatorControllerImpl contr = new ElevatorControllerImpl(m_Elevator, m_BuildingModel,  m_ElevatorModel, m_FloorModel);
        m_Elevator.setCustomSocketTimeout(100);
        m_ElevatorController = contr;
    	contr.startPolling();
    	m_OnStopFunction = () -> contr.stopPolling();
    }

    @Override
    public void start(Stage stage) { 
        IElevatorGUI gui = new ElevatorGUI(m_ElevatorController);
        
        stage.setOnCloseRequest(e -> {
	        	if(m_OnStopFunction != null)
	        		m_OnStopFunction.exec();
	            Platform.exit();
	            System.exit(0);
	        }
	    );
        
        stage.setScene(gui.getScene());
        stage.setTitle("Elevator Control Center [Team F]");
        stage.show();
    }
    
    /**
     * Allow injecting custom controller implementations.
     * @param e custom controller implementation
     */
    public void setController(IElevatorController e)
    {
    	m_ElevatorController = e;
    }

    public static void main(String[] args) {
        launch();
    }

}