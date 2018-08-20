package oracle.wio.iot.sensor;

import oracle.wio.iot.WioConstants;
import oracle.wio.iot.WioException;
import oracle.wio.iot.WioUtilities;

public class LedBar extends GroveOutputSensor {
    public LedBar(String json) {
        super(json);
    }
    public LedBar() {
        super();
    }

    @Override
    public String getInstanceName() {
        // TODO Implement this method
        return WioConstants.LEDBAR_INSTANCE_NAME;
    }

    @Override
    public String getProperty() {
        // TODO Implement this method
        return WioConstants.LEDBAR_PROPERTY_NAME;
    }
    

    @Override
    public void setValue(int... args) throws WioException {
        
        String[] arr = new String[args.length];
        for (int idx = 0; idx < args.length; idx++)
            arr[idx] = Integer.toString(args[idx]);
        WioUtilities.getInstance().write(WioConstants.LEDBAR_INSTANCE_NAME, WioConstants.LEDBAR_PROPERTY_NAME, arr);
    }
}
