package src;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class DictionaryLoader {
    public static Set<String> loadWordsFromFile(String filename) {
        Set<String> words = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return words;
    }
}
