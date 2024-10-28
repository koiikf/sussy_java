import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int макс_ряд = 0;
        int мин_место = 1000000;

        int тот_ряд = 0;
        int то_место = 0;

        for (int i = 0; i < n; i++) {
            int ряд = scanner.nextInt();
            int место = scanner.nextInt();
            if (тот_ряд == ряд && то_место == место - 3) {
                if (ряд > макс_ряд) {
                    макс_ряд = ряд;
                    мин_место = то_место + 1;
                } else if (ряд == макс_ряд) мин_место = Math.min(мин_место, то_место + 1);
            }
            тот_ряд = ряд;
            то_место = место;
        }

        System.out.println("Макисмальный ряд: " + макс_ряд);
        System.out.println("Минимальный номер места: " + мин_место);
    }
}