package pl.agh.edu.intelligenthome.shutters;

import com.zeroc.Ice.Current;
import pl.agh.edu.intelligenthome.generated.smarthome.InvalidShutterPositionException;
import pl.agh.edu.intelligenthome.generated.smarthome.Shutters;

public class ShuttersImpl implements Shutters {

    public static final int MAX_POSITION = 100;
    public static final int MIN_POSITION = 0;

    private final String name;
    private int position;

    public ShuttersImpl(String name) {
        this.name = name;
        this.position = MIN_POSITION;
    }

    @Override
    public String getName(Current current) {
        return name;
    }

    @Override
    public void setPosition(int position, Current current) throws InvalidShutterPositionException {
        if (position < MIN_POSITION || position > MAX_POSITION) {
            throw new InvalidShutterPositionException();
        }

        this.position = position;
    }

    @Override
    public int getPosition(Current current) {
        return position;
    }

    @Override
    public void moveUp(int change, Current current) {
        position = Math.min(position + change, MAX_POSITION);
    }

    @Override
    public void moveDown(int change, Current current) {
        position = Math.max(position + change, MIN_POSITION);
    }
}
