import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        StringBuilder text = new StringBuilder();
        String xyz = "zxc.txt";
        try(FileReader reader = new FileReader(xyz))
        {
            int a; // переменная для считываемого символа
            // читаем файл
            while ((a=reader.read())!=-1) {
                text.append((char)a); // добавляем символ в стрингбилдер
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        int result = 0; // количество х
        int count = 0; // сколько х на данный момент

        String letters = text.toString();

        // перебираем символы строки
        for (char letter: letters.toCharArray()) {
            if (letter == 'X') count++;
            else {
                result = Math.max(result, count); // обновляем максимум
                count = 0;
            }
        }
        result = Math.max(result, count);

        System.out.println(result);
    }
}
