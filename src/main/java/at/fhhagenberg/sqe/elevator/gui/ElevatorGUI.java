/**
 * @author Daniel Giritzer
 */
package at.fhhagenberg.sqe.elevator.gui;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import at.fhhagenberg.sqe.elevator.wrappers.IElevatorWrapper;

import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.HPos;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.paint.Color;

/**
 * This Class represents the graphical user interface for the Elevator control panel.
 */
public class ElevatorGUI {

	private IElevatorWrapper m_Elevator;
	private int m_SelectedElevator = 0;

	private TextArea m_taErrorMessages = new TextArea();

	private String m_HeadLineStyle = "-fx-font: bold 16 arial;";
	private String m_LabelStyle = "-fx-font: bold 12 arial;";


	// stats elements
	private ArrayList<Label> m_lbStatsPayload = new ArrayList<Label>();
	private ArrayList<Label> m_lbStatsSpeed = new ArrayList<Label>();
	private ArrayList<Label> m_lbStatsTarget = new ArrayList<Label>();
	private ArrayList<Label> m_lbStatsDoor = new ArrayList<Label>();
	private ArrayList<Label> m_lbStatsDir= new ArrayList<Label>();
	private ArrayList<Label> m_lbStatsMode = new ArrayList<Label>();
	private ArrayList<Label> m_lbStatsCap = new ArrayList<Label>();
	private ArrayList<Label> m_lbStatsMaxWeight = new ArrayList<Label>();
	private StackPane m_StatsPane;

	// elevator elements
	private ArrayList<Button> m_AutomaticModeButtons = new ArrayList<Button>();
	private ArrayList<Button> m_ManualModeButtons = new ArrayList<Button>();
	private ArrayList<ArrayList<Button>> m_FloorButtons = new ArrayList<ArrayList<Button>>();

	private StackPane m_ElevatorPane;

	// manual mode elements
	private ArrayList<ComboBox> m_NavigateFloor = new ArrayList<ComboBox>();
	private StackPane m_ManualModePane;

	private Scene m_Scene;

	// elevator selector
	private ArrayList<RadioButton> m_rbSelectElevator = new ArrayList<RadioButton>();

	
	/**
	 * CTor
	 * @param e Elevator to be controlled.
	 */
	public ElevatorGUI(IElevatorWrapper e) throws RemoteException {
		m_Elevator = e;

		m_taErrorMessages.setId("m_taErrorMessages");


		GridPane full = new GridPane();

		m_StatsPane = constructStatusPane();
		m_ManualModePane = constructManualModePane();
		m_ElevatorPane = constructElevatorPane();

		full.add(m_ElevatorPane, 0 , 0, 1, 2);
		full.add(m_StatsPane, 1, 0);
		full.add(m_ManualModePane, 2, 0);
		full.add(constructElevatorSelectionPane(), 1, 1);
		full.add(constructErrorPane(), 2, 1);

		m_Scene = new Scene(full, 250, 250);

	}
	
	/**
	 * @return The status section of the control panel.
	 * @throws RemoteException
	 */
	private StackPane constructStatusPane() throws RemoteException {

		StackPane stacked = new StackPane();

		for(int i = 0; i < m_Elevator.getElevatorNum(); i++){
			GridPane stats = new GridPane();
			stats.setPadding(new Insets(10, 10, 10, 10));
			stats.setVgap(10); 
			stats.setHgap(10); 
			stats.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));

			Label hl_stats = new Label("Status");
			hl_stats.setStyle(m_HeadLineStyle);
			GridPane.setHalignment(hl_stats, HPos.CENTER);
			stats.add(hl_stats, 0, 0, 2, 1);

			Label lb_pl = new Label("Payload [lbs]:");
			lb_pl.setStyle(m_LabelStyle);
			stats.add(lb_pl, 0, 1);

			Label lb_speed = new Label("Speed [m/s]:");
			lb_speed.setStyle(m_LabelStyle);
			stats.add(lb_speed, 0, 2);

			Label lb_target = new Label("Target:");
			lb_target.setStyle(m_LabelStyle);
			stats.add(lb_target, 0, 3);

			Label lb_doors = new Label("Door:");
			lb_doors.setStyle(m_LabelStyle);
			stats.add(lb_doors, 0, 4);

			Label lb_dir = new Label("Direction:");
			lb_dir.setStyle(m_LabelStyle);
			stats.add(lb_dir, 0, 5);

			Label lb_mode = new Label("Mode:");
			lb_mode.setStyle(m_LabelStyle);
			stats.add(lb_mode, 0, 6);

