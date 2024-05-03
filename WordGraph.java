import java.util.*;

class WordGraph {
    Map<String, List<String>> adjList = new HashMap<>();

    public WordGraph(List<String> words) {
        for (String word : words) {
            adjList.put(word, new ArrayList<>());
        }
        for (String word : words) {
            for (String other : words) {
                if (isOneLetterDifferent(word, other)) {
                    adjList.get(word).add(other);
                }
            }
        }
    }

    private boolean isOneLetterDifferent(String word1, String word2) {
        if (word1.length() != word2.length()) return false;
        int diffCount = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                if (++diffCount > 1) return false;
            }
        }
        return diffCount == 1;
    }
    
    public List<String> getNeighbors(String word) {
        return adjList.getOrDefault(word, new ArrayList<>());
    }
}