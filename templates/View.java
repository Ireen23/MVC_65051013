package templates;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class View extends JFrame {
    private JTextArea textArea;
    private JButton startButton;

    public View() {
        setTitle("Cow Bowling Competition");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("Cow Bowling Competition", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        startButton = new JButton("Start Game");
        add(startButton, BorderLayout.SOUTH);
    }

    public void displayMessage(String message) {
        textArea.append(message + "\n");
    }

    public JButton getStartButton() {
        return startButton;
    }

    public void clearDisplay() {
        textArea.setText("");
    }
}
