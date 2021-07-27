import javax.swing.*;

public class Desktop {
    private JPanel mainPanel;
    private JButton collapseButton;
    private JTextField nameField;
    private JTextField surNameField;
    private JTextField lastNameField;

    public Desktop() {

//        collapseButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                if (collapseButton.getText().equals("Collapse")) {
//                    collapseButton.setText("Expand");
//                }
//                if (collapseButton.getText().equals("Expand")) {
//                    collapseButton.setText("Collapse");
//                }
//            }
//        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
