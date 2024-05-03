package src;
import java.util.*;
class SolverResult {
    private List<String> path;        
    private long timeTaken;           
    private int visitedNodes;         

    // Constructor to initialize the results
    public SolverResult(List<String> path, long timeTaken, int visitedNodes) {
        this.path = path;
        this.timeTaken = timeTaken;
        this.visitedNodes = visitedNodes;
    }

    public List<String> getPath() {
        return path;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public int getVisitedNodes() {
        return visitedNodes;
    }
}
