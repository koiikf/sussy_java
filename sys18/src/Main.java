import java.util.Scanner;

class MaxSumOfSquares {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int DISTANCE = 10;
        // Ввод количества элементов
        System.out.print("Введите количество элементов: ");
        int n = scanner.nextInt();

        // Проверка на допустимое количество элементов
        if (n < 2 || n > 10000) {
            System.out.println("Количество элементов должно быть в диапазоне от 2 до 10000.");
            return;
        }

        int[] numbers = new int[n];

        // Ввод элементов последовательности
        System.out.println("Введите элементы последовательности (целые числа от -100 до 100):");
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();

            // Проверка значения элемента
            if (numbers[i] < 0 || numbers[i] > 100) {
                System.out.println("Значение элемента должно быть в диапазоне от -100 до 100.");
                return;
            }
        }
        int answer = 0;
        for (int j = 0; j < numbers.length - 10; j++) {
            int n1 = numbers[j];
            for (int i = DISTANCE; i < numbers.length; i++) {
                if (i - j > 9) {
                    int n2 = numbers[i];
                    if (n1 * n1 + n2 * n2 > answer) answer = n1 * n1 + n2 * n2;
                }
            }
        }
        System.out.println("Максимальная сумма квадратов чисел: " + answer);
    }
}
