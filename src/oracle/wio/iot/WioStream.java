package oracle.wio.iot;

import java.util.logging.Logger;

import oracle.wio.iot.event.WioEvent;
import oracle.wio.iot.sensor.Grove;
import oracle.wio.iot.sensor.Humidity;
import oracle.wio.iot.sensor.Luminance;
import oracle.wio.iot.sensor.Temperature;

public class WioStream extends Thread {
	private final static Logger logger = Logger.getLogger(WioStream.class.getName());

	public WioStream() {
		super();
	}

	@FunctionalInterface
	public interface WioDelegate {
		void invoke(Grove object);
	}

	public WioEvent<WioDelegate> wioEvent = new WioEvent<WioDelegate>();

	private void RaiseWioEvent(Grove obj) {
		// invoke all listeners:
		for (WioDelegate listener : wioEvent.listeners()) {
			listener.invoke(obj);
		}
	}

	public void run() {

		// Checking whether the thread is Daemon or not
		if (Thread.currentThread().isDaemon()) {
			logger.info("Daemon thread executing");
		} else {
			logger.info("user(normal) thread executing");
		}
		String json;
		while (true) {

			try {
				json = WioUtilities.getInstance().read(WioConstants.LUMINANCE_INSTANCE_NAME,
						WioConstants.LUMINANCE_PROPERTY_NAME);
				logger.finest("Try to read luminance returned " + json);
				Luminance l = new Luminance(json);
				logger.finer("Luminance value is " + l.getValue().floatValue());
				RaiseWioEvent(l);
			} catch (WioException e) {
				// e.printStackTrace();
			}

			try {
				json = WioUtilities.getInstance().read(WioConstants.TEMPERATURE_INSTANCE_NAME,
						WioConstants.TEMPERATURE_PROPERTY_NAME);
				logger.finest("Try to read temperature returned " + json);
				Temperature t = new Temperature(json);
				logger.finer("Temperature value is " + t.getValue().floatValue());
				RaiseWioEvent(t);
			} catch (WioException e) {
				// e.printStackTrace();
			}

			try {
				json = WioUtilities.getInstance().read(WioConstants.HUMIDITY_INSTANCE_NAME,
						WioConstants.HUMIDITY_PROPERTY_NAME);
				logger.finest("Try to read humidity returned " + json);
				Humidity h = new Humidity(json);
				logger.finer("Humidity value is " + h.getValue().floatValue());
				RaiseWioEvent(h);
			} catch (WioException e) {
				// e.printStackTrace();
			}

			try {
				sleep(WioConstants.WIO_STREAM_FREQ);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
