import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import java.util.TimerTask;
import java.util.List;

/**
 * MoistureMonitor class.
 * Periodically checks soil moisture and controls the pump.
 */
public class MoistureMonitor extends TimerTask {

    private final Pin moistureSensor;
    private final Pin pump;
    private final List<Integer> moistureLog;
    private final int DRY_THRESHOLD = 705; // Threshold value for dry soil
    private final int WET_THRESHOLD = 650; // Threshold value for wet soil
    private final SSD1306 oled;

    /**
     * MoistureMonitor constructor.
     * @param moistureSensor the pin connected to the moisture sensor
     * @param pump the pin connected to the water pump
     * @param moistureLog list used to store moisture readings
     * @param oled the OLED display for showing moisture info
     */
    public MoistureMonitor(Pin moistureSensor, Pin pump, List<Integer> moistureLog, SSD1306 oled) {
        this.moistureSensor = moistureSensor;
        this.pump = pump;
        this.moistureLog = moistureLog;
        this.oled = oled;
    }

    /**
     * Periodically checks soil moisture and controls the pump.
     * Displays current status on the OLED.
     */
    @Override
    public void run() {
        try {
            // Simulate moisture value (use real sensor data in production)
            int moistureValue = (int) moistureSensor.getValue();  // Get value from sensor

            // Log the moisture value
            moistureLog.add(moistureValue);

            // Control the pump based on moisture value
            if (moistureValue > DRY_THRESHOLD) {
                pump.setValue(1); // Turn pump ON if soil is dry
            } else {
                pump.setValue(0); // Turn pump OFF if soil is wet
            }

            // Update OLED display
            displayToOLED(moistureValue);
            System.out.println("Moisture: " + moistureValue);

        } catch (IOException e) {
            System.out.println("Error reading sensor or setting pump: " + e.getMessage());
        }
    }

    /**
     * Displays current moisture value and status on the OLED.
     * @param value the moisture sensor reading
     */
    private void displayToOLED(int value) {
        oled.clear();
        String line1 = "Moisture: " + value;
        String line2;

        if (value > DRY_THRESHOLD) {
            line2 = "Status: Dry";
        } else if (value < WET_THRESHOLD) {
            line2 = "Status: Wet";
        } else {
            line2 = "Status: OK";
        }

        oled.getCanvas().drawString(0, 0, line1);
        oled.getCanvas().drawString(0, 20, line2);
        oled.display();
    }
}
