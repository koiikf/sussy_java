import java.io.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String FILE_PATH = "Test.java";
    private static int variableName = 65;

    public static void main(String[] args) {
        String codeString = readFile(FILE_PATH);
        if (codeString != null) {
            codeString = cleanCode(codeString);
            codeString = obfuscateCode(codeString);
            System.out.println(codeString); // Выводим обфусцированный код
        }
    }

    // Метод для чтения файла
    private static String readFile(String filePath) {
        StringBuilder myCode = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                myCode.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return null;
        }
        return myCode.toString();
    }

    // Метод для очистки кода
    private static String cleanCode(String codeString) {
        codeString = codeString.replaceAll(".*//.*\n", ""); // Удаление однострочных комментариев
        codeString = codeString.replaceAll("\\s{2,}", " "); // Удаление лишних пробелов
        codeString = codeString.replaceAll(", ", ",");
        codeString = codeString.replaceAll("; ", ";");
        codeString = codeString.replaceAll("\\{ ", "{");

        // Удаление многострочных комментариев
        codeString = codeString.replaceAll("/\\*.*?\\*/", "");

        return codeString;
    }

    // Метод для обфускации кода
    private static String obfuscateCode(String codeString) {
        String fileName = "";

        // Поиск и замена названия класса
        fileName = replaceClassName(codeString);

        // Поиск и замена названий методов
        String regex = generateVariableTypesRegex();
        codeString = replaceMethodNames(codeString, regex, fileName);

        // Замена специальных символов
        codeString = replaceSpecialCharacters(codeString);

        return codeString;
    }

    // Метод для замены названий классов
    private static String replaceClassName(String codeString) {
        Pattern pattern = Pattern.compile("class \\w+( )*\\{");
        Matcher matcher = pattern.matcher(codeString);
        String fileName = null;
        while (matcher.find()) {
            String variable = matcher.group();
            fileName = variable.split(" ")[1].split("\\{")[0];
            String newFile = createName(variableName);
            codeString = codeString.replace(fileName, newFile);
            variableName++;
        }
        return fileName;
    }

    // Метод для замены названий методов
    private static String replaceMethodNames(String codeString, String regex, String fileName) {
        Pattern pattern = Pattern.compile("(" + regex + "|void) \\w+\\([a-zA-Z, ]*\\)");
        Matcher matcher = pattern.matcher(codeString);

        while (matcher.find()) {
            String variable = matcher.group().split(" ")[1].split("\\(")[0];
            if (!Objects.equals(variable, fileName)) {
                codeString = codeString.replaceAll(variable, createName(variableName));
                variableName++;
            }
        }
        return codeString;
    }

    // Метод для создания регулярного выражения для типов переменных
    private static String generateVariableTypesRegex() {

        String[] types = {
                "String", "URL", "int", "InputStream", "StringBuilder",
                "JSONObject", "HttpURLConnection", "BufferedReader",
                "IOException", "Exception", "OutputStreamWriter"
        };

        StringBuilder regex = new StringBuilder();
        for (String type : types) {
            regex.append("|").append(type);
        }
        return regex.substring(1); // убираем первый символ "|"
    }

    // Метод для замены специальных символов
    private static String replaceSpecialCharacters(String codeString) {
        return codeString
                .replaceAll("\\(", "@")
                .replaceAll("\\)", "%")
                .replaceAll("\\{", "`")
                .replaceAll("\\+", "~");
    }

    // Метод для создания нового имени
    private static String createName(int variableName) {
        return Character.toString(variableName); // Преобразование в символ
    }
}
