import edu.princeton.cs.introcs.StdDraw;
import java.util.HashMap;
import java.util.List;

/**
 * MoistureGraph class.
 * Provides functionality to visualize moisture data over time.
 */
public class MoistureGraph { // Looked at James A smith video about graphs in java for help

    /**
     * Plots the moisture data collected from the soil over time.
     * This method draws axes, labels, and moisture points using StdDraw.
     *
     * @param moistureData a list of integer values representing moisture levels
     */
    public static void plot(List<Integer> moistureData) {
        HashMap<Integer, Integer> dataMap = new HashMap<>();

        // Convert List to HashMap (time step as x, moisture value as y)
        for (int time = 0; time < moistureData.size(); time++) {
            dataMap.put(time, moistureData.get(time));
        }

        // Set canvas size and scale with padding
        StdDraw.setCanvasSize(800, 600);
        int xMax = Math.max(10, moistureData.size());
        StdDraw.setXscale(-1, xMax + 1);
        StdDraw.setYscale(-50, 850);

        // Clear canvas and enable double buffering
        StdDraw.clear();

        // Draw axis lines
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.002);
        StdDraw.line(0, 0, xMax, 0); // X-axis
        StdDraw.line(0, 0, 0, 800); // Y-axis

        // Draw X-axis ticks and labels
        for (int x = 0; x <= xMax; x += 1) {
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(x, -10, x, 10);
            if (x % 5 == 0) {
                StdDraw.text(x, -30, String.valueOf(x));
            }
        }

        // Draw Y-axis ticks and numeric labels
        for (int y = 0; y <= 800; y += 100) {
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(-0.1, y, 0.25, y);
            StdDraw.text(-0.5, y, Integer.toString(y));
        }

        // Axis labels
        StdDraw.setPenRadius();
        StdDraw.text(xMax / 2.0, -60, "Time (seconds)");
        StdDraw.text(-1, 800 / 2.0, "Moisture", 90);

        // Title
        StdDraw.text(xMax / 2.0, 830, "Soil Moisture Over Time");

        // Plot points
        StdDraw.setPenRadius(0.01);
        dataMap.forEach((time, value) -> StdDraw.point(time, value));

        // Show buffered drawing
        StdDraw.show();
    }
}
