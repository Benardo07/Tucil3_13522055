class Node implements Comparable<Node> {
    String word;
    Node parent;
    int cost;

    public Node(String word, Node parent, int cost) {
        this.word = word;
        this.parent = parent;
        this.cost = cost;
    }

    // Untuk PriorityQueue, yang membandingkan berdasarkan cost
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.cost, other.cost);
    }
}