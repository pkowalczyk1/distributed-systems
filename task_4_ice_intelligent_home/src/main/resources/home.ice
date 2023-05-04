[["java:package:pl.agh.edu.intelligenthome.generated"]]
module smarthome {

    interface Device {
        string getName();
    };

    enum DeviceState {
        ON,
        OFF
    };

    interface Lamp extends Device {
        void changeState();
        idempotent DeviceState getState();
    };

    enum LampColor {
        RED,
        GREEN,
        BLUE,
        WHITE,
        ORANGE
    };

    interface ColorLamp extends Lamp {
        idempotent void setColor(LampColor color);
        idempotent LampColor getColor();
    };

    exception InvalidBrightnessValueException{};
    exception InvalidBulbIdException{};
    exception InvalidBulbsCountException{};

    sequence<int> BrightnessList;

    interface MultipleDimmableLamp extends Lamp {
        idempotent void setOneBulbBrightness(int bulbId, int brightness) throws InvalidBulbIdException,
            InvalidBrightnessValueException;
        idempotent void setAllBulbsBrightness(BrightnessList brightnessList) throws InvalidBrightnessValueException,
            InvalidBulbsCountException;
        idempotent int getBulbBrightness(int bulbId) throws InvalidBulbIdException;
        idempotent BrightnessList getAllBulbsBrightness();
    };

    exception InvalidShutterPositionException{};

    interface Shutters extends Device {
        idempotent void setPosition(int position) throws InvalidShutterPositionException;
        idempotent int getPosition();
        void moveUp(int change);
        void moveDown(int change);
    };

    struct Position {
        int xPosition;
        int yPosition;
    };

    interface VacuumCleaner extends Device {
        idempotent void setPosition(Position position);
        idempotent Position getPosition();
        idempotent void setState(DeviceState state);
        idempotent DeviceState getState();
    };

    struct DeviceInfo {
        string name;
        string category;
        string serverName;
    };

    sequence<DeviceInfo> DeviceInfos;

    interface DeviceInfoProvider {
        idempotent DeviceInfos getDeviceInfos();
    };
};