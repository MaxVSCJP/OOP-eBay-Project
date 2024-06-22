import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginSignupGUI extends JFrame {
    private JPanel loginPanel, signupPanel;
    private JTextField usernameField, NameField, balanceField, emailField, userField;
    private JPasswordField passwordField, passwordField2;
    private JButton loginButton, signUpButton, cancelButton;

    public LoginSignupGUI() {
        setTitle("Login and Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        showLoginPanel(); // Show login panel by default
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
        setSize(800, 500);
    }

    private void initComponents() {
        // Login Panel
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints loginConstraints = new GridBagConstraints();
        loginConstraints.insets = new Insets(5, 5, 5, 5); // Padding around components

        JLabel usernameLabel = new JLabel("Username:");
        loginConstraints.gridx = 0;
        loginConstraints.gridy = 0;
        loginPanel.add(usernameLabel, loginConstraints);

        usernameField = new JTextField(20);
        loginConstraints.gridx = 1;
        loginPanel.add(usernameField, loginConstraints);

        JLabel passwordLabel = new JLabel("Password:");
        loginConstraints.gridx = 0;
        loginConstraints.gridy = 1;
        loginPanel.add(passwordLabel, loginConstraints);

        passwordField = new JPasswordField(20);
        loginConstraints.gridx = 1;
        loginPanel.add(passwordField, loginConstraints);

        JLabel signUpLabel = new JLabel("Don't have an account?");
        loginConstraints.gridx = 0;
        loginConstraints.gridy = 2;
        loginPanel.add(signUpLabel, loginConstraints);

        JLabel signUpLink = new JLabel("Sign up here");
        signUpLink.setForeground(Color.BLUE);
        signUpLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showSignUpPanel(); // Switch to sign-up panel on click
            }
        });
        loginConstraints.gridx = 1;
        loginPanel.add(signUpLink, loginConstraints);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(59, 89, 182));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                // Perform login authentication or other actions
                try{
                    if(!loginSQL.login(username, password)){
                        throw new ArithmeticException();
                    }
                } catch (ArithmeticException incorrect){
                    JOptionPane.showMessageDialog(null, "Incorrect Username or Password.", "Error", 0);
                    return;
                }
                JOptionPane.showMessageDialog(LoginSignupGUI.this, "Successful Login " + username);

                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(loginPanel);
                parentFrame.dispose();
                MainPage.createMainPage();
            }
        });
        loginConstraints.gridx = 0;
        loginConstraints.gridy = 3;
        loginConstraints.gridwidth = 2;
        loginConstraints.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, loginConstraints);

        // Sign Up Panel
        signupPanel = new JPanel(new GridBagLayout());
        signupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints signupConstraints = new GridBagConstraints();
        signupConstraints.insets = new Insets(5, 5, 5, 5);

        JLabel UsernameLabel = new JLabel("Username:");
        signupConstraints.gridx = 0;
        signupConstraints.gridy = 0;
        signupPanel.add(UsernameLabel, signupConstraints);

        userField = new JTextField(20);
        signupConstraints.gridx = 1;
        signupPanel.add(userField, signupConstraints);

        JLabel NameLabel = new JLabel("Full Name:");
        signupConstraints.gridx = 0;
        signupConstraints.gridy = 1;
        signupPanel.add(NameLabel, signupConstraints);

        NameField = new JTextField(20);
        signupConstraints.gridx = 1;
        signupPanel.add(NameField, signupConstraints);

        JLabel BalanceLabel = new JLabel("Initial Balance:");
        signupConstraints.gridx = 0;
        signupConstraints.gridy = 2;
        signupPanel.add(BalanceLabel, signupConstraints);

        balanceField = new JTextField(20);
        signupConstraints.gridx = 1;
        signupPanel.add(balanceField, signupConstraints);

        JLabel emailLabel = new JLabel("Email:");
        signupConstraints.gridx = 0;
        signupConstraints.gridy = 3;
        signupPanel.add(emailLabel, signupConstraints);

        emailField = new JTextField(20);
        signupConstraints.gridx = 1;
        signupPanel.add(emailField, signupConstraints);

        JLabel signupPasswordLabel = new JLabel("Password:");
        signupConstraints.gridx = 0;
        signupConstraints.gridy = 4;
        signupPanel.add(signupPasswordLabel, signupConstraints);

        passwordField2 = new JPasswordField(20);
        signupConstraints.gridx = 1;
        signupPanel.add(passwordField2, signupConstraints);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(59, 89, 182));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Username = userField.getText();
                try{
                    if(!userSQL.UsernameAvailability(Username)){
                        throw new RuntimeException();
                    }
                } catch (RuntimeException unavailable){
                    JOptionPane.showMessageDialog(null, "Username Unavailable. Please try a different one", "Error", 0);
                    return;
                }

                String FullName = NameField.getText();
                String balanceText = balanceField.getText();
                double balance;
                try {
                     balance = Double.parseDouble(balanceText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a Number for Balance", "Error", 0);
                    return;
                }

                String email = emailField.getText();
                char[] passwordChars = passwordField2.getPassword();
                String password = new String(passwordChars);

                JOptionPane.showMessageDialog(LoginSignupGUI.this, "Successfully Signed Up");

                User newUser = new User(Username, FullName, email, balance);
                userSQL.ADD(newUser);
                SignUp newSignUp = new SignUp(Username, password);
                loginSQL.signup(newSignUp);

                showLoginPanel();
            }
        });
        signupConstraints.gridx = 0;
        signupConstraints.gridy = 5;
        signupConstraints.gridwidth = 2;
        signupConstraints.anchor = GridBagConstraints.CENTER;
        signupPanel.add(signUpButton, signupConstraints);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginPanel(); // Switch to login panel on cancel
            }
        });
        signupConstraints.gridx = 0;
        signupConstraints.gridy = 6;
        signupConstraints.gridwidth = 2;
        signupConstraints.anchor = GridBagConstraints.CENTER;
        signupPanel.add(cancelButton, signupConstraints);
    }

    private void showSignUpPanel() {
        getContentPane().removeAll();
        add(signupPanel);
        revalidate();
        repaint();
    }

    private void showLoginPanel() {
        getContentPane().removeAll();
        add(loginPanel);
        revalidate();
        repaint();
    }


    public static void main() {
        SwingUtilities.invokeLater(LoginSignupGUI::new);
    }
}

