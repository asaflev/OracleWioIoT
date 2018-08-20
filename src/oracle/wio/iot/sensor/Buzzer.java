package oracle.wio.iot.sensor;

import oracle.wio.iot.WioConstants;
import oracle.wio.iot.WioException;
import oracle.wio.iot.WioUtilities;

public class Buzzer extends GroveOutputSensor {
    public Buzzer(String json) {
        super(json);
    }
    public Buzzer() {
        super();
    }

    @Override
    public String getInstanceName() {
        // TODO Implement this method
        return WioConstants.BUZZER_INSTANCE_NAME;
    }

    @Override
    public String getProperty() {
        // TODO Implement this method
        return WioConstants.BUZZER_PROPERTY_NAME;
    }
    

    @Override
    public void setValue(int... args) throws WioException {
        
        WioUtilities.getInstance().write(WioConstants.BUZZER_INSTANCE_NAME, WioConstants.BUZZER_PROPERTY_NAME, new String[] {Integer.toString(args[0]), Integer.toString(args[1])});
        //return (Double)_jsonObject.get(WioConstants.TEMPERATURE_JSON_NAME);
    }
}
