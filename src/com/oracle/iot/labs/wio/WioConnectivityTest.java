package com.oracle.iot.labs.wio;

import java.util.logging.Logger;

import oracle.wio.iot.WioConstants;
import oracle.wio.iot.WioException;
import oracle.wio.iot.WioUtilities;
import oracle.wio.iot.sensor.Buzzer;
import oracle.wio.iot.sensor.Luminance;

public class WioConnectivityTest implements WioLabConstants {
	private final static Logger logger = Logger.getLogger(WioConnectivityTest.class.getName());

	public static void main(String args[]) {
		logger.info("Starting");
		// load in the security stuff
		WioUtilities.getInstance().readWioProperties(WIO_CONFIG_FILE);
		logger.info("Read wio device properties");
		// Request the current luminance value
		try {
			Luminance l = new Luminance(WioUtilities.getInstance().read(WioConstants.LUMINANCE_INSTANCE_NAME,
					WioConstants.LUMINANCE_PROPERTY_NAME));
			logger.info("Retrieved luminance is " + l.getValue().floatValue());
		} catch (WioException e) {
			logger.warning("Unable to get Luminance data, is the sensor connected and configured ? " + e.getMessage());
		}
		logger.info("Sounding buzzer");
		try {
			Buzzer b = new Buzzer();
			int frequency = 450;
			int duration = 1000;
			b.setValue(frequency, duration);
			logger.info("Set buzzer data");
		} catch (WioException e) {
			logger.warning("Unable to set buzzer data, is the sensor connected and configured ? " + e.getMessage());
		}
		logger.info("Completed");
	}
}
