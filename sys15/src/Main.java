import java.io.IOException;
import java.net.URLConnection;
import java.util.*;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.io.*;
import java.net.URL;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        ArrayList<LoadFile> loadingFiles = new ArrayList<>();

        while (true) {
            System.out.println("Введите путь к файлу, который необходимо скачать: ");
            String file = in.next();
            System.out.println("Введите название файла: ");
            String fileName = in.next();

            loadingFiles.add(new LoadFile(file, fileName));

            System.out.println("Введите 1, чтобы закончить ввод, либо любой символ для продолжения: ");
            String command = in.next();
            if (Objects.equals(command, "1")) break;
        }

        ExecutorService executor = Executors.newFixedThreadPool(loadingFiles.size());

        ArrayList<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < loadingFiles.size(); i++)
            futures.add(executor.submit(loadingFiles.get(i)));

        try {
            for (int i = 0; i < loadingFiles.size(); i++){
                String result = futures.get(i).get();
                System.out.printf("Load %s: %s\n", loadingFiles.get(i).filePath, result);
                if (Objects.equals(result, "Ok") && loadingFiles.get(i).filePath.endsWith(".mp3")) {
                    String musicFilePath = "C:\\Users\\user\\IdeaProjects\\sys15\\" + loadingFiles.get(i).newFileName;
                    String musicPlayerPath = "C:\\Program Files (x86)\\Windows Media Player\\wmplayer.exe";

                    ProcessBuilder processBuilder = new ProcessBuilder(musicPlayerPath, musicFilePath);

                    Process process = processBuilder.start();
                    try {
                        process.waitFor();
                    } catch (InterruptedException e) {
                        System.out.println("Ошибка!");
                    }
                }
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();
    }
}
class LoadFile implements Callable<String> {
    final String filePath;
    final String newFileName;

    LoadFile(String filePath, String newFileName) {
        this.filePath = filePath;
        this.newFileName = newFileName;
    }

    public String call() {
        try {
            URLConnection conn = new URL(filePath).openConnection();

            InputStream in = conn.getInputStream();

            OutputStream out = new FileOutputStream(new File(newFileName));

            byte[] header = new byte[3];
            in.read(header);

            System.out.println(Arrays.toString(header));

            boolean findType = false;
            for (String key : TypeOfFiles.types.keySet()) {
                if (Arrays.toString(TypeOfFiles.types.get(key)).equals(Arrays.toString(header))) {
                    out.write(header);
                    out.write(in.readAllBytes());
                    findType = true;
                }
            }

            if (!findType) {
                out = new FileOutputStream(new File("OUT_TXT.txt"));
                out.write(in.readAllBytes());
                out.close();
                return "Not known file type. File was save as txt";
            }

            out.close();

            return "Ok";
        } catch (IOException e) {
            return "Error load file";
        }
    }
}
class TypeOfFiles {
    public static HashMap<String, byte[]> types;
    static {
        types = new HashMap<>();
        types.put("mp3", new byte[]{73, 68, 51});
        types.put("png", new byte[]{(byte)0x89, 0x50, 0x4E});
        types.put("mp4", new byte[]{0, 0, 0});
        types.put("jpg", new byte[]{(byte)0xFF, (byte)0xD8, (byte)0xFF});
    }
}