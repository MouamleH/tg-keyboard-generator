[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d5d2f57503aa41f6b89d8901aeb093e4)](https://www.codacy.com/manual/MouamleH/tg-keyboard-generator?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=MouamleH/tg-keyboard-generator&amp;utm_campaign=Badge_Grade)
[![](https://jitpack.io/v/MouamleH/tg-keyboard-generator.svg)](https://jitpack.io/#MouamleH/tg-keyboard-generator)

# TG Keyboard Generator

Generates Inline/Reply keyboards from classes using annotations

# Requirements
[Rubenlagus Telegram bots library](https://github.com/rubenlagus/TelegramBots) (For the CallbackQuery model)

# installation
[From GitHub Package Registry](https://github.com/MouamleH/tg-keyboard-generator/packages)

# Usage
First you need a class that holds your keyboard template
```java
public class Data {

    @IntValue(key = "Age")
    private int age;

    @StringValue(key = "Color", values = {"r", "g", "b", "a"})
    private String color;

    @IntValue(key = "Size")
    private int size;

    @ButtonValue(text = "Click me", callbackText = "Me clicked :D")
    private Object buttonTest;

    @ButtonValue(text = "Click me 2", callbackText = "Me clicked :D", key = "me2")
    private Object buttonTest2;
    
}

```

Then you generate the keyboard by calling `KeyboardGenerator.generateKeyboard` 
passing an instance of the template object as a parameter

```java
List<List<ButtonHolder>> buttons = KeyboardGenerator.getInstance().generateKeyboard(new Data());
```

Notice that the method returns a List of List of ButtonHolder, 
the ButtonHolder is just a class that holds the button name and the CallbackData of the button as a String
that you can use however you want


For example you can convert it to a List of List of InlineKeyboardButton and then pass it to an InlineKeyboardMarkup
```java
List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

List<List<ButtonHolder>> buttons = KeyboardGenerator.getInstance().generateKeyboard(new Data());
for (List<ButtonHolder> button : buttons) {
    List<InlineKeyboardButton> row = new ArrayList<>();
    for (ButtonHolder holder : button) {
        row.add(new InlineKeyboardButton(holder.getText()).setCallbackData(holder.getData()));
    }
    keyboard.add(row);
}
```

After sending the keyboard you need a way to handle the key presses,
For that you need a class that gets annotated with `@KeyboardCallback` and a couple of 
annotated methods to receive the actual Callback object

This is an example of a callback handler for the template class that we provided at the top
```java
@KeyboardCallback(name = "dataCallback")
public class DataCallback {

    @ValueCallback(valueKey = "me2")
    public void buttonPressed(Callback callback) {
        System.out.println("me2 button: " + callback);
    }

    @ValueCallback(valueKey = "Age")
    public void ageCallback(Callback callback) {
        System.out.println("Age: " + callback);
    }

    @ValueCallback(valueKey = "Color")
    public void colorCallbacck(Callback callback) {
        System.out.println("Color: " + callback);
    }

    @ValueCallback
    public void stringCallback(Callback callback) {
        System.out.println("All: " + callback);
    }
    
}

```


## Available Field annotations
used on the fields of the keyboard template
```java
// used for a normal button with a callback
@ButtonValue(
        key = "the value accessed by the callback",
        text = "the text to be displayed",
        callbackText = "the text that will be sent to the callback"
        )

// used for a Integer type fields
@IntValue(
        key = "the name to be displayed and the value accessed by the callback", 
        increment = "an integer representing the value of increment to the integer",
        decrement = "an integer representing the value of decrement to the integer",
        addHeader = "true if you want a header to be displayed, otherwise false"
        )

// used for String type fields
@StringValue(
        key = "the name to be displayed and the value accessed by the callback",
        values = {"An array containing the possible values for this field"},
        orientation = "The orientation of the text StringValue.Horizontal or StringValue.Vertical", 
        addHeader = "true if you want a header to be displayed, otherwise false"
        )
        
// used for list of buttons
@ButtonGroupValue(
        key = "The value accessed by the callback",
        texts = {"An array containing the buttons texts"},
        callbacks = {"An array containing the callbacks for each text"}, // length must match texts[] length
        orientation = "The orientation of the text ButtonGroupValue.Horizontal or ButtonGroupValue.Vertical"
        )
```

Read more in the [wiki](https://github.com/MouamleH/tg-keyboard-generator/wiki)



## Available callback method annotations

used on the methods of the callbacks classes
```java
// used on callback methods
@ValueCallback(
        valueKey = "Used for linking this callback to a value annotation"
        )
```

# Registering your own annotation
First you need the Annotation itself 

Then you need a class that implements `AnnotationHandler<A>` taking the Annotation as a type 

Here I'm using @IntValue as an example

The Annotation
```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntValue {
    String key();

    int increment() default 1;
    int decrement() default 1;
}
```

The handler for the IntValue 

When setting the callbackData of the button you need to follow the same template<br>
\<Annotation name\>;\<Field name\>;\<The value key to be matched with the @ValueCallback valueKey\>;\<The data value you want to receive\>

```java
public class IntValueHandler implements AnnotationHandler<IntValue> {

    @Override
    public List<List<ButtonHolder>> generate(IntValue intValue, Object o, Field field) throws Exception {
        String valueKey = intValue.key();

        List<List<ButtonHolder>> part = new ArrayList<>();
        addValueHeader(valueKey, part);

        List<ButtonHolder> row = new ArrayList<>();

        String decrementData = String.format("IntValue;%s;%s;-%s", field.getName(), valueKey, intValue.decrement());
        String incrementData = String.format("IntValue;%s;%s;+%s", field.getName(), valueKey, intValue.increment());

        row.add(new ButtonHolder("-", decrementData));
        row.add(new ButtonHolder(String.valueOf(field.getInt(o)), noData));
        row.add(new ButtonHolder("+", incrementData));

        part.add(row);

        return part;
    }
}
```

After doing so you need to register the Annotation alongside its handler in the AnnotationsRegistry like so
```java
AnnotationsRegistry.registerAnnotation(IntValue.class.getName(), new IntValueHandler());
```

# Contribution
Feel free to submit a PR if you add a new functionality or new Annotations


### You can find a complete example in the [tests](https://github.com/MouamleH/tg-keyboard-generator/tree/master/src/test/java/keyboard)