			Label lb_cpcty = new Label("Capacity [pers]:");
			lb_cpcty.setStyle(m_LabelStyle);
			stats.add(lb_cpcty, 0, 7);

			Label lb_maxWeight = new Label("Max Weight[lbs]:");
			lb_maxWeight.setStyle(m_LabelStyle);
			stats.add(lb_maxWeight, 0, 8);

			// labels holding values
			Label lb_pl_cntnd = new Label("Payload [lbs]:" + i);
			lb_pl_cntnd.setStyle(m_LabelStyle);
			lb_pl_cntnd.setId("m_lbStatsPayload_" + i);
			m_lbStatsPayload.add(lb_pl_cntnd);
			stats.add(lb_pl_cntnd, 1, 1);

			Label lb_speed_cntnd  = new Label("Speed [m/s]:");
			lb_speed_cntnd.setStyle(m_LabelStyle);
			lb_speed_cntnd.setId("m_lbStatsSpeed_" + i);
			m_lbStatsSpeed.add(lb_speed_cntnd);
			stats.add(lb_speed_cntnd , 1, 2);

			Label lb_target_cntnd  = new Label("Target:");
			lb_target_cntnd.setStyle(m_LabelStyle);
			lb_target_cntnd.setId("m_lbStatsTarget_" + i);
			m_lbStatsTarget.add(lb_target_cntnd);
			stats.add(lb_target_cntnd , 1, 3);

			Label lb_doors_cntnd  = new Label("Door:");
			lb_doors_cntnd.setStyle(m_LabelStyle);
			lb_doors_cntnd.setId("m_lbStatsDoor_" + i);
			m_lbStatsDoor.add(lb_doors_cntnd);
			stats.add(lb_doors_cntnd , 1, 4);

			Label lb_dir_cntnd  = new Label("Direction:");
			lb_dir_cntnd.setStyle(m_LabelStyle);
			lb_dir_cntnd.setId("m_lbStatsDir_" + i);
			m_lbStatsDir.add(lb_dir_cntnd);
			stats.add(lb_dir_cntnd , 1, 5);

			Label lb_mode_cntnd  = new Label("Mode:");
			lb_mode_cntnd.setStyle(m_LabelStyle);
			lb_mode_cntnd.setId("m_lbStatsMode_" + i);
			m_lbStatsMode.add(lb_mode_cntnd);
			stats.add(lb_mode_cntnd , 1, 6);

			Label lb_cpcty_cntnd  = new Label("Capacity [pers]:");
			lb_cpcty_cntnd.setStyle(m_LabelStyle);
			lb_cpcty_cntnd.setId("m_lbStatsCap_" + i);
			m_lbStatsCap.add(lb_cpcty_cntnd);
			stats.add(lb_cpcty_cntnd, 1, 7);

			Label lb_maxWeight_cntnd  = new Label("Max Weight[lbs]:");
			lb_maxWeight_cntnd.setStyle(m_LabelStyle);
			lb_maxWeight_cntnd.setId("m_lbStatsMaxWeight_" + i);
			m_lbStatsMaxWeight.add(lb_maxWeight_cntnd);
			stats.add(lb_maxWeight_cntnd, 1, 8);

			stats.setVisible(i == 0);

