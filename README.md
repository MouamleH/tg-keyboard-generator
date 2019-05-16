# Tg Keyboard Generator

Generates Inline/Reply keyboards from classes using annotations

# Requirements
[Rubenlagus Telegram bots library](https://github.com/rubenlagus/TelegramBots) (For the CallbackQuery model)

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
@KeyboardCallback
public class DataCallback {

    @ButtonValueCallback
    public void buttonPressed(Callback callback) {
        System.out.println(callback);
    }

    @IntValueCallback(valueKey = "Age")
    public void ageCallback(Callback callback) {
        System.out.println("Age: " + callback);
    }

    @IntValueCallback
    public void callback(Callback callback) {
        System.out.println("All: " + callback);
    }

    @StringValueCallback
    public void stringCallback(Callback callback) {
        System.out.println("All Strings: " + callback);
    }

}

```


## Available Field annotations
used on the fields oof the keyboard template
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
        decrement = "an integer representing the value of decrement to the integer"
        )

// used for String type fields
@StringValue(
        key = "the name to be displayed and the value accessed by the callback",
        values = {"An array containing the possible values for this field"}
        )
```

## Available callback method annotations
used on the methods of the callbacks classes
```java
@ButtonValueCallback // used on callback methods for buttons
@StringValueCallback // used on callback methods for String values
@IntValueCallback // used on callback methods for Integer values
```

### You can find a complete example in the tests