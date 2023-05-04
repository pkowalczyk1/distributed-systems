package pl.agh.edu.intelligenthome.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import pl.agh.edu.intelligenthome.generated.smarthome.DeviceInfo;
import pl.agh.edu.intelligenthome.lamp.ColorLampImpl;
import pl.agh.edu.intelligenthome.lamp.LampImpl;
import pl.agh.edu.intelligenthome.lamp.MultipleDimmableLampImpl;

import java.util.ArrayList;
import java.util.List;

public class LampsServer {

    public static final String SERVER_NAME = "LampsServer";

    public static void main(String[] args) {
        LampsServer lampsServer = new LampsServer();
        lampsServer.startServer(args);
    }

    private void startServer(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            communicator = Util.initialize(args);
            ObjectAdapter lampsAdapter = communicator.createObjectAdapter("LampsAdapter");

            List<DeviceInfo> deviceInfos = new ArrayList<>();

            String bedroomLampId = "bedroomLamp";
            String kitchenLampId = "kitchenLamp";
            LampImpl bedroomLamp = new LampImpl(bedroomLampId);
            LampImpl kitchenLamp = new LampImpl(kitchenLampId);
            String normalLampCategory = "normalLamp";
            deviceInfos.add(new DeviceInfo(bedroomLampId, normalLampCategory, SERVER_NAME));
            deviceInfos.add(new DeviceInfo(kitchenLampId, normalLampCategory, SERVER_NAME));

            String livingRoomColorLampId = "livingRoomColorLamp";
            String bedroomColorLampId = "bedroomColorLamp";
            ColorLampImpl livingRoomColorLamp = new ColorLampImpl(livingRoomColorLampId);
            ColorLampImpl bedroomColorLamp = new ColorLampImpl(bedroomColorLampId);
            String colorLampCategory = "colorLamp";
            deviceInfos.add(new DeviceInfo(livingRoomColorLampId, colorLampCategory, SERVER_NAME));
            deviceInfos.add(new DeviceInfo(bedroomColorLampId, colorLampCategory, SERVER_NAME));

            String bathroomDimmableLampId = "bathroomDimmableLamp";
            String livingRoomDimmableLampId = "livingRoomDimmableLamp";
            MultipleDimmableLampImpl bathroomDimmableLamp = new MultipleDimmableLampImpl(bathroomDimmableLampId);
            MultipleDimmableLampImpl livingRoomDimmableLamp = new MultipleDimmableLampImpl(livingRoomDimmableLampId);
            String dimmableLampCategory = "dimmableLamp";
            deviceInfos.add(new DeviceInfo(bathroomDimmableLampId, dimmableLampCategory, SERVER_NAME));
            deviceInfos.add(new DeviceInfo(livingRoomDimmableLampId, dimmableLampCategory, SERVER_NAME));

            DeviceInfoProviderImpl deviceInfoProvider = new DeviceInfoProviderImpl(deviceInfos);

            lampsAdapter.add(bedroomLamp, new Identity(bedroomLampId, normalLampCategory));
            lampsAdapter.add(kitchenLamp, new Identity(kitchenLampId, normalLampCategory));
            lampsAdapter.add(livingRoomColorLamp, new Identity(livingRoomColorLampId, colorLampCategory));
            lampsAdapter.add(bedroomColorLamp, new Identity(bedroomColorLampId, colorLampCategory));
            lampsAdapter.add(bathroomDimmableLamp, new Identity(bathroomDimmableLampId, dimmableLampCategory));
            lampsAdapter.add(livingRoomDimmableLamp, new Identity(livingRoomDimmableLampId, dimmableLampCategory));

            lampsAdapter.add(deviceInfoProvider, new Identity("deviceInfoProvider", "serverInfo"));

            lampsAdapter.activate();
            System.out.println("Entering event processing loop...");
            communicator.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            status = 1;
        }

        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                status = 1;
            }
        }
        System.exit(status);
    }
}
