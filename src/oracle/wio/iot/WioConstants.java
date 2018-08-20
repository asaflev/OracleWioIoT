package oracle.wio.iot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WioConstants {
    public static final String WIO_DEVICE_URN = "urn:com:oracle:code:iotws:wio";
    
    public static final String WIO_URL = "https://us.wio.seeed.io/";
    public static final String WIO_TOKEN = "1b723214a52a4e6ba0690f4fa35e40bb"; 
    
    public final static String LUMINANCE_INSTANCE_NAME = "GroveLuminanceA0";
    public final static String LUMINANCE_PROPERTY_NAME = "luminance";
    public final static String LUMINANCE_JSON_NAME = "lux";

    public final static String TEMPERATURE_INSTANCE_NAME = "GroveTempHumD0";
    public final static String TEMPERATURE_PROPERTY_NAME = "temperature";
    public final static String TEMPERATURE_JSON_NAME = "celsius_degree";

    public final static String HUMIDITY_INSTANCE_NAME = "GroveTempHumD0";
    public final static String HUMIDITY_PROPERTY_NAME = "humidity";
    public final static String HUMIDITY_JSON_NAME = "humidity";

    public final static String BUZZER_INSTANCE_NAME = "GroveSpeakerD0";
    public final static String BUZZER_PROPERTY_NAME = "sound_ms";

    public final static String LEDBAR_INSTANCE_NAME = "GroveLEDBarUART0";
    public final static String LEDBAR_PROPERTY_NAME = "bits";

    public final static String IOT_LIGHT_ATTR = "light";
    public final static String IOT_HUMIDITY_ATTR = "humidity";
    public final static String IOT_TEMP_ATTR = "temperature";

    public final static int WIO_STREAM_FREQ = 50;
    public static  JSONArray WIO_IOT_DEVICE; 
    
    static {
        try {
            WIO_IOT_DEVICE =
                (JSONArray) (new JSONParser())
                .parse("[\n" + 
                "\n" + 
                "  {\n" + 
                "    \"type\": \"INPUT\",\n" + 
                "    \"pin\": \"GroveLuminanceA0\",\n" + 
                "    \"property\": \"luminance\",\n" + 
                "    \"attr\": \"light\",\n" + 
                "    \"val\": 0\n" + 
                "  },\n" + 
                "  {\n" + 
                "   \"type\": \"INPUT\",\n" + 
                "    \"pin\": \"GroveTempHumD1\",\n" + 
                "    \"property\": \"humidity\",\n" + 
                "    \"attr\": \"humidity\",\n" + 
                "    \"val\": 0\n" + 
                "  },\n" + 
                "  {\n" + 
                "   \"type\": \"INPUT\",\n" + 
                "    \"pin\": \"GroveTempHumD1\",\n" + 
                "    \"property\": \"temperature\",\n" + 
                "    \"attr\": \"temperature\",\n" + 
                "    \"val\": 0\n" + 
                "  },\n" + 
                "  {\n" + 
                "    \"type\": \"OUTPUT\",  \n" + 
                "    \"pin\": \"GroveLEDBarUART0\",\n" + 
                "    \"property\": \"bits\",\n" + 
                "    \"attr\": \"lightlevel\",\n" + 
                "    \"val\": \"0\"\n" + 
                "  },\n" + 
                "  {\n" + 
                "    \"type\": \"OUTPUT\",  \n" + 
                "    \"pin\": \"GroveSpeakerD0\",\n" + 
                "    \"property\": \"sound_ms\",\n" + 
                "    \"attr\": \"soundfreq\",\n" + 
                "    \"val\": \"0\"\n" + 
                "  }\n" + 
                "]");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    
}
