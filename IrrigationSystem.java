import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;
import org.firmata4j.I2CDevice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class IrrigationSystem {

    private final String PORT = "/dev/cu.SLAB_USBtoUART"; // Arduino Port
    private final int MOISTURE_PIN = 14; // A0 = pin 14 in Firmata
    private final int PUMP_PIN = 2;     // Digital pin 2 for pump control

    protected IODevice board;
    protected Pin moistureSensor;
    protected Pin pump;
    protected SSD1306 oled;
    protected List<Integer> moistureLog;
    private Timer timer;

    public IrrigationSystem() {
        board = new FirmataDevice(PORT);
        moistureLog = new ArrayList<>();

        try {
            board.start();
            board.ensureInitializationIsDone();
            System.out.println("Board connected.");

            // Setup moisture sensor
            moistureSensor = board.getPin(MOISTURE_PIN);
            moistureSensor.setMode(Pin.Mode.ANALOG);

            // Setup pump pin
            pump = board.getPin(PUMP_PIN);
            pump.setMode(Pin.Mode.OUTPUT);

            // Setup OLED
            I2CDevice i2c = board.getI2CDevice((byte) 0x3C);
            oled = new SSD1306(i2c, SSD1306.Size.SSD1306_128_64);
            oled.init();

            // Start periodic task
            timer = new Timer();
            timer.schedule(new MoistureMonitor(moistureSensor, pump, moistureLog, oled), 0, 1000); // Every second

        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public List<Integer> getMoistureLog() {
        return moistureLog;
    }

    public void stopSystem() {
        try {
            if (timer != null) {
                timer.cancel();
                System.out.println("Timer stopped.");
            }
            if (pump != null) {
                pump.setValue(0);
                System.out.println("Pump turned off manually.");
            }
            if (board != null && board.isReady()) {
                board.stop();
                System.out.println("Board stopped.");
            }
        } catch (IOException e) {
            System.out.println("Manual shutdown error: " + e.getMessage());
        }
    }
}
