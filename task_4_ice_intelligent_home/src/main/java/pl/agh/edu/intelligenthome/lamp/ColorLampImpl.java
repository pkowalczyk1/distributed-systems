package pl.agh.edu.intelligenthome.lamp;

import com.zeroc.Ice.Current;
import pl.agh.edu.intelligenthome.generated.smarthome.ColorLamp;
import pl.agh.edu.intelligenthome.generated.smarthome.LampColor;

import static pl.agh.edu.intelligenthome.generated.smarthome.LampColor.WHITE;

public class ColorLampImpl extends LampImpl implements ColorLamp {

    private LampColor color;

    public ColorLampImpl(String name) {
        super(name);
        this.color = WHITE;
    }

    @Override
    public void setColor(LampColor color, Current current) {
        this.color = color;
    }

    @Override
    public LampColor getColor(Current current) {
        return color;
    }
}