			stacked.getChildren().add(stats);
		}

		return stacked;

	}

	/**
	 * Configure which elevator to show.
	 * @param i Zero based index of elevator to show.
	 * @throws RemoteException
	 */
	public void showElevator(int i) throws RemoteException {
		if(i > m_Elevator.getElevatorNum() || i < 0)
			return;

		for (Node component : m_StatsPane.getChildren())
			component.setVisible(false);
		m_StatsPane.getChildren().get(i).setVisible(true);

		for (Node component : m_ElevatorPane.getChildren())
			component.setVisible(false);
		m_ElevatorPane.getChildren().get(i).setVisible(true);

		for (Node component : m_ManualModePane.getChildren())
			component.setVisible(false);
		m_ManualModePane.getChildren().get(i).setVisible(true);

		m_SelectedElevator = i;
	}

	/**
	 * @return The manual mode section of the control panel.
	 * @throws RemoteException 
	 */
	private StackPane constructManualModePane() throws RemoteException {
		StackPane stacked = new StackPane();

		for(int i = 0; i < m_Elevator.getElevatorNum(); i++){
			GridPane mmode = new GridPane();
			mmode.setPadding(new Insets(10, 10, 10, 10));
			mmode.setVgap(10); 
			mmode.setHgap(10); 
			mmode.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));

			Label hl_mmode = new Label("Manual Mode");
			hl_mmode.setStyle(m_HeadLineStyle);
			GridPane.setHalignment(hl_mmode, HPos.CENTER);
			mmode.add(hl_mmode, 0, 0, 2, 1);

			Label lb_nf = new Label("Next Floor");
			lb_nf.setStyle(m_LabelStyle);
			mmode.add(lb_nf, 0, 1);

			ComboBox cb_floors = new ComboBox();
			cb_floors.setId("m_NavigateFloor_" + i);
			for(int j = 0; j < m_Elevator.getFloorNum(); j++){
				cb_floors.getItems().add(j);
			}

			cb_floors.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
				try{
					m_Elevator.setTarget(m_SelectedElevator, (int)newValue);
				} catch(RemoteException e){}
			});
			 
			m_NavigateFloor.add(cb_floors);

			mmode.add(cb_floors, 0, 2);
			mmode.setVisible(i == 0);

			stacked.getChildren().add(mmode);
		}

		return stacked;
	}
	
	/**
	 * @return The elevator section of the control panel.
	 * @throws RemoteException
	 */
	private StackPane constructElevatorPane() throws RemoteException {
		StackPane stack = new StackPane();

		for(int i = 0; i < m_Elevator.getElevatorNum(); i++){
			GridPane elev = new GridPane();
			elev.setPadding(new Insets(10, 10, 10, 10));
			elev.setVgap(10); 
			elev.setHgap(10); 

			ArrayList<Button> btns = new ArrayList<Button>();
			for(int j = 0; j < m_Elevator.getFloorNum(); j++){
				Button btn = new Button(Integer.toString(j));
				btn.setId("m_FloorButtons_" + i + "_" + j);
				btns.add(btn);
				elev.add(btn, 2, m_Elevator.getFloorNum() - j);
			}

			Button btn_auto = new Button("Automatic");
			Button btn_manual = new Button("Manual");

			btn_auto.setId("m_AutomaticModeButtons_" + i);
			btn_manual.setId("m_ManualModeButtons_" + i);

			elev.add(btn_auto, 0, m_Elevator.getFloorNum() + 1, 1, 2);
			elev.add(btn_manual, 1, m_Elevator.getFloorNum() + 1, 1, 2);
			m_FloorButtons.add(btns);

			/*Slider slider = new Slider();
			slider.setOrientation(Orientation.VERTICAL);
			elev.add(slider, 1, 0, 1, m_Elevator.getFloorNum()+1);*/
			stack.getChildren().add(elev);
		}

		return stack;
	}

	/**
	 * @return The elevator section of the control panel.
	 * @throws RemoteException
	 */
	private GridPane constructElevatorSelectionPane() throws RemoteException {
		GridPane sel = new GridPane();
		sel.setPadding(new Insets(10, 10, 10, 10));
		sel.setVgap(10); 
		sel.setHgap(10); 
		sel.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));

		final ToggleGroup group = new ToggleGroup();
		for(int i = 0; i < m_Elevator.getElevatorNum(); i++){
			RadioButton rb = new RadioButton();
			rb.setSelected(i == 0);
			rb.setToggleGroup(group);
			rb.setText("Elevator " + i);
			rb.setId("m_rbSelectElevator_" + i);
			rb.setUserData(i);
			sel.add(rb, 0, i);
			m_rbSelectElevator.add(rb);
		}

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			@Override
			public void changed(ObservableValue<? extends Toggle> ov,
				Toggle old_toggle, Toggle new_toggle) {
					if (group.getSelectedToggle() != null) {
						try{
							showElevator((int)group.getSelectedToggle().getUserData());
						}
						catch(RemoteException e){}
					}                
				}
		});
		
		return sel;
	}
	
	/**
	 * @return The error section of the control panel.
	 */
	private GridPane constructErrorPane() {
		GridPane err = new GridPane();
		err.setPadding(new Insets(10, 10, 10, 10));
		err.setVgap(10); 
		err.setHgap(10); 
		err.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));

		Label hl_err = new Label("Errors");
		hl_err.setStyle(m_HeadLineStyle);
		GridPane.setHalignment(hl_err, HPos.CENTER);
		err.add(hl_err, 0, 0);

		m_taErrorMessages.setEditable(false);
		err.add(m_taErrorMessages, 0, 1);

		return err;
	}
	
	/**
	 * @return The JavaFX scene to be drawn.
	 */
	public Scene getScene() throws RemoteException {
    	return m_Scene;
	}
	
	
}