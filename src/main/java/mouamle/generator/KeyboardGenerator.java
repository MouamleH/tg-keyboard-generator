package mouamle.generator;

import mouamle.generator.classes.ButtonHolder;
import mouamle.registry.AnnotationHandler;
import mouamle.registry.AnnotationsRegistry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class to generate inline/reply keyboards for Telegram bots. </br>
 * You can use it quickly by getting the singleton instance or create an instance yourself .
 *
 * @author Mouamle H. Hameed <mouamle.hasan@gmail.com>
 */
@SuppressWarnings("unchecked")
public class KeyboardGenerator {

    private static final String noData = "*";
    private List<List<ButtonHolder>> keyboard = new ArrayList<>();

    private static KeyboardGenerator instance = new KeyboardGenerator();

    public static KeyboardGenerator getInstance() {
        return instance;
    }

    /**
     * Creates an tg-keyboard from a template object in which it's fields are annotated with @IntValue, @StringValue
     *
     * @param obj an object that it fields are annotated with @IntValue, @StringValue
     */
    public synchronized List<List<ButtonHolder>> generateKeyboard(Object obj) throws Exception {
        Class aClass = obj.getClass();

        List<List<ButtonHolder>> keyboard = new ArrayList<>();

        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                String keyName = annotation.annotationType().getName();

                Optional<AnnotationHandler> oHandler = AnnotationsRegistry.getHandler(keyName);
                if (oHandler.isPresent()) {
                    AnnotationHandler handler = oHandler.get();
                    List<List<ButtonHolder>> part = handler.generate(annotation, obj, field);
                    keyboard.addAll(part);
                }
            }
        }

        return keyboard;
    }

    /**
     * @param jsonString the template json string
     * @throws ParseException if the json string could not be parsed
     */
    public synchronized List<List<ButtonHolder>> generateKeyboardFromJson(String jsonString) throws ParseException {
        JSONArray keyboardArray = (JSONArray) new JSONParser().parse(jsonString);

        List<ButtonHolder> row = new ArrayList<>();

        for (Object o : keyboardArray) {
            JSONArray jsonRow = (JSONArray) o;
            for (Object buttonObject : jsonRow) {
                JSONObject button = (JSONObject) buttonObject;

                ButtonHolder holder = new ButtonHolder(String.valueOf(button.get("text")), String.valueOf(button.get("data")));
                row.add(holder);
            }
            keyboard.add(row);
        }

        return keyboard;
    }

}
