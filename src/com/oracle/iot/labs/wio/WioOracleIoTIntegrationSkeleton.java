package com.oracle.iot.labs.wio;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

import oracle.iot.client.DeviceModel;
import oracle.iot.client.device.DirectlyConnectedDevice;
import oracle.iot.client.device.VirtualDevice;
import oracle.wio.iot.WioConstants;
import oracle.wio.iot.WioStream;
import oracle.wio.iot.WioUtilities;
import oracle.wio.iot.sensor.Grove;
import oracle.wio.iot.sensor.GroveInputSensor;
import oracle.wio.iot.sensor.Luminance;

public class WioOracleIoTIntegrationSkeleton implements WioLabConstants {
	public final static float LUMINANCE_THRESHOLD = 500;
	private final static Logger logger = Logger.getLogger(WioOracleIoTIntegrationSkeleton.class.getName());
	private DirectlyConnectedDevice wioDirectlyConnectedDevice;
	private String wioDeviceEndpointId;
	private DeviceModel wioDeviceModel;
	private VirtualDevice wioVirtualDevice;

	public WioOracleIoTIntegrationSkeleton(String provisioningFilePath, String provisioningFilePassword)
			throws GeneralSecurityException, IOException {
		// Create a directly connected device using the provisioning file details. This
		// knows how to communicate with the IoT Cloud service.
		wioDirectlyConnectedDevice = new DirectlyConnectedDevice(provisioningFilePath, provisioningFilePassword);
		// If the device has not been activated do so, registering it to use the
		// WIO_DEVICE_URN as the data formats
		if (!wioDirectlyConnectedDevice.isActivated()) {
			wioDirectlyConnectedDevice.activate(WioConstants.WIO_DEVICE_URN);
		}
		// Get the identifier of the device
		wioDeviceEndpointId = wioDirectlyConnectedDevice.getEndpointId();
		// Use the directly connected device to get the device model (data format) for
		// the Wio
		wioDeviceModel = wioDirectlyConnectedDevice.getDeviceModel(WioConstants.WIO_DEVICE_URN);
		// Create a virtual device form the endpoint and the model. The virtual device
		// an be used to update the values in the IoT Cloud service, and also to receive
		// commands to be forwarded to the Wio. It can also support Alerts, IoT
		// initiated attribute value changes and additional data formats if desired, but
		// for this exercise we're not using those.
		wioVirtualDevice = wioDirectlyConnectedDevice.createVirtualDevice(wioDeviceEndpointId, wioDeviceModel);
	}

	private void registerIoTCommandListeners() {
		// We are only registered for a single virtual device, so we will only get
		// callbacks for that specific device, thus we can ignore the virtual device
		// data we're provided with here. If we had multiple virtual devices we'd need
		// to keep track of them and work out which device was relevant
		//
		// We're using a Lambda here, but anything that implements the Callable
		// interface is fine (including anonymous inner classes)
		wioVirtualDevice.setCallable(IOT_BUZZER_COMMAND,
				(VirtualDevice vDev, String buzzerData) -> processBuzzerCmd(buzzerData));
		// TODO
		// Register for the Led Bar command that calls the processLedBarCmd command.
		// A lambda is easiest, but you could use an anonymous inner class if you liked.
		// (WioLabConstants has a constant of the appropriate name to use)
	}

	/**
	 * This decodes the String from the IoT cloud service and splits it into
	 * frequency and duration for us
	 * 
	 * @param buzzerData
	 */
	private void processBuzzerCmd(String buzzerData) {
		// the full buzzer data is a string frequency / ms duration.
		int frequency = 450;
		int duration = 1000;
		// extract the data
		if (buzzerData == null) {
			logger.warning("buzzerData is null, cannot process this");
			return;
		}
		logger.info("processing buzzerData " + buzzerData);
		// remove whitespace ;
		String buzzerFields[] = buzzerData.trim().split("/");
		// there should be two fields ;
		if (buzzerFields.length != 2) {
			logger.warning("buzzerData (" + buzzerData + ") is not two fields split by / cannot proceed");
			return;
		}
		try {
			frequency = Integer.parseInt(buzzerFields[0].trim());
		} catch (NumberFormatException nfe) {
			logger.warning("Cannot parse frequency field (" + buzzerFields[0].trim()
					+ ") as an integer, cannot proceed, " + nfe.getMessage());
			return;
		}
		try {
			duration = Integer.parseInt(buzzerFields[1].trim());
		} catch (NumberFormatException nfe) {
			logger.warning("Cannot parse duration field (" + buzzerFields[1].trim()
					+ ") as an integer, cannot proceed, " + nfe.getMessage());
			return;
		}
		processBuzzer(frequency, duration);
	}

