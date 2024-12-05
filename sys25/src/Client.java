import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("работаем.");

            String userInput;
            while (true) {
                System.out.print("Введите сообщение или команды (ап, давн, пока): ");
                userInput = scanner.nextLine();

                output.println(userInput); // Отправляем сообщение на сервер

                // Проверка выхода
                if (userInput.equalsIgnoreCase("пока")) {
                    break;
                }

                // Получаем ответ от сервера
                String response = new Scanner(socket.getInputStream()).nextLine();
                System.out.println("ответ от сервера - " + response);
            }
        } catch (IOException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
        }
    }
}
