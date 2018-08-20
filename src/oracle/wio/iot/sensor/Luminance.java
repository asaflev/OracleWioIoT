package oracle.wio.iot.sensor;

import oracle.wio.iot.WioConstants;

public class Luminance extends GroveInputSensor {
    public Luminance(String json) {
        super(json);
    }

    @Override
    public String getInstanceName() {
        // TODO Implement this method
        return WioConstants.LUMINANCE_INSTANCE_NAME;
    }

    @Override
    public String getProperty() {
        // TODO Implement this method
        return WioConstants.LUMINANCE_PROPERTY_NAME;
    }
    

    @Override
    public Double getValue() {
        
        return (Double)_jsonObject.get(WioConstants.LUMINANCE_JSON_NAME);
    }

    @Override
    public String getIoTAttr() {
        // TODO Implement this method
        return WioConstants.IOT_LIGHT_ATTR;
    }
}
