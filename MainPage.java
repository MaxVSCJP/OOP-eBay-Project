import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;

public class MainPage {
//    public static void main(String[] args) {
    public static void createMainPage(){
        SwingUtilities.invokeLater(() -> {
            // JFrameB
            JFrame frameB = new JFrame("Main Frame");
            frameB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameB.setSize(800, 600);
            frameB.setLayout(new BorderLayout());
            frameB.setLocationRelativeTo(null);

            // JPanel for buttons and search bar
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());
            topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Buttons panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

            // Button 1 (Menu button)
            JButton menuButton = createButton("Menu", "Menu icon.png", frameB);
            menuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MenuPage.createMenuPage();
                }
            });
            buttonPanel.add(menuButton);

            // Button 2 (Cart button)
            JButton cartButton = createButton("Cart", "Cart icon.png",frameB);
            cartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ShoppingCartPage.createShoppingCartPage(ShoppingCart.Cart);
                }
            });
            buttonPanel.add(cartButton);


            JButton Messageing = new JButton("YOUR CHAT");
            Messageing.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //  ChatPage.createChatPage();
                    HashSet<String> people = MesssageSQL.retrive();
                    ArrayList<User> use = new ArrayList<>();

                    for(String person : people){
                        User pep = MesssageSQL.getuser(person);
                        use.add(pep);
                    }
                    MessagingPageGUI page = new MessagingPageGUI(use);

                }
            });
            buttonPanel.add(Messageing);


            topPanel.add(buttonPanel, BorderLayout.WEST);


            JPanel searchBarPanel = new JPanel(new BorderLayout());

            // Dropdown menu (placeholder)
            JComboBox<String> dropdownMenu = new JComboBox<>(new String[]{"CATEGORY'S","ELECTRONICS", "SPORTS", "MUSIC","HOUSE_HOLDS","CLOTHES","TOYS", "SCHOOL APPLIANCES"});
            dropdownMenu.setPreferredSize(new Dimension(120, dropdownMenu.getPreferredSize().height));
            searchBarPanel.add(dropdownMenu, BorderLayout.WEST);

            // Search bar with magnifying glass button and dropdown menu
            JTextField searchBar = new JTextField();
            JButton searchButton = createButton(null, "search_icon.png", frameB);
            searchButton.addActionListener(e -> {
                // Action when search button is clicked
                String SelectedCat = (String) dropdownMenu.getSelectedItem();

                String query = searchBar.getText();
                    if(SelectedCat.equals("CATEGORY'S") && !(query.isEmpty())){
                        ProductListingGUI.createListingPage(productSQL.namesearch(query));
                    }
                    else if (!(SelectedCat.equals("CATEGORY'S")) && !(query.isEmpty())){
                        ProductListingGUI.createListingPage(productSQL.searchboth(query, SelectedCat));
                    }
                    else if (!(SelectedCat.equals("CATEGORY'S"))){
                        ProductListingGUI.createListingPage(productSQL.catsearch(SelectedCat));
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Nothing Searched", "Search Empty", JOptionPane.WARNING_MESSAGE);
                    }
            });
            searchBarPanel.add(searchBar, BorderLayout.CENTER);
            searchBarPanel.add(searchButton, BorderLayout.EAST);


            topPanel.add(searchBarPanel, BorderLayout.CENTER);

            // Add the top panel to JFrameB
            frameB.add(topPanel, BorderLayout.NORTH);

            // JPanel frameA (inside JFrameB as JPanel)
            JPanel frameA = new JPanel();
            frameA.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columns, with 10px horizontal and vertical gaps
            frameA.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
             String prod [] = {"C:\\Users\\maass\\Pictures\\2023\\July\\pexels-frank-cone-2258536.jpg","C:\\Users\\maass\\Pictures\\2023\\July\\pexels-james-wheeler-1486974.jpg"};
            // Example: Adding product entries
            for (int i = 0; i < 2; i++) {
                JPanel productPanel = new JPanel(new BorderLayout());
                productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                // Product image (placeholder)
                ImageIcon productImageIcon = resizeImageIcon(prod[i], 150, 150); // Resize image
                JLabel imageLabel = new JLabel(productImageIcon);
                imageLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Action when product image is clicked
                        JOptionPane.showMessageDialog(frameB, "Open product details");
                    }
                });
                productPanel.add(imageLabel, BorderLayout.CENTER);

                // Product details panel
                JPanel detailsPanel = new JPanel();
                detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
                detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel productNameLabel = new JLabel("Product " + (i + 1));
                productNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
                detailsPanel.add(productNameLabel);

                JLabel priceLabel = new JLabel("$19.99");
                priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                detailsPanel.add(priceLabel);

                JButton addButton = new JButton("+");
                addButton.addActionListener(e -> {
                    // Action when "+" button is clicked
                    JOptionPane.showMessageDialog(frameB, "Add to cart");
                });
                addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                detailsPanel.add(addButton);

                productPanel.add(detailsPanel, BorderLayout.SOUTH);
                frameA.add(productPanel);
            }

            // Add frameA panel to a JScrollPane for scrolling
            JScrollPane scrollPane = new JScrollPane(frameA);
            frameB.add(scrollPane, BorderLayout.CENTER);

            // Display the main JFrame (JFrameB)
            frameB.setVisible(true);
        });
    }

    // Helper method to create styled buttons with icons
    private static JButton createButton(String tooltip, String iconPath, JFrame parentFrame) {
        JButton button = new JButton(new ImageIcon(iconPath));
        button.setToolTipText(tooltip);
        button.setFocusPainted(false); // Remove default border
        button.setContentAreaFilled(false); // Make background transparent
        return button;
    }

    // Helper method to resize ImageIcon
    private static ImageIcon resizeImageIcon(String iconPath, int width, int height) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
