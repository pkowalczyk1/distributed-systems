package pl.agh.edu.intelligenthome.vacuum;

import com.zeroc.Ice.Current;
import pl.agh.edu.intelligenthome.generated.smarthome.DeviceState;
import pl.agh.edu.intelligenthome.generated.smarthome.Position;
import pl.agh.edu.intelligenthome.generated.smarthome.VacuumCleaner;

import static pl.agh.edu.intelligenthome.generated.smarthome.DeviceState.OFF;

public class VacuumCleanerImpl implements VacuumCleaner {

    private final String name;
    private Position position;
    private DeviceState state;

    public VacuumCleanerImpl(String name) {
        this.name = name;
        this.state = OFF;
        this.position = new Position(0, 0);
    }

    @Override
    public String getName(Current current) {
        return name;
    }

    @Override
    public void setPosition(Position position, Current current) {
        this.position = position;
    }

    @Override
    public Position getPosition(Current current) {
        return position;
    }

    @Override
    public void setState(DeviceState state, Current current) {
        this.state = state;
    }

    @Override
    public DeviceState getState(Current current) {
        return state;
    }
}
