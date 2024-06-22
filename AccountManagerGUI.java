import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountManagerGUI extends JFrame {
    private JTextField userNameField;
    private JTextField emailField;

    private JTextField balanceField;

    static Connection connect;

    // Placeholder methods for backend integration
    private void saveUserData(String Name, String email, double balance) {
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }

        try {
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement updateUser = connect.prepareStatement("""
                    Update users
                    Set Fullname = ?, Email = ?, Balance = ?
                    Where username = ?""");
            updateUser.setString(1,Name);
            updateUser.setString(2,email);
            updateUser.setDouble(3,balance);
            updateUser.setString(4,LoginManager.getActiveUser().getUsername());

            updateUser.executeUpdate();
            updateUser.close();
            connect.close();
        }
        catch (SQLException sqle){
            System.out.println("Couldn't Connect");
            sqle.printStackTrace();
        }
    }

    public AccountManagerGUI() {
        setTitle("Account Manager");
        setLayout(new BorderLayout());

        // Create JPanel for the input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20)); // Indent top, left, right

        // Constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Name field
        JLabel userNameLabel = new JLabel("Name:");
        userNameField = new JTextField(20);
        inputPanel.add(userNameLabel, gbc);
        gbc.gridx++;


        inputPanel.add(userNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Email field
        JLabel emailLabel = new JLabel("Email Address:");
        emailField = new JTextField(20);
        inputPanel.add(emailLabel, gbc);
        gbc.gridx++;


        inputPanel.add(emailField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;


        // Balance Field
        JLabel balanceLabel = new JLabel("Balance:");
        balanceField = new JTextField(20);
        inputPanel.add(balanceLabel, gbc);
        gbc.gridx++;

        inputPanel.add(balanceField, gbc);


        gbc.gridx = 0;
        gbc.gridy++;

        JButton saveButton = new JButton("Save");
        inputPanel.add(saveButton, gbc);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = userNameField.getText();
                String emailEdit = emailField.getText();
                String updateBalance = balanceField.getText();
                double newBalance;

                try {
                    newBalance = Double.parseDouble(updateBalance);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Input valid balance deposit");
                    return;
                }
                saveUserData(fullName, emailEdit, newBalance);
                JOptionPane.showMessageDialog(null, "User Updated");
            }
        });

        // Add inputPanel to JFrame
        add(inputPanel, BorderLayout.NORTH);

        // Set JFrame properties
        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    public static void createAccountEditPage() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AccountManagerGUI();
            }
        });
    }
}
