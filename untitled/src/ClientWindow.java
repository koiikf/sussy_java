
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {
    private static final String IP_ADDR = "192.168.134.23";
    private static final int PORT = 8186;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }

    private final JTextArea log = new JTextArea();
    private final JTextField fieldNickname = new JTextField("im not koi");
    private final JTextField fieldInput = new JTextField();

    private TCPConnection connection;

    private boolean isBan = false;
    boolean isCommand = false;

    private ClientWindow() {
        setTitle("СБОРИЩЕ ЧЕЛОВЕКОВ");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);



        add(fieldNickname, BorderLayout.NORTH);

        log.setEditable(false);
        log.setLineWrap(true);
        add(new JScrollPane(log), BorderLayout.CENTER);

        log.setFont(log.getFont().deriveFont(18f));
        fieldNickname.setFont(log.getFont());

        fieldInput.addActionListener(this);
        fieldInput.setFont(log.getFont());
        add(fieldInput, BorderLayout.SOUTH);

        log.setBackground(Color.BLACK);
        fieldInput.setBackground(Color.BLACK);
        fieldNickname.setBackground(Color.BLACK);
        fieldInput.setForeground(Color.WHITE);

        log.setForeground(Color.WHITE);
        fieldNickname.setForeground(Color.WHITE);


        setVisible(true);

        try {
            connection = new TCPConnection(this, IP_ADDR, PORT, fieldNickname.getText());
            connection.sendString(fieldNickname.getText() + " присоединился(лась) к чату, поздоровайтесь!");
        } catch (IOException e) {}

        {
            String fileName = "words.txt";

            try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
                String line;
                while ((line = br.readLine()) != null) {
                    Data.badWords.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isBan && !isCommand) {
            String msg = fieldInput.getText();
            if (msg.isEmpty()) return;
            fieldInput.setText(null);
            connection.sendString(fieldNickname.getText() + ": " + msg);
        }
        else if (isCommand) {
            printMessage(fieldNickname.getText() + ": " + fieldInput.getText());
            fieldInput.setText(null);
        }
        isCommand = false;
    }


    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMessage("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMessage(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMessage("Connection close");
    }

    @Override
    public void onException(TCPConnection tcpConnection, IOException e) {
        printMessage("Connection exception: " + e);
    }

    private synchronized void printMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!isBan) {
                    if (message.split(": ").length != 1) {
                        System.out.println(3);
                        String mes = message.split(": ")[1];
                        for (String key: Data.commands.keySet()) {

                            if (Objects.equals(mes, key)) {
                                log.append(Data.commands.get(key));
                                isCommand = true;
                                return;
                            }
                        }

                        if (!isCommand) {
                            for (String key: Data.themes.keySet()) {
                                if (Objects.equals(mes, key)) {
                                    ArrayList<Color> colors = Data.themes.get(key);
                                    setChatTheme(colors.get(0), colors.get(1));
                                    isCommand = true;
                                    return;
                                }
                            }
                        }

                        if (!isCommand) {
                            boolean flag = false;
                            for (String word: Data.badWords) {
                                if (mes.toLowerCase().contains(word.toLowerCase())) {
                                    log.append(message.replace(word, "***") + "\n");
                                    isBan = true;
                                    flag = true;
                                }
                            }
                            if (!flag) log.append(message + "\n");
                        }
                    }
                    else log.append(message + "\n");
                }
            }
        });
    }

    private void setChatTheme(Color textColor, Color backgroundColor) {
        log.setBackground(textColor);
        fieldInput.setBackground(textColor);
        fieldNickname.setBackground(textColor);

        fieldInput.setForeground(backgroundColor);
        log.setForeground(backgroundColor);
        fieldNickname.setForeground(backgroundColor);

        log.append("Система: СМЕНА ТЕМЫ ПРОШЛА УСПЕШНО\n" );
    }
}
