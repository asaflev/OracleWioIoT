package oracle.wio.iot.sensor;

import oracle.wio.iot.WioConstants;

public class Temperature extends GroveInputSensor {
    public Temperature(String json) {
        super(json);
    }

    @Override
    public String getInstanceName() {
        // TODO Implement this method
        return WioConstants.TEMPERATURE_INSTANCE_NAME;
    }

    @Override
    public String getProperty() {
        // TODO Implement this method
        return WioConstants.TEMPERATURE_PROPERTY_NAME;
    }
    

    @Override
    public Double getValue() {
        
        return (Double)_jsonObject.get(WioConstants.TEMPERATURE_JSON_NAME);
    }

    @Override
    public String getIoTAttr() {
        // TODO Implement this method
        return WioConstants.IOT_TEMP_ATTR;
    }
}
