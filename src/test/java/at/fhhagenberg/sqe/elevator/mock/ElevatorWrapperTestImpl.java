package at.fhhagenberg.sqe.elevator.mock;

/**
 * @author Daniel Giritzer, S1810567004
 */
import at.fhhagenberg.sqe.elevator.wrappers.ElevatorWrapperImpl;
import sqelevator.IElevator;

/**
 * ElevatorWrapper for testing, does nothing on reconnect.
 */
public class ElevatorWrapperTestImpl extends ElevatorWrapperImpl {

    public ElevatorWrapperTestImpl(IElevator e){
        super(e);
    }

    public ElevatorWrapperTestImpl(){
        super();
    }

    @Override
    public void reconnect(){
        // do nothing
    }
    
}
