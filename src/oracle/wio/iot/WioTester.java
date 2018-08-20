package oracle.wio.iot;

import java.util.Random;

import oracle.wio.iot.sensor.Buzzer;
import oracle.wio.iot.sensor.Humidity;
import oracle.wio.iot.sensor.LedBar;
import oracle.wio.iot.sensor.Luminance;
import oracle.wio.iot.sensor.Temperature;
import oracle.wio.iot.event.WioEvent;
import oracle.wio.iot.sensor.Grove;
import oracle.wio.iot.sensor.GroveInputSensor;


public class WioTester {
    public WioTester() {
        super();
    }

    public static void main(String[] args) throws WioException, InterruptedException {
        WioTester wioTester = new WioTester();
        
        
        /*LedBar l1 = new LedBar();
        for (int i = 0; i < 5; i++)
        {
            Random r = new Random(System.currentTimeMillis());
            int rand = r.nextInt(1023);
            l1.setValue(rand);

        }
        l1.setValue(0);
        
        Buzzer b = new Buzzer();
        b.setValue(430,1000);
        
        String json = WioUtilities.getInstance().read(WioConstants.LUMINANCE_INSTANCE_NAME, WioConstants.LUMINANCE_PROPERTY_NAME);
        System.out.println(json);
        Luminance l = new Luminance(json);
        System.out.println("val :" + l.getValue().floatValue());

        json = WioUtilities.getInstance().read(WioConstants.TEMPERATURE_INSTANCE_NAME, WioConstants.TEMPERATURE_PROPERTY_NAME);
        System.out.println(json);
        Temperature t = new Temperature(json);
        System.out.println("val :" + t.getValue().floatValue());

        json = WioUtilities.getInstance().read(WioConstants.HUMIDITY_INSTANCE_NAME, WioConstants.HUMIDITY_PROPERTY_NAME);
        System.out.println(json);
        Humidity h = new Humidity(json);
        System.out.println("val :" + h.getValue().floatValue());*/
        WioStream stream = new WioStream();
        stream.start();
        
        //WioEvent e = new WioEvent();
        //e. addListener("onWioEvent", (event) -> onWioEvent(event));
        stream.wioEvent.addListener("onWioEvent", (event) -> onWioEvent(event));

    }
    
    public static void onWioEvent(Grove e) {
        GroveInputSensor s = (GroveInputSensor) e;
        System.out.println("Got Event - " + s.getProperty() + ", value: " + s.getValue());
    }
}
