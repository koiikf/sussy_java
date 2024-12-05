import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class MaxPairsFinder {
    public static void main(String[] args) throws IOException {
        String filePath = "fl.txt";
        int maxCount = 0;
        int currentCount = 0;
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        }
        for (int i = 0; i < content.length() - 1; i++) {
            String pair = content.substring(i, i + 2);
            if (pair.equals("AB") || pair.equals("CB")) {
                currentCount++;
                i++;
            } else {
                currentCount = 0;
            }
            maxCount = Math.max(maxCount, currentCount);
        }

        System.out.println("пары AB или CB: " + maxCount);

    }

}
