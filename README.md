# OracleWioIoT Project


<h3>event:</h3>

<b>WioEvent.java</b> - Java class that represents an incoming event from the Wio Device sensors (temp, humidity etc) new measurement 



<h3>iotcs:</h3>

<b>WioIoTDevice.java</b> - Java class that represents the VirtualDevice with in Oracle IoT Cloud Service 
<ul>
<li>Register and activate a device</li>
<li>Register to incoming messages from IoT CS</li>
<li>Register to WioEvent and send the data to the IoT CS</li>
 </ul>
<h3>sensor:</h3>

Contains the different classes that represents specific WIO sensors.
All the sensor specific classes inherit from Groove, GroveInputSensor and GroveOutputSensor  

<h2>Classes under src/oracle/wio/iot package:</h2>

<b>WioConstants.java</b> - Project constants such as WioServer URL, sensors details - name, physical name, json node name, etc.

This class also contains the JSON payload that is being sent to the Oracle IoT Cloud Service

<b>WioException.java</b> - Exception wrapper for WIO board based exceptions

<b>WioStream.java</b> - This is the main thread that listens (polls) sensor data from the connected WIO board 

<b>WioTester.java</b> - Solution tester class 

<b>WioUtilities.java</b> - WIO board REST API wrapper for sending, recieving and invoking sensor data
