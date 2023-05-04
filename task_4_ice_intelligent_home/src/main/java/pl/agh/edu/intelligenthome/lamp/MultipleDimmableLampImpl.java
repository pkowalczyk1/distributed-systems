package pl.agh.edu.intelligenthome.lamp;

import com.zeroc.Ice.Current;
import pl.agh.edu.intelligenthome.generated.smarthome.InvalidBrightnessValueException;
import pl.agh.edu.intelligenthome.generated.smarthome.InvalidBulbIdException;
import pl.agh.edu.intelligenthome.generated.smarthome.InvalidBulbsCountException;
import pl.agh.edu.intelligenthome.generated.smarthome.MultipleDimmableLamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MultipleDimmableLampImpl extends LampImpl implements MultipleDimmableLamp {

    private static final int BULBS_COUNT = 5;
    public static final int MAX_BRIGHTNESS = 100;
    public static final int MIN_BRIGHTNESS = 0;

    private final List<Integer> brightnessList;

    public MultipleDimmableLampImpl(String name) {
        super(name);
        this.brightnessList = new ArrayList<>(BULBS_COUNT);
        for (int i = MIN_BRIGHTNESS; i < BULBS_COUNT; i++) {
            brightnessList.add(MAX_BRIGHTNESS);
        }
    }

    @Override
    public void setOneBulbBrightness(int bulbId, int brightness, Current current)
            throws InvalidBrightnessValueException, InvalidBulbIdException {
        if (bulbId < MIN_BRIGHTNESS || bulbId >= BULBS_COUNT) {
            throw new InvalidBulbIdException();
        }

        if (brightness < MIN_BRIGHTNESS || brightness > MAX_BRIGHTNESS) {
            throw new InvalidBrightnessValueException();
        }

        brightnessList.set(bulbId, brightness);
    }

    @Override
    public void setAllBulbsBrightness(int[] brightnessList, Current current)
            throws InvalidBrightnessValueException, InvalidBulbsCountException {
        var newBrightnessList = Arrays.stream(brightnessList).boxed().toList();
        if (newBrightnessList.size() != BULBS_COUNT) {
            throw new InvalidBulbsCountException();
        }

        if (newBrightnessList.stream().anyMatch(value -> value < MIN_BRIGHTNESS || value > MAX_BRIGHTNESS)) {
            throw new InvalidBrightnessValueException();
        }

        IntStream.range(MIN_BRIGHTNESS, newBrightnessList.size())
                .forEach(index -> this.brightnessList.set(index, newBrightnessList.get(index)));
    }

    @Override
    public int getBulbBrightness(int bulbId, Current current) throws InvalidBulbIdException {
        if (bulbId < MIN_BRIGHTNESS || bulbId >= BULBS_COUNT) {
            throw new InvalidBulbIdException();
        }

        return brightnessList.get(bulbId);
    }

    @Override
    public int[] getAllBulbsBrightness(Current current) {
        return brightnessList.stream().mapToInt(Integer::intValue).toArray();
    }
}
