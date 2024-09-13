import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("введите первый множитель: ");
        int a = scanner.nextInt();

        System.out.println("введите второй множитель: ");
        int и = scanner.nextInt();

        System.out.println("выберите способ умножения \n(1 - рекурсия, 2 - сложение, 3 - побитовый сдвиг)");
        int n = scanner.nextInt();

        //выбор способа умножения
        switch (n){
            case 1:
                System.out.println(multiply(a, и));
                break;
            case 2:
                System.out.println(multiply2(a, и));
                break;
            case 3:
                System.out.println(multiply3(a, и));
                break;
            default:
                System.out.println("введены неверные значения блин!!!!!");
        }

    }

    //рекурсия
    public static int multiply(int a, int b) {
        if (b == 0) {
            return 0;
        } else {
            return a + multiply(a, b - 1);
        }
    }

    //сложение
    public static int multiply2(int a, int b) {
        int result = 0;
        for (int i = 0; i < b; i++) {
            result += a;
        }
        return result;
    }

    public static int multiply3(int a, int b) {
        int result = 0;
        while (b != 0) {
            if ((b & 1) != 0) {     //в условии побитовое умножение
                
                result += a;
            }
            a <<= 1; // сдвиг влево на 1 бит
            b >>= 1; // сдвиг вправо на 1 бит
        }
        return result;

        /*
        :)
         */
    }


}