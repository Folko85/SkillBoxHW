import javax.swing.*;

public class MyApp {
    private JPanel mainPanel;
    private JTextField name;
    private JTextField surname;
    private JTextField lastname;
    private JButton collapseButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MyApp");
        frame.setContentPane(new MyApp().mainPanel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public MyApp() {
        collapseButton.addActionListener(actionEvent -> {
            if (collapseButton.getText().equals("Collapse")) {
                collapseButton.setText("Expand");
            } else if (collapseButton.getText().equals("Expand")) {
                collapseButton.setText("Collapse");
            }
        });
    }
}
