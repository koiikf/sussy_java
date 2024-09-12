import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int n = 1;
        int m = -1;

        System.out.print("Введите размер массива: ");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] array = new int[size];

        System.out.println("Введите элементы массива:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }

        System.out.println("Введите степень мажоритарности в процентах: ");
        int a = scanner.nextInt();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    if (array[i] == array[j]) n += 1;
                }
            }
            if (n >= (size * a) / 100) {
                m = array[i];
                i = size;
            } else n = 0;
        }
        System.out.println("От " + a + " процентов массива составляет число " + m);
    }
}