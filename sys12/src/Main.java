import java.io.*;

public class Main {
    public static void main(String[] args) {
        File sausik = new File("source.txt"); // файл источник
        File bobrik = new File("dest.txt"); // файл куда копировать
        File newYear = new File("anotherone.txt"); // еще один файл источник для последовательной копирки

        long vremya = System.nanoTime(); // замеряем время начала

        try {
            copyFileUsingStream(sausik, bobrik); // копируем
            copyFileUsingStream(newYear, sausik);
        } catch (IOException e) {
            System.out.println("ну ты дурак что ли");
        }

        System.out.println("времечко последовательной копирки - " + (System.nanoTime() - vremya)/1000000.0 + " миллисек :)"); // время копирования

        File sourceFile1 = new File("source1.txt"); // файл источник 1
        File sourceFile2 = new File("source2.txt"); // файл источник 2
        File destFile1 = new File("dest1.txt"); // файл для копирки 1
        File destFile2 = new File("dest2.txt"); // файл для копирки второй

        Thread thread1 = new Thread(() -> { // создаем поток для копирования первого файла
            try {
                copyFileUsingStream(sourceFile1, destFile1);
            } catch (IOException e) {
                System.out.println("ну ты дурак что ли");
            }
        });

        Thread thread2 = new Thread(() -> { // создаем поток для копирования второго файла
            try {
                copyFileUsingStream(sourceFile2, destFile2);
            } catch (IOException e) {
                System.out.println("ну ты дурак что ли");
            }
        });

        vremya = System.nanoTime(); // время начала параллельного копирования

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
            System.out.println("времечко параллельной копирки - " + (System.nanoTime() - vremya)/1000000.0 + " миллисек :)"); // выводим время параллельного копирования
        } catch (InterruptedException e) {
            System.out.println("не");
        }


        System.out.println("молодец, ты скопировал файлы!!!!!!");

    }
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }
}