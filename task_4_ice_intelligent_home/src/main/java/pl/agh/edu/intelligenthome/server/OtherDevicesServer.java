package pl.agh.edu.intelligenthome.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import pl.agh.edu.intelligenthome.generated.smarthome.DeviceInfo;
import pl.agh.edu.intelligenthome.shutters.ShuttersImpl;
import pl.agh.edu.intelligenthome.vacuum.VacuumCleanerImpl;

import java.util.ArrayList;
import java.util.List;

public class OtherDevicesServer {

    public static final String SERVER_NAME = "OtherDevicesServer";

    public static void main(String[] args) {
        OtherDevicesServer otherDevicesServer = new OtherDevicesServer();
        otherDevicesServer.startServer(args);
    }

    private void startServer(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            communicator = Util.initialize(args);
            ObjectAdapter otherDevicesAdapter = communicator.createObjectAdapter("OtherDevicesAdapter");

            List<DeviceInfo> deviceInfos = new ArrayList<>();

            String vacuumCleanerId = "vacuumCleaner";
            VacuumCleanerImpl vacuumCleaner = new VacuumCleanerImpl(vacuumCleanerId);
            String vacuumCategory = "vacuum";
            deviceInfos.add(new DeviceInfo(vacuumCleanerId, vacuumCategory, SERVER_NAME));

            String livingRoomShuttersId = "livingRoomShutters";
            String bedroomShuttersId = "bedroomShutters";
            ShuttersImpl livingRoomShutters = new ShuttersImpl(livingRoomShuttersId);
            ShuttersImpl bedroomShutters = new ShuttersImpl(bedroomShuttersId);
            String shuttersCategory = "shutters";
            deviceInfos.add(new DeviceInfo(livingRoomShuttersId, shuttersCategory, SERVER_NAME));
            deviceInfos.add(new DeviceInfo(bedroomShuttersId, shuttersCategory, SERVER_NAME));

            DeviceInfoProviderImpl deviceInfoProvider = new DeviceInfoProviderImpl(deviceInfos);

            otherDevicesAdapter.add(vacuumCleaner, new Identity(vacuumCleanerId, vacuumCategory));
            otherDevicesAdapter.add(livingRoomShutters, new Identity(livingRoomShuttersId, shuttersCategory));
            otherDevicesAdapter.add(bedroomShutters, new Identity(bedroomShuttersId, shuttersCategory));

            otherDevicesAdapter.add(deviceInfoProvider, new Identity("deviceInfoProvider", "serverInfo"));

            otherDevicesAdapter.activate();
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
