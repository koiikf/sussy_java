import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static boolean flag = true;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("сервер робэ. ожидание подключения клиента...");
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 OutputStream outputStream = clientSocket.getOutputStream()) {

                System.out.println("клиент тут: " + clientSocket.getInetAddress());

                String message;
                while ((message = input.readLine()) != null) {
                    System.out.println("сообщение клиента: " + message);
                    String response = processMessage(message);
                    outputStream.write((response + "\n").getBytes());
                    outputStream.flush();
                }
                System.out.println("клиент сказал бб.");
            }
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private static String processMessage(String message) {
        if (message.equalsIgnoreCase("пока")) {
            return "Сервер завершает соединение.";
        } else if (message.equalsIgnoreCase("ап")) {
            flag = false;
            return "сделать больше: ";
        } else if (message.equalsIgnoreCase("давн")) {
            flag = true;
            return "сделать меньше";
        } else {
            // Проверка, если текст нужно преобразовать
            if (flag)
                return "изменено: " + message.toLowerCase();
            return "изменено: " + message.toUpperCase();// Пример преобразования
        }
    }
}
