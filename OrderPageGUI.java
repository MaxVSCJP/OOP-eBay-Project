import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class OrderPageGUI extends JFrame {

    public OrderPageGUI() {
        setTitle("Order Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Adjusted frame size for better visibility

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical arrangement
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Sample data for items
        Item[] items = {
                new Item("Item 1", "C:\\Users\\USER\\Desktop\\Transact\\src\\a.jpg", "10 mins", "$5.00"),
                new Item("Item 2", "C:\\Users\\USER\\Desktop\\Transact\\src\\b.jpg", "15 mins", "$7.50"),
                new Item("Item 3", "C:\\Users\\USER\\Desktop\\Transact\\src\\c.jpg", "12 mins", "$6.20")
        };

        for (Item item : items) {
            JPanel itemPanel = createItemPanel(item);
            mainPanel.add(itemPanel);
            // Add space between items
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
        setVisible(true);
    }

    private JPanel createItemPanel(Item item) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2)); // Light gray border

        // Load and scale the image
        BufferedImage image = null;
        try {
            File file = new File(item.imageUrl);
            image = ImageIO.read(file);
            // Scale the image to fit within a maximum size (e.g., 200x200 pixels)
            Image scaledImage = scaleImage(image, 200, 200);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(JLabel.CENTER); // Center align image
            itemPanel.add(imageLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Panel for item details
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5)); // Added gaps between components
        infoPanel.setBackground(Color.WHITE); // White background for text area

        JLabel nameLabel = new JLabel("<html><div style='text-align: center;'><b>" + item.name + "</b></html>");
        JLabel timeLabel = new JLabel("<html><div style='text-align: center;'>Estimated Time: " + item.estimatedTime + "</html>");
        JLabel priceLabel = new JLabel("<html><div style='text-align: center;'>Price: " + item.price + "</html>");

        infoPanel.add(nameLabel);
        infoPanel.add(timeLabel);
        infoPanel.add(priceLabel);

        itemPanel.add(infoPanel, BorderLayout.SOUTH);

        return itemPanel;
    }

    // Method to scale image to specified dimensions while maintaining aspect ratio
    private Image scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resizedImage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrderPageGUI());
    }

    // Class representing an item with its details
    private static class Item {
        String name;
        String imageUrl;
        String estimatedTime;
        String price;

        public Item(String name, String imageUrl, String estimatedTime, String price) {
            this.name = name;
            this.imageUrl = imageUrl;
            this.estimatedTime = estimatedTime;
            this.price = price;
        }
    }
}
