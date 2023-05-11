package Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public List<String> readFile(String path) {
        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public static void writeStringToFile(List<String> base, String filename) throws IOException {
        try {
            FileWriter writer = new FileWriter(filename);
            for (String word : base) {
                writer.write(word + "\n"); // write word followed by newline character
            }
            writer.close();
            System.out.println("Word list written to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file.");
            e.printStackTrace();
        }
    }
    public static void writeLongToFile(List<Long> base, String filename) throws IOException {
        try {
            FileWriter writer = new FileWriter(filename);
            for (long word : base) {
                writer.write(word + "\n"); // write word followed by newline character
            }
            writer.close();
            System.out.println("Word list written to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file.");
            e.printStackTrace();
        }
    }
    public static void writeIntToFile(List<Integer> base, String filename) throws IOException {
        try {
            FileWriter writer = new FileWriter(filename);
            for (int word : base) {
                writer.write(word + "\n"); // write word followed by newline character
            }
            writer.close();
            System.out.println("Word list written to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file.");
            e.printStackTrace();
        }
    }
}