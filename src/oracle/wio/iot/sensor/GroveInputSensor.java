package oracle.wio.iot.sensor;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class  GroveInputSensor extends Grove {
        
    public GroveInputSensor(String json) {
        super(json);
    }
    public abstract Double getValue();
    
    public abstract String getIoTAttr();
}

