package com.oracle.iot.labs.wio;

import java.util.logging.Logger;

import oracle.wio.iot.WioStream;
import oracle.wio.iot.WioUtilities;
import oracle.wio.iot.sensor.Grove;
import oracle.wio.iot.sensor.GroveInputSensor;
import oracle.wio.iot.sensor.Luminance;

public class WioLuminanceMonitorSkeleton implements WioLabConstants {
	public final static float LUMINANCE_THRESHOLD = 500;
	private final static Logger logger = Logger.getLogger(WioLuminanceMonitorSkeleton.class.getName());

	public WioLuminanceMonitorSkeleton() {
	}

	public static void main(String args[]) {
		logger.info("Starting");
		// create the monitor to process the data
		WioLuminanceMonitorSkeleton monitor = new WioLuminanceMonitorSkeleton();
		// load in the security stuff
		WioUtilities.getInstance().readWioProperties(WIO_CONFIG_FILE);
		logger.info("Read wio device properties");
		WioStream stream = new WioStream();
		logger.info("Created WIoStream");
		stream.start();
		logger.info("Started Stream");
		stream.wioEvent.addListener("onWioEvent", (event) -> monitor.onWioEvent(event));
		logger.info("Added Listener");
	}

	public void onWioEvent(Grove e) {
		GroveInputSensor s = (GroveInputSensor) e;
		logger.info("Got Event - " + s.getProperty() + ", value: " + s.getValue());
		if (s instanceof Luminance) {
			// You need to get the current luminance value, test it
			// and sound the buzzer if it's to low
			// See the WioConnectivityTest for examples of how to do this
		}
	}
}
