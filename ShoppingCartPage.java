import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class ShoppingCartPage {

    static JFrame frame;
    static JPanel productsPanel;
    public static void createShoppingCartPage(HashSet<Product> productSet) {
        createAndShowGUI(productSet);
    }

    private static void createAndShowGUI(HashSet<Product> productSet) {
        frame = new JFrame("Product Catalog");
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null); // Center the frame on screen

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Panel to hold products arranged both horizontally and vertically
        productsPanel = new JPanel(new GridLayout(0, 3, 20, 20)); // 3 columns, with 20px horizontal and vertical gap
        productsPanel.setBackground(Color.WHITE);


        for (Product prod: productSet) {
            JPanel productItemPanel = createProductItemPanel(prod.getName(), prod.getPrice(), prod.getAmount(), prod.getImage(), prod);
            productsPanel.add(productItemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createProductItemPanel(String productName, double price, int quantity,String imagePath, Product prod) {
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        productPanel.setBackground(Color.WHITE);

        // Image panel
        ImageIcon imageIcon = createResizedImageIcon(imagePath, 150, 150);
        JLabel imageLabel = new JLabel(imageIcon);
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        productPanel.add(imagePanel, BorderLayout.CENTER);

        // Details panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        detailsPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(productName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailsPanel.add(nameLabel);

        JLabel priceLabel = new JLabel(String.valueOf(price));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailsPanel.add(priceLabel);


        JLabel quantityLabel = new JLabel(String.valueOf(quantity));
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 16));
        quantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailsPanel.add(quantityLabel);


        JLabel totalPriceLabel = new JLabel(String.valueOf(quantity*price));
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailsPanel.add(totalPriceLabel);


        JButton buyButton = new JButton("Buy Now");
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.setBackground(new Color(59, 89, 182));
        buyButton.setForeground(Color.WHITE);
        buyButton.setFocusPainted(false);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(LoginManager.getActiveUser().getBalance() >= quantity*price) {
                    Orders newOrder = new Orders(prod, quantity, LoginManager.getActiveUser(), "On it's Way");
                    OrderSQL.ADD(newOrder);
                    User payingUser = LoginManager.getActiveUser();
                    userSQL.upadte(payingUser, "Balance", payingUser.getBalance() - quantity * price);

                    User receivingUser = prod.getProductOwner();
                    userSQL.upadte(receivingUser, "Balance", receivingUser.getBalance() + quantity * price);

                    if (prod.getAmount() <= 0) {
                        productSQL.DELETE(prod);
                    } else {
                        int initialQuantity = OrderSQL.getproduct(prod.getProductId()).getAmount();
                        productSQL.upadte(prod, "amount", initialQuantity - prod.getAmount());
                    }
                    JOptionPane.showMessageDialog(null, "Order Placed");
                    ShoppingCart.removeFromCart(prod);
                    productsPanel.remove(productPanel);
                    productsPanel.revalidate();
                    productsPanel.repaint();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                }
            }
        });


        detailsPanel.add(buyButton);

        productPanel.add(detailsPanel, BorderLayout.SOUTH);

        return productPanel;
    }

    private static ImageIcon createResizedImageIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
