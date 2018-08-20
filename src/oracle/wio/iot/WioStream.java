package oracle.wio.iot;

import oracle.wio.iot.sensor.Humidity;
import oracle.wio.iot.sensor.Luminance;
import oracle.wio.iot.sensor.Temperature;
import oracle.wio.iot.event.WioEvent;
import oracle.wio.iot.sensor.Grove;

public class WioStream extends Thread {
    public WioStream() {
        super();
    }
    @FunctionalInterface
    public interface WioDelegate
    {
        void invoke(Grove object);
    }
    public WioEvent<WioDelegate> wioEvent = new WioEvent<WioDelegate>();
    
    private void RaiseWioEvent(Grove obj)
       {
           //invoke all listeners:
           for (WioDelegate listener : wioEvent.listeners())
           {
               listener.invoke(obj);
           }
       }
    public void run() {

        // Checking whether the thread is Daemon or not
        if (Thread.currentThread().isDaemon()) {
            System.out.println("Daemon thread executing");
        } else {
            System.out.println("user(normal) thread executing");
        }
        String json;
        while (true) {

            try {
                json =
                    WioUtilities.getInstance()
                    .read(WioConstants.LUMINANCE_INSTANCE_NAME, WioConstants.LUMINANCE_PROPERTY_NAME);
                System.out.println(json);
                Luminance l = new Luminance(json);
                System.out.println("val :" + l.getValue().floatValue());
                RaiseWioEvent(l);
            } catch (WioException e) {
                //e.printStackTrace();
            }

            try {
                json =
                    WioUtilities.getInstance()
                    .read(WioConstants.TEMPERATURE_INSTANCE_NAME, WioConstants.TEMPERATURE_PROPERTY_NAME);
                System.out.println(json);
                Temperature t = new Temperature(json);
                System.out.println("val :" + t.getValue().floatValue());
                RaiseWioEvent(t);
            } catch (WioException e) {
                //e.printStackTrace();
            }

            try {
                json =
                    WioUtilities.getInstance()
                    .read(WioConstants.HUMIDITY_INSTANCE_NAME, WioConstants.HUMIDITY_PROPERTY_NAME);
                System.out.println(json);
                Humidity h = new Humidity(json);
                System.out.println("val :" + h.getValue().floatValue());
                RaiseWioEvent(h);
            } catch (WioException e) {
                //e.printStackTrace();
            }


            try {
                Thread.currentThread().sleep(WioConstants.WIO_STREAM_FREQ);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
