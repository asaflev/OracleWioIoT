package com.oracle.iot.labs.wio;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.iot.client.DeviceModel;
import oracle.iot.client.device.DirectlyConnectedDevice;
import oracle.iot.client.device.VirtualDevice;
import oracle.wio.iot.WioConstants;
import oracle.wio.iot.WioException;
import oracle.wio.iot.WioStream;
import oracle.wio.iot.WioUtilities;
import oracle.wio.iot.sensor.Buzzer;
import oracle.wio.iot.sensor.Grove;
import oracle.wio.iot.sensor.GroveInputSensor;
import oracle.wio.iot.sensor.Humidity;
import oracle.wio.iot.sensor.LedBar;
import oracle.wio.iot.sensor.Luminance;
import oracle.wio.iot.sensor.Temperature;

public class WioOracleIoTIntegration implements WioLabConstants {
	public final static float LUMINANCE_THRESHOLD = 500;
	private final static Logger logger = Logger.getLogger(WioOracleIoTIntegration.class.getName());
	private DirectlyConnectedDevice wioDirectlyConnectedDevice;
	private String wioDeviceEndpointId;
	private DeviceModel wioDeviceModel;
	private VirtualDevice wioVirtualDevice;

	public WioOracleIoTIntegration(String provisioningFilePath, String provisioningFilePassword)
			throws GeneralSecurityException, IOException {
		wioDirectlyConnectedDevice = new DirectlyConnectedDevice(provisioningFilePath, provisioningFilePassword);
		if (!wioDirectlyConnectedDevice.isActivated()) {
			wioDirectlyConnectedDevice.activate(WioConstants.WIO_DEVICE_URN);
		}
		wioDeviceEndpointId = wioDirectlyConnectedDevice.getEndpointId();
		wioDeviceModel = wioDirectlyConnectedDevice.getDeviceModel(WioConstants.WIO_DEVICE_URN);
		wioVirtualDevice = wioDirectlyConnectedDevice.createVirtualDevice(wioDeviceEndpointId, wioDeviceModel);
	}

	private void registerIoTCommandListeners() {
		// We are only registered for a single virtual device, so we will only get
		// callbacks for that specific device, thus we can ignore the virtual device
		// data we're provided with here. If we had multple virtual devices we'd need to
		// keep track of them and work out which device was relevant
		wioVirtualDevice.setCallable(IOT_BUZZER_COMMAND,
				(VirtualDevice vDev, String buzzerData) -> processBuzzer(buzzerData));
		wioVirtualDevice.setCallable(IOT_LEDBAR_COMMAND,
				(VirtualDevice vDev, String ledBarData) -> processLedBar(ledBarData));
	}

	private void processBuzzer(String buzzerData) {
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
		// got the data, sound the buzzer
		Buzzer buzzer = new Buzzer();
		// send the data
		try {
			buzzer.setValue(frequency, duration);
		} catch (WioException e) {
			logger.warning("Exception sounding buzzer," + e.getMessage());
		}
	}

	private void processLedBar(String ledBarData) {
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
		LedBar ledBar = new LedBar();
		try {
			ledBar.setValue(leds);
		} catch (WioException e) {
			logger.warning("Exception setting LED's," + e.getMessage());
		}
	}

	public static void main(String args[]) {
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
		try {
			WioOracleIoTIntegration monitor = new WioOracleIoTIntegration(provisioningFilePath,
					provisioningFilePassword);
			// have to do this here, to ensure that monitor is effectively final (It's a
			// Lambda requirement.)
			stream.wioEvent.addListener("onWioEvent", (event) -> monitor.onWioEvent(event));
			logger.info("Added Wio eventListener");
			monitor.registerIoTCommandListeners();
			logger.info("Registered IoT callbacks");
		} catch (GeneralSecurityException | IOException e) {
			logger.log(Level.SEVERE, "Exception creating WioOracleIoTIntegration instance", e);
			System.exit(-2);
		}
	}

	public void onWioEvent(Grove e) {
		GroveInputSensor s = (GroveInputSensor) e;
		logger.info("Got Event - " + s.getProperty() + ", value: " + s.getValue());
		if (s instanceof Luminance) {
			Luminance l = (Luminance) s;
			wioVirtualDevice.set(IOT_ATTRIBUTE_LIGHT, l.getValue().floatValue());
		} else if (s instanceof Humidity) {
			Humidity h = (Humidity) s;
			wioVirtualDevice.set(IOT_ATTRIBUTE_HUMIDITY, h.getValue().floatValue());
		} else if (s instanceof Temperature) {
			Temperature t = (Temperature) s;
			wioVirtualDevice.set(IOT_ATTRIBUTE_TEMPERATURE, t.getValue().floatValue());
		}
	}
}
