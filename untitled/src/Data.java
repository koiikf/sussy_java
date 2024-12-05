import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Data {
    public static ArrayList<String> badWords = new ArrayList<>() {};
    public static HashMap<String, String> commands = new HashMap<>() {{
        put("/правила", "Система: \n" +
                "        ПРАВИЛА ЧАТА: \n" +
                "        1. не писать отстойные слова\n" +
                "        2. быть адекватным человеком и никого не оскорблять \n");
        put("/приветствие", "Система: Привеееет :)\n");
        //put("/банворд", "Система: \n" +
        //                "        СПИСОК ЗАПРЕЩЕННЫХ СЛОВ: \n" +
        //                "        " + Arrays.toString(badWords) + "\n");
        put("/команды", "Система: \n" +
                "        СПИСОК КОМАНД: \n" +
                "        /команды - список всех команд\n" +
                "        /правила - правила чата\n" +
                "        /банворд - список запрещенных слов\n" +
                "        /приветствие - система вас приветствует\n");
        put("/темы", "Система: \n" +
                "     ДОСТУПНЫЕ ТЕМЫ: \n" +
                "     гламур\n" +
                "     тьма\n" +
                "     свет\n" +
                "     космос\n");
    }};

    public static HashMap<String, ArrayList<Color>> themes = new HashMap<>() {{
        put("космос", new ArrayList<>(List.of(new Color(12, 17, 38), new Color(26, 255, 255))));
        put("гламур", new ArrayList<>(List.of(new Color(46, 0, 17), Color.pink)));
        put("свет", new ArrayList<>(List.of(Color.WHITE, Color.BLACK)));
        put("тьма", new ArrayList<>(List.of(Color.BLACK, Color.WHITE)));
    }};
}
