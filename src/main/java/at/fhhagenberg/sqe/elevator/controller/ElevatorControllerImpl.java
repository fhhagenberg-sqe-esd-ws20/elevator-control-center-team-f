/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.controller;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import at.fhhagenberg.sqe.elevator.model.IBuildingModel;
import at.fhhagenberg.sqe.elevator.model.IElevatorModel;
import at.fhhagenberg.sqe.elevator.model.IFloorModel;
import at.fhhagenberg.sqe.elevator.wrappers.IElevatorWrapper;
import javafx.application.Platform;

/**
 * Controller implementation. Allows sending requests to elevator(s). Receives datachanges and stores
 * them to the Model(s).
 */
public class ElevatorControllerImpl implements IElevatorController {

    private IElevatorWrapper m_Elevator;
    private IBuildingModel m_BuildingModel;
    private IFloorModel m_FloorModel;
    private IElevatorModel m_ElevatorModel;
    private Timer m_PollingCall;
    private boolean m_Initialized = false;

    /**
     * CTor
     * @param e Elevator connection interface.
     * @param bm Building model to store all data.
     * @throws NullPointerException
     */
    public ElevatorControllerImpl(IElevatorWrapper e, IBuildingModel bm, IElevatorModel em, IFloorModel fm) throws NullPointerException {
        if(e == null)
            throw new NullPointerException("Invalid ElevatorWrapper passed.");
        if(bm == null)
            throw new NullPointerException("Invalid BuildingModel passed.");
        if(em == null)
            throw new NullPointerException("Invalid ElevatorModel passed.");
        if(fm == null)
            throw new NullPointerException("Invalid FloorModel passed.");

        m_Elevator = e;
        m_BuildingModel = bm;
        m_ElevatorModel = em;
        m_FloorModel = fm;

        maintainConnection();
    }

