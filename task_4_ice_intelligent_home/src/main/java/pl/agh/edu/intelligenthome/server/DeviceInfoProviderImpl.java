package pl.agh.edu.intelligenthome.server;

import com.zeroc.Ice.Current;
import pl.agh.edu.intelligenthome.generated.smarthome.DeviceInfo;
import pl.agh.edu.intelligenthome.generated.smarthome.DeviceInfoProvider;

import java.util.List;

public class DeviceInfoProviderImpl implements DeviceInfoProvider {

    private final List<DeviceInfo> deviceInfos;

    public DeviceInfoProviderImpl(List<DeviceInfo> deviceInfos) {
        this.deviceInfos = deviceInfos;
    }

    @Override
    public DeviceInfo[] getDeviceInfos(Current current) {
        return deviceInfos.toArray(new DeviceInfo[0]);
    }
}
