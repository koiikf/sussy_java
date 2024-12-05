import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        
        try (Socket socket = new Socket("localhost", 8080);

             OutputStream outputStream = socket.getOutputStream();
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("работаем.");
            String userInput;

            while (true) {
                System.out.println("камень, ножницы, бумага или пока:");
                userInput = scanner.nextLine();

                outputStream.write(userInput.getBytes());
                outputStream.flush();

                if (userInput.equalsIgnoreCase("пока")) {
                    break;
                }

                String serverChoice = getRandomChoice();
                System.out.println("сервак выбрал: " + serverChoice);
                String result = determineWinner(userInput, serverChoice);
                System.out.println(result);
            }
        } catch (IOException e) {
            System.err.println("ошибочка вышла: " + e.getMessage());
        }
    }

    private static String getRandomChoice() {
        String[] choices = {"камень", "ножницы", "бумага"};
        Random random = new Random();
        return choices[random.nextInt(choices.length)];
    }

    private static String determineWinner(String playerChoice, String serverChoice) {

        if (playerChoice.equalsIgnoreCase(serverChoice)) {
            return "ничья, ура: " + playerChoice;
        } else if ((playerChoice.equalsIgnoreCase("камень") && serverChoice.equalsIgnoreCase("ножницы")) ||
                (playerChoice.equalsIgnoreCase("ножницы") && serverChoice.equalsIgnoreCase("бумага")) ||
                (playerChoice.equalsIgnoreCase("бумага") && serverChoice.equalsIgnoreCase("камень"))) {
            return "победа клиента!!!! он выкинул: " + playerChoice + ", а сервер выбрал: " + serverChoice;
        } else {
            return "блиин клиент проиграл, он выбрал: " + playerChoice + ", а сервер выбрал: " + serverChoice;
        }
    }
}
