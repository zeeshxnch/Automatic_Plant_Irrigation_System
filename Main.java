/**
 * Main class to run the irrigation system and plot data.
 */
public class Main {

    /**
     * Main entry point to run the irrigation system and plot data.
     * Initializes the system, collects data for 20 seconds, stops the system,
     * and then plots the recorded moisture values.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        IrrigationSystem system = new IrrigationSystem();

        try {
            Thread.sleep(20000); // 20 seconds for testing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.stopSystem();
        MoistureGraph.plot(system.getMoistureLog());
    }
}
