package keyboard;

import keyboard.callback.DataCallback;
import keyboard.model.Data;
import mouamle.generator.KeyboardGenerator;
import mouamle.generator.classes.ButtonHolder;
import mouamle.processor.KeyboardProcessor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TestBot extends TelegramLongPollingBot {

    private final KeyboardProcessor processor = new KeyboardProcessor();

    TestBot() {
        processor.registerHandler(new DataCallback());
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            try {
                SendMessage send = new SendMessage(message.getChatId(), "Hello?");

                List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

                List<List<ButtonHolder>> buttons = KeyboardGenerator.getInstance().generateKeyboard(new Data());
                for (List<ButtonHolder> button : buttons) {
                    List<InlineKeyboardButton> row = new ArrayList<>();
                    for (ButtonHolder holder : button) {
                        row.add(new InlineKeyboardButton(holder.getText()).setCallbackData(holder.getData()));
                    }
                    keyboard.add(row);
                }

                send.setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(keyboard));

                execute(send);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            try {
                System.out.println(query.getData());
                processor.processCallback(query);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "BOT_USERNAME";
    }

    @Override
    public String getBotToken() {
        return "BOT_TOKEN";
    }
}
