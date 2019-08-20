package mouamle.keyboard.general;

import mouamle.generator.KeyboardGenerator;
import mouamle.generator.classes.ButtonHolder;
import mouamle.keyboard.bot.model.Data;
import mouamle.keyboard.bot.model.ListAndMap;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class KeyboardHandlerTest {

    @Test
    public void jsonTest() throws Exception {
        StringBuilder str = new StringBuilder();
        Files.readAllLines(new File("keyboard template.json").toPath()).forEach(line -> str.append(line).append('\n'));
        System.out.println(KeyboardGenerator.getInstance().generateKeyboardFromJson(str.toString()));
    }

    @Test
    public void generatorTest() throws Exception {
        List<List<ButtonHolder>> keyboard = KeyboardGenerator.getInstance().generateKeyboard(new Data());

        for (List<ButtonHolder> buttonHolders : keyboard) {
            for (ButtonHolder buttonHolder : buttonHolders) {
                System.out.print(buttonHolder + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void listAndMapTest() throws Exception {
        List<List<ButtonHolder>> keyboard = KeyboardGenerator.getInstance().generateKeyboard(new ListAndMap());

        for (List<ButtonHolder> buttonHolders : keyboard) {
            for (ButtonHolder buttonHolder : buttonHolders) {
                System.out.print(buttonHolder + " ");
            }
            System.out.println();
        }
    }

}
