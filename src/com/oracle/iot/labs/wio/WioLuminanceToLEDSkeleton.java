package com.oracle.iot.labs.wio;

import java.util.logging.Logger;

import oracle.wio.iot.WioUtilities;
import oracle.wio.iot.sensor.Grove;
import oracle.wio.iot.sensor.GroveInputSensor;
import oracle.wio.iot.sensor.Luminance;

public class WioLuminanceToLEDSkeleton implements WioLabConstants {
	public final static float LUMINANCE_THRESHOLD = 500;
	private final static Logger logger = Logger.getLogger(WioLuminanceToLEDSkeleton.class.getName());

	public WioLuminanceToLEDSkeleton() {
	}

	public static void main(String args[]) {
		logger.info("Starting");
		// create the monitor to process the data
		WioLuminanceToLEDSkeleton monitor = new WioLuminanceToLEDSkeleton();
		// load in the security stuff
		WioUtilities.getInstance().readWioProperties(WIO_CONFIG_FILE);
		logger.info("Read wio device properties");
		// TODO
		// Setup the WioStream, start it and use a Lambda to call the monitor.onWioEvent
		// method when a "onWioEvent" is received
		// If you want call the logger to report the progress
	}

	public void onWioEvent(Grove e) {
		GroveInputSensor s = (GroveInputSensor) e;
		logger.info("Got Event - " + s.getProperty() + ", value: " + s.getValue());
		if (s instanceof Luminance) {
			// TODO
			// You need to get the current luminance value, and send it to the LED Bar
			// Remember to replace the buzzer on the Wio Node with the LED bas *AND* update
			// the firmware !
			// Note - the luminance value is a float, but the LED bad takes an int (0 -
			// 1023)
			// You may want to have a delay if the updates are to frequent
		}
	}
}
