import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.Collections;
import java.util.Set;


public class WordLadderGUI extends JFrame {
    private JTextField startWordField;
    private JTextField endWordField;
    private JButton ucsButton;
    private JButton greedyButton;
    private JButton aStarButton;
    private JLabel resultLabel;
    private WordLadderSolver solver;
    private JPanel outputPanel;

    public WordLadderGUI(Set<String> dictionary) {
        solver = new WordLadderSolver(dictionary);

        setTitle("Word Ladder Game");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        // Top Panel for Inputs
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        // Top Panel for Inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Start Word:"));
        startWordField = new JTextField();
        inputPanel.add(startWordField);
        inputPanel.add(new JLabel("End Word:"));
        endWordField = new JTextField();
        inputPanel.add(endWordField);
        containerPanel.add(inputPanel);

        // Button Panel for Solving Algorithms
        JPanel buttonPanel = new JPanel();
        ucsButton = new JButton("Solve with UCS");
        greedyButton = new JButton("Solve with Greedy BFS");
        aStarButton = new JButton("Solve with A*");
        buttonPanel.add(ucsButton);
        buttonPanel.add(greedyButton);
        buttonPanel.add(aStarButton);
        containerPanel.add(buttonPanel);

        add(containerPanel, BorderLayout.NORTH);

        // Setup action listeners for each button
        ucsButton.addActionListener(e -> solve("UCS"));
        greedyButton.addActionListener(e -> solve("Greedy BFS"));
        aStarButton.addActionListener(e -> solve("A*"));

        outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(outputPanel);
        scrollPane.setPreferredSize(new Dimension(580, 300));
        add(scrollPane, BorderLayout.CENTER);
        // Output Table for displaying the path
    
        // Result Labels
        resultLabel = new JLabel("Selected Algorithm: None, Execution Time: 0 ms, Visited Nodes: 0");
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
    }
        
        private void solve(String algorithm) {
            String start = startWordField.getText().trim().toLowerCase();
            String end = endWordField.getText().trim().toLowerCase();
        
            if (start.length() != end.length()) {
                JOptionPane.showMessageDialog(this, "Start word and end word must be the same length.");
                return;
            }
        
            SolverResult result;
            switch (algorithm) {
                case "UCS":
                    result = solver.solveUCS(start, end);
                    break;
                case "Greedy BFS":
                    result = solver.solveUsingGreedy(start, end);
                    break;
                case "A*":
                    result = solver.solveUsingAStar(start, end);
                    break;
                default:
                    result = new SolverResult(Collections.emptyList(), 0, 0);
            }
        
            updateResultDisplay(result,algorithm);
        }
        
        private void updateResultDisplay(SolverResult result, String algorithm) {
            outputPanel.removeAll();  
        
            if (result.getPath().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No path found.");
            } else {
                for (int i = 0; i < result.getPath().size(); i++) {
                    JPanel wordPanel = new JPanel();
                    wordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    String current = result.getPath().get(i);
                    String prev = i != 0 ? result.getPath().get(i-1) : null;
        
                    JLabel num = new JLabel((i + 1) + ". ");
                    wordPanel.add(num);
        
                    for (int j = 0; j < current.length(); j++) {
                        JLabel label = new JLabel(String.valueOf(current.charAt(j)));
                        if (prev != null && j < prev.length() && current.charAt(j) != prev.charAt(j)) {
                            String styledText = "<html><u><font color='blue'>" + current.charAt(j) + "</font></u></html>";
                            label.setText(styledText);
                        }
                        wordPanel.add(label);
                    }
        
                    outputPanel.add(wordPanel);  
                }
        
                outputPanel.revalidate(); 
                outputPanel.repaint();  
            }
        
            resultLabel.setText("Selected Algorithm: " + algorithm +
                                ", Execution Time: " + result.getTimeTaken() + " ms" +
                                ", Visited Nodes: " + result.getVisitedNodes());
        }
}
