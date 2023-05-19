import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    private static final int MIN_STRING_LENGTH = 7; // Minimum length of each random string
    private static final int MAX_STRING_LENGTH = 7; // Maximum length of each random string
    private static final int NUM_STRINGS = 15000; // Number of random strings to generate

    public static void main(String[] args) {
        String fileName = "random_strings.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < NUM_STRINGS; i++) {
                String randomString = generateRandomString();
                writer.write(randomString);
                writer.newLine();
            }
            System.out.println("Random strings generated and written to file: " + fileName);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    private static String generateRandomString() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        int length = random.nextInt(MAX_STRING_LENGTH - MIN_STRING_LENGTH + 1) + MIN_STRING_LENGTH;
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            char randomChar = chars.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
