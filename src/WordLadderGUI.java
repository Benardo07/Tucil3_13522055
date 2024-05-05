import javax.swing.*;
import javax.swing.border.Border;
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
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        // Top Panel for Inputs
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBackground(Color.BLACK);

        // Top Panel for Inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 5, 5));
        inputPanel.setBackground(Color.BLACK);
        JLabel startword = new JLabel("Start Word       :");
        startword.setForeground(Color.WHITE);
        inputPanel.add(startword);
        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY);
        Border margin = BorderFactory.createEmptyBorder(5, 5, 5, 5); // top, left, bottom, right padding
        Border compound = BorderFactory.createCompoundBorder(line, margin);
        startWordField = new JTextField();
        startWordField.setBorder(compound);
        startWordField.setBackground(Color.BLACK);
        startWordField.setForeground(Color.WHITE);
        inputPanel.add(startWordField);
        JLabel endword = new JLabel("End Word     :");
        endword.setForeground(Color.WHITE);
        inputPanel.add(endword);
        endWordField = new JTextField();
        endWordField.setBackground(Color.BLACK);
        endWordField.setForeground(Color.WHITE);
        
        endWordField.setBorder(compound);
        inputPanel.add(endWordField);
        containerPanel.add(inputPanel);

        containerPanel.add(Box.createVerticalStrut(20));

        // Button Panel for Solving Algorithms
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        ucsButton = createButton("Solve with UCS");
        greedyButton = createButton("Solve with Greedy BFS");
        aStarButton = createButton("Solve with A*");
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
        outputPanel.setBackground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(outputPanel);
        scrollPane.setPreferredSize(new Dimension(580, 300));
        add(scrollPane, BorderLayout.CENTER);
        // Output Table for displaying the path
    
        // Result Labels
        resultLabel = new JLabel("Selected Algorithm: None       Execution Time: 0 ms       Visited Nodes: 0");
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        resultLabel.setForeground(Color.WHITE);
        add(resultLabel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.GREEN); 
        button.setForeground(Color.BLACK); 
        button.setFocusPainted(false);

        // Create a compound border to add padding
        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY);
        Border margin = BorderFactory.createEmptyBorder(10, 15, 10, 15); 
        Border compound = BorderFactory.createCompoundBorder(line, margin);
        button.setBorder(compound);
        return button;
    }
        
    private void solve(String algorithm) {
        String start = startWordField.getText().trim().toLowerCase();
        String end = endWordField.getText().trim().toLowerCase();

    
        if (start.length() != end.length()) {
            JOptionPane.showMessageDialog(this, "Start word and end word must be the same length.");
            return;
        }

        if (!solver.getDictionary().contains(start) || !solver.getDictionary().contains(end)){
            JOptionPane.showMessageDialog(this, "Invalid Input.");
            return;
        }
        Runtime runtime = Runtime.getRuntime();
        long startmemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Start : " + start);
        System.out.println("End : " + end);
        System.out.println("Algorithm : " + algorithm);
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
       
        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = (finalMemory - startmemory);

        
        System.out.println("Memory Used : " + memoryUsed + " bytes");
        updateResultDisplay(result,algorithm);
    }
        
    private void updateResultDisplay(SolverResult result, String algorithm) {
        outputPanel.removeAll();  
        
        if (result.getPath().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No path found.");
        } else {
            for (int i = 0; i < result.getPath().size(); i++) {
                JPanel wordPanel = new JPanel();
                wordPanel.setBackground(Color.BLACK);
                wordPanel.setForeground(Color.WHITE);
                wordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                String current = result.getPath().get(i);
                String prev = i != 0 ? result.getPath().get(i-1) : null;
    
                JLabel num = new JLabel((i + 1) + ". ");
                num.setForeground(Color.WHITE);
                wordPanel.add(num);
    
                for (int j = 0; j < current.length(); j++) {
                    JLabel label = new JLabel(String.valueOf(current.charAt(j)));
                    label.setForeground(Color.WHITE);
                    if (prev != null && j < prev.length() && current.charAt(j) != prev.charAt(j)) {
                        String styledText = "<html><u><font color='green' style='font-weight:bold;'>" + current.charAt(j) + "</font></u></html>";
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
                            "       Execution Time: " + result.getTimeTaken() + " ms" +
                            "       Visited Nodes: " + result.getVisitedNodes());
    }
}