    @Override
    public void maintainConnection(){
        try{
            m_Elevator.reconnect();

            if(!m_Initialized){
                m_BuildingModel = m_BuildingModel.createBuildingModel(m_Elevator.getClockTick(), m_Elevator.getFloorHeight());
                for(int i = 0; i < m_Elevator.getFloorNum(); i++)
                    m_BuildingModel.getFloors().add(m_FloorModel.createFloorModel(i));

                for(int i = 0; i < m_Elevator.getElevatorNum(); i++)
                    m_BuildingModel.getElevators().add(m_ElevatorModel.createElevatorModel(m_Elevator.getElevatorCapacity(i), i, m_BuildingModel));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            m_BuildingModel.setError(ex.getMessage());
            m_BuildingModel.setConnectionState(false);
        }
        m_BuildingModel.setConnectionState(true);
        m_Initialized = true;
    }


    private void fillModel(){

        if(!m_BuildingModel.getConnectionState()){
            maintainConnection();
            return;
        }

        try{
            // update elevators
            for(IElevatorModel e : m_BuildingModel.getElevators()){

                // -- Receive Values

                e.setAccel(m_Elevator.getElevatorAccel(e.getNum()));

                int d = m_Elevator.getCommittedDirection(e.getNum());
                if(d == IElevatorWrapper.ELEVATOR_DIRECTION_UP)
                    e.setCommitedDirection(IElevatorModel.CommitedDirection.UP);
                else if(d == IElevatorWrapper.ELEVATOR_DIRECTION_DOWN)
                    e.setCommitedDirection(IElevatorModel.CommitedDirection.DOWN);
                else if(d == IElevatorWrapper.ELEVATOR_DIRECTION_UNCOMMITTED)
                    e.setCommitedDirection(IElevatorModel.CommitedDirection.UNCOMMITED);
                else
                    throw new Exception("Invalid Commited direction.");

                int ds = m_Elevator.getElevatorDoorStatus(e.getNum());
                if(ds == IElevatorWrapper.ELEVATOR_DOORS_CLOSED)
                    e.setDoorStatus(IElevatorModel.DoorStatus.CLOSED);
                else if(ds == IElevatorWrapper.ELEVATOR_DOORS_CLOSING)
                    e.setDoorStatus(IElevatorModel.DoorStatus.CLOSING);
                else if(ds == IElevatorWrapper.ELEVATOR_DOORS_OPEN)
                    e.setDoorStatus(IElevatorModel.DoorStatus.OPEN);
                else if(ds == IElevatorWrapper.ELEVATOR_DOORS_OPENING)
                    e.setDoorStatus(IElevatorModel.DoorStatus.OPENING);
                else
                    throw new Exception("Invalid Door status.");

                e.setFloorPos(m_Elevator.getElevatorFloor(e.getNum()));
                e.setPos(m_Elevator.getElevatorPosition(e.getNum()));
                e.setSpeed(m_Elevator.getElevatorSpeed(e.getNum()));
                e.setTarget(m_Elevator.getTarget(e.getNum()));
                e.setWeight(m_Elevator.getElevatorWeight(e.getNum()));

                for(IFloorModel f : m_BuildingModel.getFloors()) {
                    e.getButtons().set(f.getNum(), m_Elevator.getElevatorButton(e.getNum(), f.getNum()));
                    
                    if(m_Elevator.getServicesFloors(e.getNum(), f.getNum()) && !f.getServicedElevators().contains(e))
                        f.getServicedElevators().add(e);
                    else if(!m_Elevator.getServicesFloors(e.getNum(), f.getNum()) && f.getServicedElevators().contains(e))
                        f.getServicedElevators().remove(e);

                    f.setButtonDownPressed(m_Elevator.getFloorButtonDown(f.getNum()));
                    f.setButtonUpPressed(m_Elevator.getFloorButtonUp(f.getNum()));
                }

                // ---- Send Values

                if(e.getFloorPos() != e.getNextTargetFloor()) // fires automatic mode commands
                    m_Elevator.setTarget(e.getNum(), e.getNextTargetFloor());

                if(e.getFloorPos() < e.getTarget())
                    m_Elevator.setCommittedDirection(e.getNum(), IElevatorWrapper.ELEVATOR_DIRECTION_UP);
                else if(e.getFloorPos() > e.getTarget())
                    m_Elevator.setCommittedDirection(e.getNum(), IElevatorWrapper.ELEVATOR_DIRECTION_DOWN);
                else
                    m_Elevator.setCommittedDirection(e.getNum(), IElevatorWrapper.ELEVATOR_DIRECTION_UNCOMMITTED);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            m_BuildingModel.setError(ex.getMessage());
            m_BuildingModel.setConnectionState(false);
        }
    }

    @Override
    public void setCommittedDirection(int elevatorNumber, int direction){
        try{
            m_Elevator.setCommittedDirection(elevatorNumber, direction);
        }
        catch(Exception ex){
            ex.printStackTrace();
            m_BuildingModel.setError(ex.getMessage());
            m_BuildingModel.setConnectionState(false);
        }
    }

    @Override
    public void setServicesFloors(int elevatorNumber, int floor, boolean service){
        try{
            m_Elevator.setServicesFloors(elevatorNumber, floor, service);
        }
        catch(Exception ex){
            ex.printStackTrace();
            m_BuildingModel.setError(ex.getMessage());
            m_BuildingModel.setConnectionState(false);
        }
    }

    @Override
    public void setTarget(int elevatorNumber, int target){
        try{
            if(!m_BuildingModel.getElevators().get(elevatorNumber).getAutomaticMode())
                m_Elevator.setTarget(elevatorNumber, target);
        }
        catch(RemoteException ex){
            ex.printStackTrace();
            m_BuildingModel.setError(ex.getMessage());
            m_BuildingModel.setConnectionState(false);
        }
    }

    @Override
    public IBuildingModel getBuilding(){
        return m_BuildingModel;
    }


    /**
     * Starts polling data from the given IElevatorWrapper interface.
     */
    public void startPolling(){
        // periodic polling and setting model data
        m_PollingCall = new Timer();
        m_PollingCall.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> fillModel());
			}
		}, 0, 200);
    }

    @Override
    public void setAutomaticMode(int e, boolean m){
        m_BuildingModel.getElevators().get(e).setAutomaticMode(m);
    }
}
