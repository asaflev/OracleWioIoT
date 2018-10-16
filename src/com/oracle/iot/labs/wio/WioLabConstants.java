package com.oracle.iot.labs.wio;

public interface WioLabConstants {
	public final static String WIO_CONFIG_FILE = "wioConfig.json";
	// Names of the IoT attributes we can set
	public final static String IOT_ATTRIBUTE_LIGHT = "light";
	public final static String IOT_ATTRIBUTE_TEMPERATURE = "temperature";
	public final static String IOT_ATTRIBUTE_HUMIDITY = "humidity";
	// names of the commands we get from IoT
	public final static String IOT_BUZZER_COMMAND = "buzzer";
	public final static String IOT_LEDBAR_COMMAND = "ledbar";
}
