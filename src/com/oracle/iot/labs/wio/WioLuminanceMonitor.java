package com.oracle.iot.labs.wio;

import java.util.logging.Logger;

import oracle.wio.iot.WioException;
import oracle.wio.iot.WioStream;
import oracle.wio.iot.WioUtilities;
import oracle.wio.iot.sensor.Buzzer;
import oracle.wio.iot.sensor.Grove;
import oracle.wio.iot.sensor.GroveInputSensor;
import oracle.wio.iot.sensor.Luminance;

public class WioLuminanceMonitor implements WioLabConstants {
	public final static float LUMINANCE_THRESHOLD = 500;
	private final static Logger logger = Logger.getLogger(WioLuminanceMonitor.class.getName());

	public static void main(String args[]) {
		logger.info("Starting");
		// load in the security stuff
		WioUtilities.getInstance().readWioProperties(CONFIG_FILE);
		logger.info("Read wio device properties");
		WioStream stream = new WioStream();
		logger.info("Created WIoStream");
		stream.start();
		logger.info("Started Stream");
		stream.wioEvent.addListener("onWioEvent", (event) -> onWioEvent(event));
		logger.info("Added Listener");
	}

	public static void onWioEvent(Grove e) {
		GroveInputSensor s = (GroveInputSensor) e;
		logger.info("Got Event - " + s.getProperty() + ", value: " + s.getValue());
		if (s instanceof Luminance) {
			Luminance l = (Luminance) s;
			if (l.getValue().floatValue() < LUMINANCE_THRESHOLD) {
				logger.info("Below threshold (" + LUMINANCE_THRESHOLD + ") sounding buzzer");
				try {
					Buzzer b = new Buzzer();
					int frequency = 450;
					int duration = 1000;
					b.setValue(frequency, duration);
					logger.info("Set buzzer data");
				} catch (WioException we) {
					logger.warning(
							"Unable to set buzzer data, is the sensor connected and configured ? " + we.getMessage());
				}
			}
		}
	}
}
