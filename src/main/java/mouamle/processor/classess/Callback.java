package mouamle.processor.classess;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.Serializable;

public class Callback implements Serializable {

    private final CallbackQuery callbackQuery;

    private final String valueType;
    private final String filedName;
    private final String valueKey;
    private final String value;

    public Callback(String valueType, String filedName, String valueKey, String value, CallbackQuery callbackQuery) {
        this.valueType = valueType;
        this.filedName = filedName;
        this.valueKey = valueKey;
        this.value = value;
        this.callbackQuery = callbackQuery;
    }

    public String getValueType() {
        return valueType;
    }

    public String getFiledName() {
        return filedName;
    }

    public String getValueKey() {
        return valueKey;
    }

    public String getValue() {
        return value;
    }

    public CallbackQuery getCallbackQuery() {
        return callbackQuery;
    }


    @Override
    public String toString() {
        return "Callback{" +
                "valueType='" + valueType + '\'' +
                ", filedName='" + filedName + '\'' +
                ", valueKey='" + valueKey + '\'' +
                ", value='" + value + '\'' +
                ", callbackQuery=" + callbackQuery +
                '}';
    }
}
