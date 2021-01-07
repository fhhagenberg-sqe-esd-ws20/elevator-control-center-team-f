package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import at.fhhagenberg.sqe.elevator.mock.MockElevator;

import java.rmi.RemoteException;

import at.fhhagenberg.sqe.elevator.gui.ElevatorGUI;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        
        try {
            MockElevator ele = new MockElevator(2,3,5,10);
            ElevatorGUI gui = new ElevatorGUI(ele);
            stage.setScene(gui.getScene());
            stage.show();
        }
        catch(RemoteException e){

        }
    }

    public static void main(String[] args) {
        launch();
    }

}