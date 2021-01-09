/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.gui;

import javafx.event.EventHandler;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import at.fhhagenberg.sqe.elevator.controller.IElevatorController;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel;
import at.fhhagenberg.sqe.elevator.model.IFloorModel;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel.CommitedDirection;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.geometry.HPos;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * This Class represents the graphical user interface for the Elevator control panel.
 */
public class ElevatorGUI implements IElevatorGUI {

	private IElevatorController m_Controller;
	private int m_SelectedElevator = 0;

	private TextArea m_taErrorMessages = new TextArea();

	private String m_HeadLineStyle = "-fx-font: bold 16 arial;";
	private String m_LabelStyle = "-fx-font: bold 12 arial;";

	private String m_ButtonSelectStyle = "-fx-background-color: #FFA500";
	private String m_ButtonDefaultSyle = "-fx-background-color: #C0C0C0";


	private StackPane m_StatsPane;
	private StackPane m_ElevatorPane;
	private StackPane m_ManualModePane;
	private Scene m_Scene;

	// elevator selector
	private ArrayList<RadioButton> m_rbSelectElevator = new ArrayList<RadioButton>();

	
	/**
	 * CTor
	 * @param e Elevator controller.
	 */
	public ElevatorGUI(IElevatorController e) {
		m_Controller = e;

		m_taErrorMessages.setId("m_taErrorMessages");


		GridPane full = new GridPane();

		full.setPadding(new Insets(10, 10, 10, 10));
		full.setVgap(10); 
		full.setHgap(10); 

		m_StatsPane = constructStatusPane();
		m_ManualModePane = constructManualModePane();
		m_ElevatorPane = constructElevatorPane();

		full.add(m_ElevatorPane, 0 , 0, 1, 2);
		full.add(m_StatsPane, 1, 0);
		full.add(m_ManualModePane, 2, 0);
		full.add(constructElevatorSelectionPane(), 1, 1);
		full.add(constructErrorPane(), 2, 1);

		ScrollPane sp = new ScrollPane();
		sp.setContent(full);

		m_Scene = new Scene(sp, 800, 500);

	}
	
