package oracle.wio.iot.sensor;

public abstract class GroveInputSensor extends Grove {

	public GroveInputSensor(String json) {
		super(json);
	}

	public abstract Double getValue();

	public abstract String getIoTAttr();
}
