import java.io.*;

public class Main {
    public static void main(String[] args) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("file.txt"))) {

            String line = bufferedReader.readLine();

            // заменяем комбинации символов (Q, R, W) с цифрами 1, 2, 4 на точку
            line = line.replaceAll("(Q|R|W)(1|2|4)", ".");
            // заменяем сами символы Q, R, W, 1, 2, 4 на пробелы
            line = line.replaceAll("Q|R|W|1|2|4", " ");


            String[] wordsArray = line.split("\\s+");

            // ищем максимальную длину слова
            int maxLen = 0;
            for (String word : wordsArray) {
                if (word.length() > maxLen) {
                    maxLen = word.length();
                }
            }



            System.out.println(maxLen);

        } catch (IOException e) {

            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
