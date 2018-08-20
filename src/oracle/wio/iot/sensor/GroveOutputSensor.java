package oracle.wio.iot.sensor;

import oracle.wio.iot.WioException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class  GroveOutputSensor extends Grove {
        
    public GroveOutputSensor(String json) {
        super(json);
    }
    public GroveOutputSensor() {
        super();
    }
    public abstract void setValue(int... args) throws WioException;
}

