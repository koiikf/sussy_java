import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Введите число: ");
        int n = input.nextInt();
        if (n > -1) {
            System.out.println("факториал " + n + " равен " + factorial(n));
        }
        else System.out.println("и зачем тебе факториал отрицательного числа??");
    }
    static int factorial(int n) {
        int result = 1;
        if (n == 1 || n == 0) {
            return result;
        }
        result = n * factorial(n-1);
        return result;
    }
}