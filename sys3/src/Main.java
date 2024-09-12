import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("введите количество голосов: ");
        int n = scanner.nextInt();

        //номера пар от 1 до 16
        final int max_couple = 16;
        final int min_couple = 1;

        //обрабатываем не более 20 голосов
        if (n > 20) {
            System.out.println("слишком много голосов. при голосовании будут учитаны первые 20.");
            n = 20;
        }

        //массив голосов
        int[] dancers = new int[n];

        //заполняем массив
        for(int i = 0; i<n; i++){
            dancers[i] = scanner.nextInt();
            while (!(dancers[i] <= max_couple && dancers[i] >= min_couple)) {
                System.out.println("такой пары нет, введите заново: ");
                dancers[i] = scanner.nextInt();
            }
        }

        //хэш-таблица, где ключ - номер пары, а значение - количество голосов
        HashMap<Integer, Integer> list = new HashMap<>();

        // проходим по всем числам массива. если мы уже добавляли информацию в словарь, то добавляем +1, иначе добавляем новую запись со значением 1
        for (int dance: dancers) {
            if (list.containsKey(dance)) list.put(dance, list.get(dance) + 1);
            else list.put(dance, 1);
        }

        //снова проходим по всем числам. если информация уже добавлена, то добавлем новую запись со значением ноль (это для вывода списка всех конкурсантов и их голосов)
        for (int i = min_couple; i<=max_couple; i++){
            if (!list.containsKey(i)) list.put(i, 0);
        }

        System.out.println("Список призеров: ");
        list.entrySet().stream()    //возвращаем записи из list и преобразуем в поток для дальнейшей сортировки
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())  //сортируем в порядке убывания
                .limit(3)   //ставим лимит три для трех призеров
                .forEach(it -> System.out.println(it.getKey() + " " + it.getValue()));  //выводим призеров

        System.out.println("Список всех конкурсантов и их голосов: ");
        list.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .forEach(it -> System.out.println(it.getKey() + " " + it.getValue()));  //лимита нет, выводим всех конкурсантов
    }
}