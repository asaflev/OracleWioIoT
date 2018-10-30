package oracle.wio.iot.sensor;

import oracle.wio.iot.WioException;

public abstract class GroveOutputSensor extends Grove {

	public GroveOutputSensor(String json) {
		super(json);
	}

	public GroveOutputSensor() {
		super();
	}

	public abstract void setValue(int... args) throws WioException;
}
