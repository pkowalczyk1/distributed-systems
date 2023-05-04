package pl.agh.edu.intelligenthome.lamp;

import com.zeroc.Ice.Current;
import pl.agh.edu.intelligenthome.generated.smarthome.Lamp;
import pl.agh.edu.intelligenthome.generated.smarthome.DeviceState;

import static pl.agh.edu.intelligenthome.generated.smarthome.DeviceState.OFF;
import static pl.agh.edu.intelligenthome.generated.smarthome.DeviceState.ON;

public class LampImpl implements Lamp {

    private final String name;
    private DeviceState state;

    public LampImpl(String name) {
        this.name = name;
        this.state = OFF;
    }

    @Override
    public String getName(Current current) {
        return name;
    }

    @Override
    public void changeState(Current current) {
        if (state == ON) {
            state = OFF;
        } else {
            state = ON;
        }
    }

    @Override
    public DeviceState getState(Current current) {
        return state;
    }
}
