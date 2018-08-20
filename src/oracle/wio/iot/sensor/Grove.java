package oracle.wio.iot.sensor;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class Grove {
    protected String _json;
    protected JSONObject _jsonObject;
    protected JSONParser _parser = new JSONParser();
    public Grove() 
    {
    }

    public Grove(String json) {
        this();
        setJson(json);
    }

    public void setJson(String json) {
        _json = json;
        try {
            _jsonObject = (JSONObject) _parser.parse(_json);
        } catch (ParseException e) {
        }
        
    }
    
    public abstract String getInstanceName();

    public abstract String getProperty();

}
