package oracle.wio.iot.iotcs;

import java.io.IOException;

import java.security.GeneralSecurityException;

import java.util.Date;

import oracle.iot.client.DeviceModel;
import oracle.iot.client.device.Alert;
import oracle.iot.client.device.DirectlyConnectedDevice;

import oracle.iot.client.device.VirtualDevice;

import oracle.wio.iot.WioConstants;
import oracle.wio.iot.WioStream;
import oracle.wio.iot.sensor.Grove;
import oracle.wio.iot.sensor.GroveInputSensor;

public class WioIoTDevice {

    DirectlyConnectedDevice _wioDevice;
    VirtualDevice _virtualDevice;

    public WioIoTDevice() {
        super();
    }

    public void initiateDevice(String configFilePath, String configFilePassword) throws GeneralSecurityException,
                                                                                        IOException {
        _wioDevice = new DirectlyConnectedDevice(configFilePath, configFilePassword);

        // Activate the device
        if (!_wioDevice.isActivated()) {
            System.out.println("Activating...");
            _wioDevice.activate(WioConstants.WIO_DEVICE_URN);
        }

        // The "real" device
        String endpointID = _wioDevice.getEndpointId();

        // Create a virtual device implementing the device model
        DeviceModel deviceModel = _wioDevice.getDeviceModel(WioConstants.WIO_DEVICE_URN);

        _virtualDevice = _wioDevice.createVirtualDevice(endpointID, deviceModel);

        /*
          * Monitor the virtual device for requested attribute changes and
          * errors.
          *
          * Since there is only one attribute, maxThreshold, this could have
          * done with using an attribute specific on change handler.
          */
        _virtualDevice.setOnChange(new VirtualDevice.ChangeCallback<VirtualDevice>() {
            public void onChange(VirtualDevice.ChangeEvent<VirtualDevice> event) {

                VirtualDevice virtualDevice = event.getVirtualDevice();
                VirtualDevice.NamedValue<?> namedValues = event.getNamedValue();
                StringBuilder msg = new StringBuilder(new java.util.Date().toString());
                msg.append(" : ");
                msg.append(virtualDevice.getEndpointId());
                msg.append(" : onChange : ");

                boolean first = true;
                for (VirtualDevice.NamedValue<?> namedValue = namedValues; namedValue != null;
                     namedValue = namedValue.next()) {
                    final String attribute = namedValue.getName();
                    final Object value = namedValue.getValue();
                    msg.append("\"")
                       .append(attribute)
                       .append("\" not implemented");
                }

                System.out.println(msg.toString());
            }
        });


        WioStream stream = new WioStream();
        stream.start();

        //WioEvent e = new WioEvent();
        //e. addListener("onWioEvent", (event) -> onWioEvent(event));
        stream.wioEvent.addListener("onWioEvent", (event) -> onWioEvent(event));
    }
  


    public void onWioEvent(Grove e) {
        GroveInputSensor s = (GroveInputSensor) e;
        System.out.println("Got Event - " + s.getProperty() + ", value: " + s.getValue());
        
        _virtualDevice.update().set(s.getIoTAttr(), s.getValue().floatValue()).finish();
    }
    // add 132.145.41.47  iotlab.us.oracle.com to /etc/hosts
    // keytool -import -alias ca -file somecert.cer -keystore cacerts -storepass changeit [Return]
    // keytool -import -alias iot -file /Users/alev/ApplicationIOT/OracleWioIOT/lib/iotserver.cer -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/security/cacerts -storepass changeit
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        WioIoTDevice device = new WioIoTDevice();
        device.initiateDevice("/Users/alev/Downloads/london333.csv", "London333");
    }
}
