package mouamle.keyboard.bot;

import mouamle.generator.KeyboardGenerator;
import mouamle.generator.classes.ButtonHolder;
import mouamle.keyboard.bot.callback.DataCallback;
import mouamle.keyboard.bot.model.single.*;
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

    TestBot() {
        KeyboardProcessor.registerHandler(new DataCallback());
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            try {
                SendMessage send = new SendMessage(message.getChatId(), "Hello?");

                send.setReplyMarkup(generateMarkup(new StringValueModel()));
                execute(send);

                send.setReplyMarkup(generateMarkup(new IntValueModel()));
                execute(send);

                send.setReplyMarkup(generateMarkup(new ButtonValueModel()));
                execute(send);

                send.setReplyMarkup(generateMarkup(new ButtonGroupValueModel()));
                execute(send);

                send.setReplyMarkup(generateMarkup(new ListValueModel()));
                execute(send);

                send.setReplyMarkup(generateMarkup(new MapValueModel()));
                execute(send);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            try {
                boolean processed = KeyboardProcessor.processCallback(query);
                if (!processed) {
                    // Carry on with custom processing
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private InlineKeyboardMarkup generateMarkup(Object object) throws Exception {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<List<ButtonHolder>> buttons = KeyboardGenerator.getInstance().generateKeyboard(object);
        for (List<ButtonHolder> button : buttons) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (ButtonHolder holder : button) {
                row.add(new InlineKeyboardButton(holder.getText()).setCallbackData(holder.getData()));
            }
            keyboard.add(row);
        }

        return new InlineKeyboardMarkup().setKeyboard(keyboard);
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
