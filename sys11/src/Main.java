import java.io.*;

class TextStatistics {

    public static void main(String[] args) {
        String txtfile = "aaa.txt";

        StringBuilder text = new StringBuilder(); // Хранит текст из файла
        try (BufferedReader reader = new BufferedReader(new FileReader(txtfile))) {
            int c; // хранит текущий символ
            while ((c = reader.read()) != -1) { // читает посимвольно
                text.append((char)c); // добавляет символ в текст
            }
        } catch (IOException ex) { // Обработка ошибок ввода-вывода
            System.out.println();
        }

        String textNew = text.toString();

        int vsebukvi = text.length(); // количество символов
        int without_space = text.toString().replaceAll("\\s", "").length(); // количество символов без пробелов
        String[] words = text.toString().split("\\s+"); // массив слов
        int slova = words.length; // количество слов

        int pager = 0; // счетчик страниц
        int abzats = 0; // счётчик абзацев

        for (char буковка: textNew.toCharArray()) {
            if (буковка == '\u000c') pager++; // если символ перехода на страницу, увеличивает счетчик
            if (буковка == '\r') abzats++; // если символ перехода каретки, увеличивает счетчик
        }

        int aaa = vsebukvi-1-pager-abzats; // общее количество символов без спец символов

        // Выводит статистику в консоль
        System.out.println("Статистика текста:");
        System.out.println("Общее количество символов: " + aaa);
        System.out.println("Количество символов без пробелов: " + without_space);
        System.out.println("Количество слов: " + slova);
        System.out.println("Количество абзацев: " + abzats);
        System.out.println("Количество страниц: " + pager);

        // Записывает статистику в файл "statistics.txt"
        try (FileWriter writer = new FileWriter("statistics.txt")) {
            writer.write("Статистика текста:\n");
            writer.write("Общее количество символов: " + aaa + "\n");
            writer.write("Количество символов без пробелов: " + without_space + "\n");
            writer.write("Количество слов: " + slova + "\n");
            writer.write("Количество абзацев: " + abzats + "\n");
            writer.write("Количество страниц: " + pager + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }

        // Записывает статистику в файл "statistics.svc" (повторение)
        try (FileWriter writer = new FileWriter("statistics.svc")) {
            writer.write("Статистика текста:\n");
            writer.write("Общее количество символов: " + aaa + "\n");
            writer.write("Количество символов без пробелов: " + without_space + "\n");
            writer.write("Количество слов: " + slova + "\n");
            writer.write("Количество абзацев: " + abzats + "\n");
            writer.write("Количество страниц: " + pager + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
