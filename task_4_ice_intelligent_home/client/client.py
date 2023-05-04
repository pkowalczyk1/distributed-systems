import sys
import Ice
import smarthome

servers_config = {
    "LampsServer": "tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z",
    "OtherDevicesServer": "tcp -h 127.0.0.3 -p 10000 -z : udp -h 127.0.0.3 -p 10000 -z"
}

device_proxy_by_device_identity = {}

all_device_infos = []

BULBS_COUNT = 5


def filter_devices_by_identity_and_validate(name, category):
    filtered_devices = list(
        filter(lambda device: device.name == name and device.category == category, all_device_infos))
    if len(filtered_devices) == 0:
        print("No device with given name in this category!")
    return filtered_devices


def print_all_device_infos(servers):
    for server in servers:
        device_infos = server.getDeviceInfos()
        all_device_infos.extend(device_infos)
        for device_info in device_infos:
            print("- name: " + device_info.name + ", category: " + device_info.category)


def handle_normal_lamp(communicator):
    while True:
        print("Available actions: changeState, getState")
        device_name = input("Give lamp name or exit to leave this category: ")
        if device_name == "exit":
            return
        device_identity = "normalLamp/" + device_name
        filtered_devices = filter_devices_by_identity_and_validate(device_name, "normalLamp")
        if len(filtered_devices) == 0:
            continue
        device = filtered_devices[0]
        if device_identity not in device_proxy_by_device_identity.keys():
            base = communicator.stringToProxy(device_identity + ": " + servers_config[device.serverName])
            new_device_proxy = smarthome.LampPrx.checkedCast(base)
            device_proxy_by_device_identity[device_identity] = new_device_proxy
        device_proxy = device_proxy_by_device_identity[device_identity]
        command = input("Give command: ")
        if command == "changeState":
            device_proxy.changeState()
        elif command == "getState":
            print(device_proxy.getState())
        else:
            print("Unknown command!")


def handle_color_lamp(communicator):
    while True:
        print("Available actions: changeState, getState, getColor, setColor")
        device_name = input("Give lamp name or exit to leave this category: ")
        if device_name == "exit":
            return
        device_identity = "colorLamp/" + device_name
        filtered_devices = filter_devices_by_identity_and_validate(device_name, "colorLamp")
        if len(filtered_devices) == 0:
            continue
        device = filtered_devices[0]
        if device_identity not in device_proxy_by_device_identity.keys():
            base = communicator.stringToProxy(device_identity + ": " + servers_config[device.serverName])
            new_device_proxy = smarthome.ColorLampPrx.checkedCast(base)
            device_proxy_by_device_identity[device_identity] = new_device_proxy
        device_proxy = device_proxy_by_device_identity[device_identity]
        command = input("Give command: ")
        if command == "changeState":
            device_proxy.changeState()
        elif command == "getState":
            print(device_proxy.getState())
        elif command == "getColor":
            print(device_proxy.getColor())
        elif command == "changeColor":
            try:
                color = input("Give color (RED, GREEN, BLUE, WHITE, ORANGE): ")
                device_proxy.setColor(getattr(smarthome.LampColor, color))
            except Exception as e:
                print("Invalid color!")
                print(e)
        else:
            print("Unknown command!")


def handle_dimmable_lamp(communicator):
    while True:
        print("Available actions: changeState, getState, setOneBulbBrightness, getOneBulbBrightness, "
              "setAllBulbsBrightness, getAllBulbsBrightness")
        device_name = input("Give lamp name or exit to leave this category: ")
        if device_name == "exit":
            return
        device_identity = "dimmableLamp/" + device_name
        filtered_devices = filter_devices_by_identity_and_validate(device_name, "dimmableLamp")
        if len(filtered_devices) == 0:
            continue
        device = filtered_devices[0]
        if device_identity not in device_proxy_by_device_identity.keys():
            base = communicator.stringToProxy(device_identity + ": " + servers_config[device.serverName])
            new_device_proxy = smarthome.MultipleDimmableLampPrx.checkedCast(base)
            device_proxy_by_device_identity[device_identity] = new_device_proxy
        device_proxy = device_proxy_by_device_identity[device_identity]
        command = input("Give command: ")
        if command == "changeState":
            device_proxy.changeState()
        elif command == "getState":
            print(device_proxy.getState())
        elif command == "setOneBulbBrightness":
            bulb_id = int(input("Give bulb id (0-4): "))
            brightness = int(input("Give brightness in percent: "))
            try:
                device_proxy.setOneBulbBrightness(bulb_id, brightness)
            except smarthome.InvalidBrightnessValueException:
                print("Brightness must be between 0 and 100")
            except smarthome.InvalidBulbIdException:
                print("Bulb id must be between 0 and 4")
        elif command == "getOneBulbBrightness":
            bulb_id = int(input("Give bulb id (0-4): "))
            print(device_proxy.getBulbBrightness(bulb_id))
        elif command == "setAllBulbsBrightness":
            brightness_list = []
            for i in range(BULBS_COUNT):
                brightness_list.append(int(input("Give brightness for bulb " + str(i) + "(in percent): ")))
            try:
                device_proxy.setAllBulbsBrightness(brightness_list)
            except smarthome.InvalidBrightnessValueException:
                print("Brightness must be between 0 and 1")
        elif command == "getAllBulbsBrightness":
            print(device_proxy.getAllBulbsBrightness())
        else:
            print("Unknown command!")


