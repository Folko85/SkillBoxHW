import javax.swing.*;

public class MyApp {
    private JPanel mainPanel;
    private JTextArea name;
    private JTextArea surname;
    private JTextArea lastname;
    private JButton collapseButton;
    private JLabel nameLabel;
    private JLabel lastnameLabel;
    private JLabel surnameLabel;

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
                if (name.getText().isEmpty() || surname.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Имя и фамилия должны быть заполнены");
                } else {
                    name.setVisible(false);
                    nameLabel.setVisible(false);
                    surname.setVisible(false);
                    surnameLabel.setVisible(false);
                    lastname.setSize(150, 50);
                    lastname.setText(name.getText() + "\n" + lastname.getText() + "\n" + surname.getText());
                    lastnameLabel.setText("ФИО");
                    collapseButton.setText("Expand");
                }
            } else if (collapseButton.getText().equals("Expand")) {
                String[] fio = lastname.getText().split("\n");
                if (fio.length > 3) {
                    JOptionPane.showMessageDialog(mainPanel, "Полное имя не может состоять более чем из трёх слов");
                } else if (fio.length < 2) {
                    JOptionPane.showMessageDialog(mainPanel, "Полное имя не может состоять менее чем из двух слов");
                } else {
                    lastname.setSize(150, -1);
                    name.setText(fio[0]);
                    surname.setText(fio[fio.length - 1]);
                    lastname.setText("");
                    if (fio.length == 3) {
                        lastname.setText(fio[1]);
                    }
                    name.setVisible(true);
                    nameLabel.setVisible(true);
                    surname.setVisible(true);
                    surnameLabel.setVisible(true);
                    lastnameLabel.setText("Отчество");
                    collapseButton.setText("Collapse");
                }
            }
        });
    }
}
