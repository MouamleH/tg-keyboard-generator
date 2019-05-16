package keyboard;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

public class Main {

    public static void main(String[] args) throws Exception {
        ApiContextInitializer.init();

        TelegramBotsApi api = new TelegramBotsApi();
        api.registerBot(new TestBot());
        System.out.println("Bot started.");
    }


}
