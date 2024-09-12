
import java.util.Random;

class FindMinMultiple {

    public static void main(String[] args) {
        final int size = 1000;
        // Генерируем случайную последовательность
        int[] mass = generateRandomSequence(size, 10000);

        // Находит минимальное число, кратное 21, которое является произведением двух других чисел последовательности
        int minMultiple = -1;
        for (int i = 0; i < mass.length; i++) {
            for (int j = i + 1; j < mass.length; j++) {
                int product = mass[i] * mass[j];
                if (product % 21 == 0 && (minMultiple == -1 || product < minMultiple) && InArray(mass, product)) {
                    minMultiple = product;
                }
            }
        }

        // Выводим результат
         if (minMultiple == -1) {
            System.out.println("Такого числа нет");
        } else {
            System.out.println("Минимальное число, кратное 21: " + minMultiple);
        }


    }

    // Генерирует случайную последовательность целых чисел в заданном диапазоне
    private static int[] generateRandomSequence(int size, int maxValue) {
        Random random = new Random();
        int[] sequence = new int[size];
        for (int i = 0; i < size; i++) {
            sequence[i] = random.nextInt( maxValue + 1);
        }
        return sequence;
    }



    // Проврека на наличие числа в массиве
    private static boolean InArray(int[] numbers, int n) {
        for (int number : numbers) {
            if (number == n) return true;
        }
        return false;
    }
}