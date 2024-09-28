import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("введите количество деняк");
        int money = sc.nextInt();
        System.out.println("введите цену");
        int price = sc.nextInt();
        System.out.println("введите количество оберток для доп шоколадки");
        int wrap = sc.nextInt();
        int шоколадки = money/price;
        int фантики = шоколадки;
        while (фантики >= wrap){
            шоколадки = шоколадки + фантики/wrap;
            фантики = фантики/wrap + фантики % wrap
            ;
        }
        System.out.println(шоколадки);
    }
}