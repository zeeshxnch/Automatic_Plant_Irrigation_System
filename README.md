# Automatic_Plant_Irrigation_System

An automated Java-based embedded system designed to monitor soil moisture levels, control a water pump, and display real-time telemetry on an OLED screen. Built using the **Firmata4j** framework to interface directly with microcontroller hardware, this system features background monitoring tasks, threshold-based automated feedback loops, unit testing, and dynamic telemetry plotting.

---

## Key Features

* **Real-Time Automated Control:** Periodically samples soil moisture data via analog input and toggles a relay/pump output based on predefined threshold values (`DRY_THRESHOLD: 705`, `WET_THRESHOLD: 650`).
* **OLED Visual Status Display:** Updates an I2C SSD1306 OLED display every second showing exact moisture readings and state notifications (`Dry`, `OK`, `Wet`).
* **Data Visualization & Plotting:** Utilizes `StdDraw` to convert time-series moisture logs into a structured 2D Cartesian plot for post-run analysis.
* **Unit Testing Suite:** Includes JUnit test coverage to verify board connection readiness, pump pin default states, and log initialization.
* **Graceful Lifecycle Management:** Features clear shutdown routines to safely disable hardware timers, ensure the water pump is powered off, and terminate board connections cleanly.

---

## Project Structure

```text
├── Main.java             # Entry point; controls execution lifecycle and triggers plotting
├── IrrigationSystem.java # Core hardware initialization, pin setup, and shutdown routines
├── MoistureMonitor.java  # TimerTask executing loop for reading sensors, toggling pump, and updating display
├── MoistureGraph.java    # Data visualization renderer built with Princeton StdDraw
└── IrrigationTest.java   # JUnit 4 integration and initialization test suite
