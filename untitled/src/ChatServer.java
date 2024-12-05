import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

@SuppressWarnings("InfiniteLoopStatement")
public class ChatServer implements TCPConnectionListener {
    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private ChatServer() {
        System.out.println("Server running...");
        try(ServerSocket serverSocket = new ServerSocket(8186)) {
            while(true) {
                try {
                    new TCPConnection(this, serverSocket.accept(), "1");
                    System.out.println();
                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnections("Пользователь присоединился к чату");
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        sendToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnected: " + tcpConnection);
    }

    @Override
    public void onException(TCPConnection tcpConnection, IOException e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendToAllConnections(String value){
        try {
            if (value.contains("/список")) {
                System.out.println("Список");
                System.out.println("-----------");
                for (TCPConnection connection : connections) {
                    System.out.println(connection.toString().split(" - ")[1]);
                }
                System.out.println("-----------");
            }

            if (value.contains("/удалить")) {
                String id = value.split(": ")[1].split("( )+")[1];
                for (TCPConnection connection: connections) {
                    if (connection.toString().contains(id)) {
                        connections.remove(connection);
                        connection.sendString("В чате забанен пользователь: " + connection.toString().split(" - ")[1]);
                        break;
                    }
                }
            }
            for (TCPConnection connection : connections) {
                if (value.contains("присоединился(лась) к чату, поздоровайтесь!")) {
                    connection.userName = value.replace("присоединился(лась) к чату, поздоровайтесь!", "");
                }
                System.out.println(connection + " " + value);
                connection.sendString(value);
            }
        }
        catch (Exception ex) {}
    }
}
