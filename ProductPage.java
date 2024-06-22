
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductPage {

    public static void createProductPage(Product prod) {
        SwingUtilities.invokeLater(() -> {

            createAndShowGUI(prod);
        });
    }

    private static void createAndShowGUI(Product prod) {
        JFrame frame = new JFrame("product view");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the frame on screen

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel for image and product details
        JPanel productPanel = new JPanel(new GridBagLayout());
        productPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Image panel
        JLabel productImageLabel = new JLabel();
        ImageIcon resizedImageIcon = createResizedImageIcon(prod.getImage(), 300, 300);
        productImageLabel.setIcon(resizedImageIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        productPanel.add(productImageLabel, gbc);

        // Product details panel
        JPanel detailsPanel = new JPanel(new GridLayout(5,1));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel productNameLabel = new JLabel("NAME: " + prod.getName());
        productNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        detailsPanel.add(productNameLabel);

        /*JLabel productDescriptionLabel = new JLabel(prod.getDescription());
        productDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsPanel.add(productDescriptionLabel);*/

        String str = String.valueOf(prod.getPrice());

        JLabel priceLabel = new JLabel("PRICE: " +str + " ");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        detailsPanel.add(priceLabel);

        JLabel sellerLabel = new JLabel("Seller: " +prod.getProductOwner().getName());
        sellerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        detailsPanel.add(sellerLabel);


        String str2 = String.valueOf(prod.getAmount());

        JLabel Quantity = new JLabel("QUANTITY: "+str2);
        Quantity.setFont(new Font("Arial", Font.BOLD, 18));
        detailsPanel.add(Quantity);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        productPanel.add(detailsPanel, gbc);

        // Description panel
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JTextArea descriptionTextArea = new JTextArea("Product Description",4,40);
        descriptionTextArea.setText(prod.getDescription());
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setEditable(false);

        descriptionPanel.add(descriptionTextArea, BorderLayout.CENTER);


        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 1;
        productPanel.add(descriptionPanel, gbc);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton chatbutton = new JButton("CHAT");
        JButton placeBidButton = new JButton("Place Bid");
        JButton addToCartButton = new JButton("Add to Cart");

        chatbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open chat window
                String content = JOptionPane.showInputDialog("YOU MUST SEND FIRST");
                if(content == null){
                    return;
                }
                else{
                    Massage mess = new Massage(LoginManager.getActiveUser(), prod.getProductOwner(), content);
                    MesssageSQL.ADD(mess);
                    JOptionPane.showMessageDialog(null,"Message Sent");
                }
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String quantityChoose = JOptionPane.showInputDialog(null,"Input Quantity", "Amount", JOptionPane.QUESTION_MESSAGE);
                int quant;
                try {
                    quant = Integer.parseInt(quantityChoose);
                    if(quant<1){
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please Enter a Positive Number");
                    return;
                }

                if(quant<=prod.getAmount()){
                    ShoppingCart.addToCart(prod, quant);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Desired quantity greater than available");
                }
                JOptionPane.showMessageDialog(null, "Added to Cart");
            }
        });

        placeBidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Stringbid = JOptionPane.showInputDialog(null,"Current Highest Bid: "+ prod.getBidPrice(), "Place your Bid", JOptionPane.PLAIN_MESSAGE);
                double realBid;

                try {
                    realBid = Double.parseDouble(Stringbid);
                    if(realBid<=prod.getBidPrice()){
                        throw new ArithmeticException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid entry, Please enter a valid bid");
                    return;
                } catch (ArithmeticException ar){
                    JOptionPane.showMessageDialog(null, "Invalid entry, Bid must be higher than current bid.");
                    return;
                }

                if(realBid<LoginManager.getActiveUser().getBalance()) {
                    productSQL.upadte(prod, "BidPrice", realBid);

                    productSQL.upadte(prod, "HighestBidder", LoginManager.getActiveUser().getUsername());
                }
                else{
                    JOptionPane.showMessageDialog(null, "Insufficient Funds");
                }
            }
        });

        if(!(prod.getProductOwner().getUsername().equals(LoginManager.getActiveUser().getUsername()))) {
            buttonsPanel.add(chatbutton);
            buttonsPanel.add(placeBidButton);
            buttonsPanel.add(addToCartButton);
        }

        gbc.gridx = 1;
        gbc.gridy = 2;
        productPanel.add(buttonsPanel, gbc);

        mainPanel.add(productPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static ImageIcon createResizedImageIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
