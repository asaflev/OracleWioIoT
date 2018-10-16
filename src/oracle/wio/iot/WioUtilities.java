package oracle.wio.iot;

import java.io.FileReader;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import oracle.wio.iot.sensor.LedBar;

public class WioUtilities {
	private final static Logger logger = Logger.getLogger(WioUtilities.class.getName());
	private static WioUtilities _instance = null;
	private Client _restClient = ClientBuilder.newClient();
	private String _wioServerURL = WioConstants.WIO_URL;
	private String _wioToken = WioConstants.WIO_TOKEN;
	private JSONArray _wioIoTDevice = WioConstants.WIO_IOT_DEVICE;

	private WioUtilities() {
	}

	public static WioUtilities getInstance() {
		if (_instance == null)
			_instance = new WioUtilities();
		return _instance;
	}

	public String read(String groveInstanceName, String property) throws WioException {
		String url = _wioServerURL + "v1/node/" + groveInstanceName + "/" + property + "?access_token=" + _wioToken;
		WebTarget target = _restClient.target(url);
		logger.finer("Read request URL is " + url);
		Response response = target.request().header("Authorization", "token " + _wioToken)
				.header("Content-Type", "application/x-www-form-urlencoded").get();
		if (response.getStatus() == 200) {
			return response.readEntity(String.class);
		} else {
			throw new WioException(response.readEntity(String.class));
		}
	}

	public String write(String groveInstanceName, String property, String... args) throws WioException {
		String url = _wioServerURL + "v1/node/" + groveInstanceName + "/" + property + "/" + String.join("/", args)
				+ "?access_token=" + _wioToken;
		WebTarget target = _restClient.target(url);
		logger.finer("Write request URL is " + url);
		Response response = target.request().header("Authorization", "token " + _wioToken)
				.header("Content-Type", "application/x-www-form-urlencoded").post(null);
		if (response.getStatus() == 200) {
			return response.readEntity(String.class);
		} else {
			throw new WioException(response.readEntity(String.class));
		}
	}

	public void readWioProperties(String filename) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(filename));

			JSONObject jsonObject = (JSONObject) obj;
			Set keys = jsonObject.keySet();

			if (keys.contains("location"))
				_wioServerURL = (String) jsonObject.get("location");
			if (keys.contains("access_token"))
				_wioToken = (String) jsonObject.get("access_token");
			if (keys.contains("iot_device"))
				_wioIoTDevice = (JSONArray) jsonObject.get("iot_device");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crazyLed() throws WioException {
		LedBar l1 = new LedBar();
		for (int i = 0; i < 5; i++) {
			Random r = new Random(System.currentTimeMillis());
			int rand = r.nextInt(1023);
			l1.setValue(rand);

		}
		l1.setValue(0);

	}
}
