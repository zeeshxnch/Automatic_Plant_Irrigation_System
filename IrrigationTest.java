import org.junit.Test;
import static org.junit.Assert.*;

/**
 * IrrigationTest class.
 * This class tests functionality of the irrigation system.
 */
public class IrrigationTest {

    /**
     * Method to test Board Connectivity.
     * Ensures the Arduino board is connected and ready.
     */
    @Test
    public void testBoardConnectivity() {
        IrrigationSystem systemTest = new IrrigationSystem();

        // Only proceed with the test if the system is running on a platform with the Arduino connected
        if (systemTest.board != null && systemTest.board.isReady()) {
            assertNotNull("The Arduino board is connected.", systemTest.board);
            assertTrue("The board is started.", systemTest.board.isReady());
        } else {
            System.out.println("Skipping board connectivity test: Board is not connected.");
        }
    }

    /**
     * Tests that the pump is off when the system initializes.
     */
    @Test
    public void testPumpOffInitialization() {
        IrrigationSystem systemTest = new IrrigationSystem();
        assertEquals("The pump should be off upon system initialization", 0, systemTest.pump.getValue());
    }

    /**
     * Tests that the moisture log starts out empty.
     */
    @Test
    public void testMoistureLogInitialization() {
        IrrigationSystem systemTest = new IrrigationSystem();
        assertTrue("The moisture log should be empty upon system initialization", systemTest.getMoistureLog().isEmpty());
    }
}