	/**
	 * @return The status section of the control panel.
	 */
	private StackPane constructStatusPane() {

		StackPane stacked = new StackPane();

		for(IElevatorModel e : m_Controller.getBuilding().getElevators()){
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

			Label lb_cpcty = new Label("Capacity [pers]:");
			lb_cpcty.setStyle(m_LabelStyle);
			stats.add(lb_cpcty, 0, 6);

			// labels holding values
			Label lb_pl_cntnd = new Label(Integer.toString(e.getWeight()));
			lb_pl_cntnd.setStyle(m_LabelStyle);
			lb_pl_cntnd.setId("lbStatsWeight_" + e.getNum());
			e.addWeightPropertyChangeListener(pr -> lb_pl_cntnd.setText(Integer.toString((int)pr.getNewValue())));
			stats.add(lb_pl_cntnd, 1, 1);

			Label lb_speed_cntnd  = new Label(Integer.toString(e.getSpeed()));
			lb_speed_cntnd.setStyle(m_LabelStyle);
			lb_speed_cntnd.setId("lbStatsSpeed_" + e.getNum());
			e.addSpeedPropertyChangeListener(pr -> lb_speed_cntnd.setText(Integer.toString((int)pr.getNewValue())));
			stats.add(lb_speed_cntnd , 1, 2);

			Label lb_target_cntnd  = new Label(Integer.toString(e.getTarget()));
			lb_target_cntnd.setStyle(m_LabelStyle);
			lb_target_cntnd.setId("lbStatsTarget_" + e.getNum());
			e.addTargetPropertyChangeListener(pr -> lb_target_cntnd.setText(Integer.toString((int)pr.getNewValue())));
			stats.add(lb_target_cntnd , 1, 3);

			IElevatorModel.DoorStatus s = e.getDoorStatus();
			String doorStat = "";
			if(s == IElevatorModel.DoorStatus.CLOSED) doorStat = "Closed";
			else if(s == IElevatorModel.DoorStatus.CLOSING) doorStat = "Closing";
			else if(s == IElevatorModel.DoorStatus.OPEN) doorStat = "Open";
			else if(s == IElevatorModel.DoorStatus.OPENING) doorStat = "Opening";
			Label lb_doors_cntnd  = new Label(doorStat);
			lb_doors_cntnd.setStyle(m_LabelStyle);
			lb_doors_cntnd.setId("lbStatsDoor_" + e.getNum());
			e.addDoorStatusPropertyChangeListener(pr -> {
					IElevatorModel.DoorStatus st = (IElevatorModel.DoorStatus)pr.getNewValue();
					String ds = "";
					if(st == IElevatorModel.DoorStatus.CLOSED) ds = "Closed";
					else if(st == IElevatorModel.DoorStatus.CLOSING) ds = "Closing";
					else if(st == IElevatorModel.DoorStatus.OPEN) ds = "Open";
					else if(st== IElevatorModel.DoorStatus.OPENING) ds = "Opening";
					lb_doors_cntnd.setText(ds);
				}	
			);
			stats.add(lb_doors_cntnd , 1, 4);

			IElevatorModel.CommitedDirection c = e.getCommitedDirection();
			String dirStat = "";
			if(c == IElevatorModel.CommitedDirection.UP) dirStat = "Up";
			else if(c == IElevatorModel.CommitedDirection.DOWN) dirStat = "Down";
			else if(c == IElevatorModel.CommitedDirection.UNCOMMITED) dirStat = "Uncommited";
			Label lb_dir_cntnd  = new Label(dirStat);
			lb_dir_cntnd.setStyle(m_LabelStyle);
			lb_dir_cntnd.setId("lbStatsDir_" + e.getNum());
			e.addCommitedDirectionPropertyChangeListener(pr -> {
					IElevatorModel.CommitedDirection cmD = (IElevatorModel.CommitedDirection)pr.getNewValue();
					String drs = "";
					if(cmD == IElevatorModel.CommitedDirection.UP) drs = "Up";
					else if(cmD == IElevatorModel.CommitedDirection.DOWN) drs = "Down";
					else if(cmD == IElevatorModel.CommitedDirection.UNCOMMITED) drs = "Uncommited";
					lb_dir_cntnd.setText(drs);
				}	
			);
			stats.add(lb_dir_cntnd , 1, 5);


			Label lb_cpcty_cntnd  = new Label(Integer.toString(e.getCapacity())); // does never change
			lb_cpcty_cntnd.setStyle(m_LabelStyle);
			lb_cpcty_cntnd.setId("lbStatsCap_" + e.getNum());
			stats.add(lb_cpcty_cntnd, 1, 6);

			stats.setVisible(e.getNum() == 0);

			stacked.getChildren().add(stats);
		}

		return stacked;

	}

	/**
	 * Configure which elevator to show.
	 * @param i Zero based index of elevator to show.
	 */
	private void showElevator(int i) {
		if(i > m_Controller.getBuilding().getElevators().size() || i < 0)
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
	 */
	private StackPane constructManualModePane() {
		StackPane stacked = new StackPane();

		for(IElevatorModel e : m_Controller.getBuilding().getElevators()){
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
			cb_floors.setId("cbNavigateFloor_" + e.getNum());

			for(IFloorModel f : m_Controller.getBuilding().getFloors()){
				cb_floors.getItems().add(f.getNum());
			}

			cb_floors.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
					m_Controller.setTarget(m_SelectedElevator, (int)newValue);
			});
			 
			mmode.add(cb_floors, 0, 2);
			mmode.setVisible(e.getNum() == 0);
			stacked.getChildren().add(mmode);
		}

