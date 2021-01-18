/**
 * @author Daniel Giritzer, S1810567004
 */
package at.fhhagenberg.sqe.elevator.wrappers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.server.RMISocketFactory;

import sqelevator.IElevator;

/**
 * Implementation of the IElevatorWrapper, which can be adjusted when the IElevator API changes.
 */
public class ElevatorWrapperImpl implements IElevatorWrapper {
    
    private IElevator m_Elev;

    public ElevatorWrapperImpl(IElevator e) {
        if(e == null)
            throw new NullPointerException("Invalid IElevator Object passed to the wrapper!");
        m_Elev = e;
    }

    public ElevatorWrapperImpl() {
    }

    public void setCustomSocketTimeout(int timeout) throws IOException {
        RMISocketFactory.setSocketFactory(new RMISocketFactory()
        {
            @Override
            public Socket createSocket(String host, int port) throws IOException {
                Socket socket = new Socket();
                socket.setSoTimeout(timeout);
                socket.setSoLinger(false, 0);
                socket.connect(new InetSocketAddress(host, port), timeout);
                return socket;
            }

            @Override
            public ServerSocket createServerSocket(int port) throws IOException {
                return RMISocketFactory.getDefaultSocketFactory().createServerSocket(port);
            }
        } );
    }

    @Override
    public void reconnect() throws java.rmi.RemoteException, java.rmi.NotBoundException, java.net.MalformedURLException {
        m_Elev = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
    }

    @Override
	public int getCommittedDirection(int elevatorNumber) throws java.rmi.RemoteException{
        return m_Elev.getCommittedDirection(elevatorNumber);
    }

    @Override
	public int getElevatorAccel(int elevatorNumber) throws java.rmi.RemoteException{
        return m_Elev.getElevatorAccel(elevatorNumber);
    }


    @Override
	public boolean getElevatorButton(int elevatorNumber, int floor) throws java.rmi.RemoteException{
        return m_Elev.getElevatorButton(elevatorNumber, floor);
    }


    @Override
	public int getElevatorDoorStatus(int elevatorNumber) throws java.rmi.RemoteException{
        return m_Elev.getElevatorDoorStatus(elevatorNumber);
    }

    @Override
	public int getElevatorFloor(int elevatorNumber) throws java.rmi.RemoteException{
        return m_Elev.getElevatorFloor(elevatorNumber);
    }


    @Override
    public int getElevatorNum() throws java.rmi.RemoteException{
        return m_Elev.getElevatorNum();
    }

    
    @Override
	public int getElevatorPosition(int elevatorNumber) throws java.rmi.RemoteException{
        return m_Elev.getElevatorPosition(elevatorNumber);
    }
 

    @Override
	public int getElevatorSpeed(int elevatorNumber) throws java.rmi.RemoteException{
        return m_Elev.getElevatorSpeed(elevatorNumber);
    }


    @Override
	public int getElevatorWeight(int elevatorNumber) throws java.rmi.RemoteException{
        return m_Elev.getElevatorWeight(elevatorNumber);
    }
 

    @Override
	public int getElevatorCapacity(int elevatorNumber) throws java.rmi.RemoteException{
        return m_Elev.getElevatorCapacity(elevatorNumber);
    }

	
    @Override
	public boolean getFloorButtonDown(int floor) throws java.rmi.RemoteException{
        return m_Elev.getFloorButtonDown(floor);
    }


    @Override
	public boolean getFloorButtonUp(int floor) throws java.rmi.RemoteException{
        return m_Elev.getFloorButtonUp(floor);
    }
 

    @Override
	public int getFloorHeight() throws java.rmi.RemoteException{
        return m_Elev.getFloorHeight();
    }


    @Override
	public int getFloorNum() throws java.rmi.RemoteException{
        return m_Elev.getFloorNum();
    }

    @Override
	public boolean getServicesFloors(int elevatorNumber, int floor) throws java.rmi.RemoteException{
        return m_Elev.getServicesFloors(elevatorNumber, floor);
    }

    @Override
	public int getTarget(int elevatorNumber) throws java.rmi.RemoteException{
        return m_Elev.getTarget(elevatorNumber);
    }

    @Override
	public void setCommittedDirection(int elevatorNumber, int direction) throws java.rmi.RemoteException{
        m_Elev.setCommittedDirection(elevatorNumber, direction);
    }



    @Override
	public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws java.rmi.RemoteException{
        m_Elev.setServicesFloors(elevatorNumber, floor, service);
    }
 


    @Override
	public void setTarget(int elevatorNumber, int target) throws java.rmi.RemoteException{
        m_Elev.setTarget(elevatorNumber, target);
    }



    @Override
	public long getClockTick() throws java.rmi.RemoteException{
        return m_Elev.getClockTick();
    }


}
