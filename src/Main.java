import java.util.*;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Set<String> dictionary = DictionaryLoader.loadWordsFromFile("bin/words.txt");
        // WordLadderSolver solver = new WordLadderSolver(dictionary);

        SwingUtilities.invokeLater(() -> {
            WordLadderGUI frame = new WordLadderGUI(dictionary);
            frame.setVisible(true);
        });
    }
}