		return stacked;
	}
	
	/**
	 * @return The elevator section of the control panel.
	 */
	private StackPane constructElevatorPane() {
		StackPane stack = new StackPane();

		
		for(IElevatorModel e : m_Controller.getBuilding().getElevators()){
			GridPane elev = new GridPane();
			elev.setPadding(new Insets(10, 10, 10, 10));
			elev.setVgap(10); 
			elev.setHgap(10); 


			// ---
			// --- Elements that exist for every floor
			// ---
			for(IFloorModel f : m_Controller.getBuilding().getFloors()){

				// -- floor state buttons (not clickable)
				Button btn = new Button(Integer.toString(f.getNum()));
				btn.setId("btnFloor_" + e.getNum() + "_" + f.getNum());
				btn.setDisable(true);
				btn.setStyle(m_ButtonDefaultSyle);
				if(e.getButtons().get(f.getNum()))
					btn.setStyle(m_ButtonSelectStyle);
				btn.setOpacity(1);
				e.addButtonStatusPropertyChangeListener(pl -> { 
					if(((ArrayList<Boolean>)pl.getNewValue()).get(f.getNum()))
						btn.setStyle(m_ButtonSelectStyle);
					else
						btn.setStyle(m_ButtonDefaultSyle);
				});
				elev.add(btn, 2, m_Controller.getBuilding().getFloors().size() - f.getNum() - 1);

				// serviced checkbox
				CheckBox cb = new CheckBox("Serviced");
				cb.setId("chkServiced_" + e.getNum() + "_" + f.getNum());
				if(f.getServicedElevators().contains(e))
					cb.setSelected(true);

				f.addServicedElevatorsPropertyChangeListener(pl -> {
					if(((ArrayList<IElevatorModel>)pl.getNewValue()).contains(e))
						cb.setSelected(true);
					else
						cb.setSelected(false);
					}
				);
				cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						m_Controller.setServicesFloors(e.getNum(), f.getNum(), newValue);
					}
				});
				elev.add(cb, 5, m_Controller.getBuilding().getFloors().size() - f.getNum() - 1);

				// Up/Down Arrows
				Polygon upArrow = new Polygon();
				upArrow.setStroke(Color.BLACK);
				upArrow.getPoints().addAll(new Double[]{
					5.0, 0.0,
					0.0, 10.0,
					10.0, 10.0 });
				elev.add(upArrow, 3, m_Controller.getBuilding().getFloors().size() - f.getNum() - 1);
				upArrow.setFill(Color.WHITE);
				if(f.getButtonUpPressed())
					upArrow.setFill(Color.BLUE);
				f.addButtonUpPropertyChangeListener(pl -> {
					if((boolean)pl.getNewValue())
						upArrow.setFill(Color.BLUE);
					else
						upArrow.setFill(Color.WHITE);
					});
				upArrow.setId("upBtnArrow_" + e.getNum() + "_" + f.getNum());

				Polygon downArrow = new Polygon();
				downArrow.setStroke(Color.BLACK);
				downArrow.getPoints().addAll(new Double[]{
					0.0, 0.0,
					10.0, 0.0,
					5.0, 10.0 });
				downArrow.setFill(Color.WHITE);
				if(f.getButtonUpPressed())
					downArrow.setFill(Color.BLUE);
				f.addButtonDownPropertyChangeListener(pl -> {
					if((boolean)pl.getNewValue())
						downArrow.setFill(Color.BLUE);
					else
						downArrow.setFill(Color.WHITE);
				 });
				 downArrow.setId("downBtnArrow_" + e.getNum() + "_" + f.getNum());
					
				elev.add(downArrow, 4, m_Controller.getBuilding().getFloors().size() - f.getNum() - 1);
			}

			// --- Big Up/Down Arrows
			Polygon bigUpArrow = new Polygon();
			bigUpArrow.setStroke(Color.BLACK);
			bigUpArrow.getPoints().addAll(new Double[]{
				15.0, 0.0,
				0.0, 30.0,
				30.0, 30.0 });
			elev.add(bigUpArrow, 0, 0, 1, m_Controller.getBuilding().getFloors().size() / 2);
			bigUpArrow.setFill(Color.WHITE);
			if(e.getCommitedDirection() == CommitedDirection.UP)
				bigUpArrow.setFill(Color.BLUE);
			GridPane.setValignment(bigUpArrow, VPos.BOTTOM);
			bigUpArrow.setId("upArrowComittedDir_" + e.getNum());

			Polygon bigDownArrow = new Polygon();
			bigDownArrow.setStroke(Color.BLACK);
			bigDownArrow.getPoints().addAll(new Double[]{
				0.0, 0.0,
				30.0, 0.0,
				15.0, 30.0 });
			elev.add(bigDownArrow, 0, m_Controller.getBuilding().getFloors().size() / 2, 1, m_Controller.getBuilding().getFloors().size() / 2);
			bigDownArrow.setFill(Color.WHITE);
			if(e.getCommitedDirection() == CommitedDirection.DOWN)
				bigDownArrow.setFill(Color.BLUE);
			GridPane.setValignment(bigDownArrow, VPos.TOP);
			bigDownArrow.setId("downArrowComittedDir_" + e.getNum());


			e.addCommitedDirectionPropertyChangeListener(pl -> {
				bigDownArrow.setFill(Color.WHITE);
				bigUpArrow.setFill(Color.WHITE);
				if((CommitedDirection)pl.getNewValue() == CommitedDirection.DOWN)
					bigDownArrow.setFill(Color.BLUE);
				else if((CommitedDirection)pl.getNewValue() == CommitedDirection.UP)
					bigUpArrow.setFill(Color.BLUE);
			 });

			// --- Manual Mode / Auto mode buttons
			Button btn_auto = new Button("Automatic");
			Button btn_manual = new Button("  Manual  ");

			if(e.getAutomaticMode()){
				btn_auto.setStyle(m_ButtonSelectStyle);
				btn_manual.setStyle(m_ButtonDefaultSyle);
			}else{
				btn_auto.setStyle(m_ButtonDefaultSyle);
				btn_manual.setStyle(m_ButtonSelectStyle);
			}

			btn_auto.setId("btnAutomaticMode_" + e.getNum());
			btn_auto.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					e.setAutomaticMode(true);
				}
			});

			btn_manual.setId("btnManualMode_" + e.getNum());
			btn_manual.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					e.setAutomaticMode(false);
				}
			});

			e.addAutomaticModePropertyChangeListener(pl -> {
				if((boolean)pl.getNewValue()){
					btn_auto.setStyle(m_ButtonSelectStyle);
					btn_manual.setStyle(m_ButtonDefaultSyle);
				}
				else{
					btn_auto.setStyle(m_ButtonDefaultSyle);
					btn_manual.setStyle(m_ButtonSelectStyle);
				}
			});
			elev.add(btn_auto, 0, m_Controller.getBuilding().getFloors().size(), 3, 1);
			elev.add(btn_manual, 3, m_Controller.getBuilding().getFloors().size(), 3, 1);


			// -- Vertical Elevator slider
			Slider slider = new Slider();
			slider.setOrientation(Orientation.VERTICAL);
			GridPane.setValignment(slider, VPos.CENTER);
			slider.setMin(0);
			slider.setMax(m_Controller.getBuilding().getFloors().size() - 1);
			slider.setId("sliPosition_" + e.getNum());
			slider.setDisable(true);
			slider.setOpacity(1);
			elev.add(slider, 1, 0, 1, m_Controller.getBuilding().getFloors().size());
			e.addFloorPositionPropertyChangeListener(pl -> slider.setValue((int)pl.getNewValue()));

			elev.setVisible(0 == e.getNum());
			stack.getChildren().add(elev);
		}

		return stack;
	}

	/**
	 * @return The elevator section of the control panel.
	 */
	private GridPane constructElevatorSelectionPane() {
		GridPane sel = new GridPane();
		sel.setPadding(new Insets(10, 10, 10, 10));
		sel.setVgap(10); 
		sel.setHgap(10); 
		sel.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3))));

		final ToggleGroup group = new ToggleGroup();
		for(IElevatorModel e : m_Controller.getBuilding().getElevators()){
			RadioButton rb = new RadioButton();
			rb.setSelected(e.getNum() == 0);
			rb.setToggleGroup(group);
			rb.setText("Elevator " + e.getNum());
			rb.setId("m_rbSelectElevator_" + e.getNum());
			rb.setUserData(e.getNum());
			sel.add(rb, 0, e.getNum());
			m_rbSelectElevator.add(rb);
		}

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			@Override
			public void changed(ObservableValue<? extends Toggle> ov,
				Toggle old_toggle, Toggle new_toggle) {
					if (group.getSelectedToggle() != null) {
							showElevator((int)group.getSelectedToggle().getUserData());
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
		m_taErrorMessages.setMaxWidth(200);
		
		m_Controller.getBuilding().addErrorPropertyChangeListener(pl -> m_taErrorMessages.setText(m_taErrorMessages.getText() + "\n" + (String)pl.getNewValue()));
		m_taErrorMessages.setId("m_taErrorMessage");

		err.add(m_taErrorMessages, 0, 1);
		return err;
	}
	
	@Override
	public Scene getScene() {
		return m_Scene;
	}

	@Override 
	public void setController(IElevatorController c){
		m_Controller = c;
	}
	
}
