import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPage extends JFrame {

    public MenuPage() {
        setTitle("Indented JPanel Example");
        setPreferredSize(new Dimension(600, 400));

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding to main panel

        // Create left panel
        JPanel leftPanel = createLeftPanel("USER@BUYER.COM", "user_buyer_icon.png");

        // Create right panel
        JPanel rightPanel = createRightPanel("USER@SELLER.COM", "user_seller_icon.png");

        // Add left and right panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Add main panel to JFrame
        add(mainPanel);

        pack(); // Pack components tightly
        setLocationRelativeTo(null); // Center window
    }

    private JPanel createLeftPanel(String userEmail, String iconPath) {
        JPanel sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setBackground(Color.lightGray);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding for content

        // User icon
        ImageIcon userIcon = resizeImageIcon(iconPath, 100, 100);
        JLabel userLabel = new JLabel(userEmail, SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add user icon and label
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0); // Bottom margin
        sidePanel.add(new JLabel(userIcon), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0); // Reset insets for buttons

        // Add buttons

        JButton OrderHistoryButton = new JButton("Orders");
        OrderHistoryButton.setPreferredSize(new Dimension(200, 40));
        OrderHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PurchaseHistoryApp.BoughtandSoldPage(OrderSQL.retriveBoughtItems(LoginManager.getActiveUser().getUsername()));
            }
        });
        gbc.gridy++;
        sidePanel.add(OrderHistoryButton, gbc);


        JButton SalesButton = new JButton("Sales");
        SalesButton.setPreferredSize(new Dimension(200, 40));
        SalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PurchaseHistoryApp.BoughtandSoldPage(OrderSQL.retriveSoldItems(LoginManager.getActiveUser().getUsername()));

            }
        });
        gbc.gridy++;
        sidePanel.add(SalesButton, gbc);


        return sidePanel;
    }


    private JPanel createRightPanel(String userEmail, String iconPath) {
        JPanel sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setBackground(Color.lightGray);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding for content

        // User icon
        ImageIcon userIcon = resizeImageIcon(iconPath, 100, 100);
        JLabel userLabel = new JLabel(userEmail, SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add user icon and label
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0); // Bottom margin
        sidePanel.add(new JLabel(userIcon), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0); // Reset insets for buttons

        // Add buttons

        JButton AddProductbutton = new JButton("Add Product");
        AddProductbutton.setPreferredSize(new Dimension(200, 40));
        AddProductbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProductGUI.main();
            }
        });
        gbc.gridy++;
        sidePanel.add(AddProductbutton, gbc);

        JButton UserManagerbutton = new JButton("User Account Management");
        UserManagerbutton.setPreferredSize(new Dimension(200, 40));
        UserManagerbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountManagerGUI.createAccountEditPage();
            }
        });
        gbc.gridy++;
        sidePanel.add(UserManagerbutton, gbc);

        gbc.gridy++;
        JLabel BalanceLabel = new JLabel("Balance: "+ OrderSQL.getuser(LoginManager.getActiveUser().getUsername()).getBalance());
        sidePanel.add(BalanceLabel,gbc);

        return sidePanel;
    }


    // Helper method to resize ImageIcon
    private static ImageIcon resizeImageIcon(String iconPath, int width, int height) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static void createMenuPage() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MenuPage gui = new MenuPage();
                gui.setVisible(true);
            }
        });
    }
}
