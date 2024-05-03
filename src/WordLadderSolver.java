package src;
import java.util.*;

public class WordLadderSolver {
    private Set<String> dictionary;
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public WordLadderSolver(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    public SolverResult solveUCS(String start, String end) {
        long startTime = System.currentTimeMillis();
        if (!dictionary.contains(start) || !dictionary.contains(end)) {
            return new SolverResult(Collections.emptyList(), 0, 0); // Start or end word not in dictionary
        }

        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new Node(start, null, 0));
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.word.equals(end)) {
                long endTime = System.currentTimeMillis();
                return new SolverResult(constructPath(current), endTime - startTime, visited.size());
            }

            for (String neighbor : getNeighbors(current.word)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(new Node(neighbor, current, current.cost + 1));
                }
            }
        }
        long endTime = System.currentTimeMillis();
        return new SolverResult(Collections.emptyList(), endTime - startTime, visited.size()); // No solution found
    }

    public SolverResult solveUsingGreedy(String start, String end) {
        long startTime = System.currentTimeMillis();
        if (!dictionary.contains(start) || !dictionary.contains(end)) {
            return new SolverResult(Collections.emptyList(), 0, 0); // Start or end word not in dictionary
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));
        Set<String> visited = new HashSet<>();
        queue.add(new Node(start, null, getHeuristic(start, end)));
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.word.equals(end)) {
                long endTime = System.currentTimeMillis();
                return new SolverResult(constructPath(current), endTime - startTime, visited.size());
            }

            for (String neighbor : getNeighbors(current.word)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(new Node(neighbor, current, getHeuristic(neighbor, end)));
                }
            }
        }
        long endTime = System.currentTimeMillis();
        return new SolverResult(Collections.emptyList(), endTime - startTime, visited.size()); // No solution found
    }

    public SolverResult solveUsingAStar(String start, String end) {
        long startTime = System.currentTimeMillis();
        if (!dictionary.contains(start) || !dictionary.contains(end)) {
            return new SolverResult(Collections.emptyList(), 0, 0); // Start or end word not in dictionary
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost + getHeuristic(node.word, end)));
        Set<String> visited = new HashSet<>();
        queue.add(new Node(start, null, getHeuristic(start, end)));
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.word.equals(end)) {
                long endTime = System.currentTimeMillis();
                return new SolverResult(constructPath(current), endTime - startTime, visited.size());
            }

            for (String neighbor : getNeighbors(current.word)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    Node nextNode = new Node(neighbor, current, current.cost + 1 + getHeuristic(neighbor, end));
                    queue.add(nextNode);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        return new SolverResult(Collections.emptyList(), endTime - startTime, visited.size()); // No solution found
    }


    private int getHeuristic(String currentWord, String endWord) {
        int differenceCount = 0;
        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) != endWord.charAt(i)) {
                differenceCount++;
            }
        }
        return differenceCount;
    }

    private List<String> getNeighbors(String word) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = word.toCharArray();

        for (int i = 0; i < word.length(); i++) {
            char originalChar = chars[i];
            for (char c : ALPHABET) {
                if (c != originalChar) {
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (dictionary.contains(newWord)) {
                        neighbors.add(newWord);
                    }
                }
            }
            chars[i] = originalChar; // Restore original character
        }

        return neighbors;
    }

    private List<String> constructPath(Node endNode) {
        LinkedList<String> path = new LinkedList<>();
        Node current = endNode;
        while (current != null) {
            path.addFirst(current.word);
            current = current.parent;
        }
        return path;
    }
}

