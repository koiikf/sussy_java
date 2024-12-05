import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Сервер ждет клиента...");

        try (Socket clientSocket = serverSocket.accept();
             InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {
            System.out.println("Новое соединение: " + clientSocket.getInetAddress().toString());

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String playerChoice = new String(buffer, 0, bytesRead).trim();
                System.out.println("Игрок выбрал: " + playerChoice);



                outputStream.flush();
            }
            System.out.println("Клиент отключился");
        }
    }
}