def handle_vacuum_cleaner(communicator):
    while True:
        print("Available actions: changeState, getState, getPosition, setPosition")
        device_name = input("Give vacuum cleaner name or exit to leave this category: ")
        if device_name == "exit":
            return
        device_identity = "vacuum/" + device_name
        filtered_devices = filter_devices_by_identity_and_validate(device_name, "vacuum")
        if len(filtered_devices) == 0:
            continue
        device = filtered_devices[0]
        if device_identity not in device_proxy_by_device_identity.keys():
            base = communicator.stringToProxy(device_identity + ": " + servers_config[device.serverName])
            new_device_proxy = smarthome.VacuumCleanerPrx.checkedCast(base)
            device_proxy_by_device_identity[device_identity] = new_device_proxy
        device_proxy = device_proxy_by_device_identity[device_identity]
        command = input("Give command: ")
        if command == "changeState":
            device_proxy.changeState()
        elif command == "getState":
            print(device_proxy.getState())
        elif command == "getPosition":
            print("x: " + str(device_proxy.getPosition().xPosition) + ", y: " +
                  str(device_proxy.getPosition().yPosition))
        elif command == "setPosition":
            x_position = int(input("Give x coordinate: "))
            y_position = int(input("Give y coordinate: "))
            device_proxy.setPosition(smarthome.Position(x_position, y_position))
        else:
            print("Unknown command!")


def handle_shutters(communicator):
    while True:
        print("Available actions: getPosition, setPosition, moveUp, moveDown")
        device_name = input("Give shutters name or exit to leave this category: ")
        if device_name == "exit":
            return
        device_identity = "shutters/" + device_name
        filtered_devices = filter_devices_by_identity_and_validate(device_name, "shutters")
        if len(filtered_devices) == 0:
            continue
        device = filtered_devices[0]
        if device_identity not in device_proxy_by_device_identity.keys():
            base = communicator.stringToProxy(device_identity + ": " + servers_config[device.serverName])
            new_device_proxy = smarthome.ShuttersPrx.checkedCast(base)
            device_proxy_by_device_identity[device_identity] = new_device_proxy
        device_proxy = device_proxy_by_device_identity[device_identity]
        command = input("Give command: ")
        if command == "getPosition":
            print(device_proxy.getPosition())
        elif command == "setPosition":
            position = int(input("Give position in percent: "))
            try:
                device_proxy.setPosition(position)
            except smarthome.InvalidShutterPositionException:
                print("Shutter position must be between 0 and 100")
        elif command == "moveUp":
            change = int(input("Give up position change in percent: "))
            device_proxy.moveUp(change)
        elif command == "moveDown":
            change = int(input("Give down position change in percent: "))
            device_proxy.moveUp(change)
        else:
            print("Unknown command!")


if __name__ == "__main__":
    with Ice.initialize(sys.argv) as communicator:
        server_infos_proxys = []
        base = communicator.propertyToProxy("LampsServer.Proxy")
        lamps_server = smarthome.DeviceInfoProviderPrx.checkedCast(base)
        if not lamps_server:
            raise RuntimeError("Cannot create lamps server proxy!")
        server_infos_proxys.append(lamps_server)

        base = communicator.propertyToProxy("OtherDevicesServer.Proxy")
        other_devices_server = smarthome.DeviceInfoProviderPrx.checkedCast(base)
        if not other_devices_server:
            raise RuntimeError("Cannot create other devices server proxy!")
        server_infos_proxys.append(other_devices_server)

        for server in server_infos_proxys:
            device_infos = server.getDeviceInfos()
            all_device_infos.extend(device_infos)
            print("Devices available in server " + device_infos[0].serverName + ":")
            for device_info in device_infos:
                print("- name: " + device_info.name + ", category: " + device_info.category)

        while True:
            device_category = input("Give device category or info to list all devices: ")
            if device_category == "normalLamp":
                handle_normal_lamp(communicator)
            elif device_category == "colorLamp":
                handle_color_lamp(communicator)
            elif device_category == "dimmableLamp":
                handle_dimmable_lamp(communicator)
            elif device_category == "vacuum":
                handle_vacuum_cleaner(communicator)
            elif device_category == "shutters":
                handle_shutters(communicator)
            elif device_category == "info":
                print_all_device_infos(server_infos_proxys)
            else:
                print("Invalid device category!")