	/**
	 * Actually cause the buzzer to sound
	 * 
	 * @param frequency
	 * @param duration
	 */
	public void processBuzzer(int frequency, int duration) {
		// TODO
		// Take the frequency and duration values and sent them to the wio
		// (this of course will only do anything if you've got the Buzzer setup on the
		// left port)
	}

	/**
	 * This decodes the string from the IoT cloud service and converts it into the
	 * LED display value for us
	 * 
	 * @param ledBarData
	 */
	@SuppressWarnings("unused")
	private void processLedBarCmd(String ledBarData) {
		// set the led bad data, if it's null or doesn't parse as an Integer, warn and
		// give up
		// if it's empty default to 0 (turns the LEDs off)
		if (ledBarData == null) {
			logger.warning("ledBarData is null, cannot process this");
			return;
		}
		logger.info("processing ledBarData " + ledBarData);
		// remove whitespace ;
		ledBarData = ledBarData.trim();
		int leds = 0;
		if (ledBarData.length() != 0) {
			try {
				leds = Integer.parseInt(ledBarData);
			} catch (NumberFormatException nfe) {
				logger.warning(
						"ledBarData (" + ledBarData + ") does not parse as an integer, cannot process this command");
				return;
			}
		}
		processLedBar(leds);
	}

	/**
	 * 
	 * Trigger the LED bar to display
	 * 
	 * @param leds
	 */
	public void processLedBar(int leds) {
		// TODO
		// Take the leds value and sent it to the wio
		// (this of course will only do anything if you've got the LED bar setup on the
		// left port in place of the buzzer)
	}

	public static void main(String args[]) throws GeneralSecurityException, IOException {
		if (args.length < 2) {
			logger.severe("Must run this program with arguments <provisioning file path> <provisioning file password>");
			System.exit(-1);
		}
		logger.info("Starting");
		// load in the security stuff
		WioUtilities.getInstance().readWioProperties(WIO_CONFIG_FILE);
		logger.info("Read wio device properties");
		WioStream stream = new WioStream();
		logger.info("Created WIoStream");
		stream.start();
		logger.info("Started Stream");
		// setup the
		String provisioningFilePath = args[0];
		String provisioningFilePassword = args[1];
		// create the monitor to process the data
		// Note that we would normally do something to handle the possible exceptions
		// but for simplicity we just throw them all
		WioOracleIoTIntegrationSkeleton monitor = new WioOracleIoTIntegrationSkeleton(provisioningFilePath,
				provisioningFilePassword);
		stream.wioEvent.addListener("onWioEvent", (event) -> monitor.onWioEvent(event));
		logger.info("Added Wio eventListener");
		monitor.registerIoTCommandListeners();
		logger.info("Registered IoT callbacks");
	}

	/**
	 * Process the events from the Wio stream and do something with them
	 * 
	 * @param e
	 */
	public void onWioEvent(Grove e) {
		GroveInputSensor s = (GroveInputSensor) e;
		logger.info("Got Event - " + s.getProperty() + ", value: " + s.getValue());
		if (s instanceof Luminance) {
			Luminance l = (Luminance) s;
			wioVirtualDevice.set(IOT_ATTRIBUTE_LIGHT, l.getValue().floatValue());
		}
		// TODO If given a Humidity or Temperature event then set the IoT values
		// appropriately,
		// the WioLabConstants has constants for the attribute names.
	}
}
