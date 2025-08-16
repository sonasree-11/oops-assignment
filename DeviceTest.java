package oopsassignment1;
class Device {
    int id;
    String name;
    String type;

    // Constructor (Parameterized)
    Device(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // Display method
    void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
    }
}

class Devicemanager {
    Device[] devices;  // Array of devices
    int count;         // number of devices added

    // Constructor
    Devicemanager(int size) {
        devices = new Device[size];
        count = 0;
    }

    // Add device
    void adddevice(Device d) {
        if (count < devices.length) {
            devices[count] = d;
            count++;
        } else {
            System.out.println("Device list is full!");
        }
    }

    // Search device by ID
    Device searchbyid(int id) {
        for (int i = 0; i < count; i++) {
            if (devices[i].id == id) {
                return devices[i];
            }
        }
        return null; // return null if not found
    }
}

public class DeviceTest {

	public static void main(String[] args) {
		Devicemanager dm = new Devicemanager(5);

        // Adding devices
        dm.adddevice(new Device(101, "Printer", "Electronics"));
        dm.adddevice(new Device(102, "Scanner", "Electronics"));
        dm.adddevice(new Device(103, "Router", "Networking"));

        // Search by ID
        Device d = dm.searchbyid(102);
        

        if (d != null) {
            System.out.println("Device found:");
            d.display();
        } else {
            System.out.println("Device not found!");
        }

	}

}
