package oracle.wio.iot.sensor;

import oracle.wio.iot.WioConstants;

public class Humidity extends GroveInputSensor {
    public Humidity(String json) {
        super(json);
    }

    @Override
    public String getInstanceName() {
        // TODO Implement this method
        return WioConstants.HUMIDITY_INSTANCE_NAME;
    }

    @Override
    public String getProperty() {
        // TODO Implement this method
        return WioConstants.HUMIDITY_PROPERTY_NAME;
    }
    

    @Override
    public Double getValue() {
        
        return (Double)_jsonObject.get(WioConstants.HUMIDITY_JSON_NAME);
    }

    @Override
    public String getIoTAttr() {
        // TODO Implement this method
        return WioConstants.IOT_HUMIDITY_ATTR;
    }
}
