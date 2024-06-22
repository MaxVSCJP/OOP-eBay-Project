import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryApp extends JFrame {

    private static JPanel mainPanel;


    private void updateUI(ArrayList<Orders> products) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical layout

        // Create a scroll pane to hold the main panel
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add components to the JFrame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window

        for (Orders item : products) {
            JPanel itemPanel = new JPanel(new GridLayout(1, 0));
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            itemPanel.setBackground(Color.WHITE); // Set background color

            // Use HTML for multiline labels for better formatting

            ImageIcon icon = new ImageIcon(item.getProduct().getImage());

            JLabel ImageLabel = new JLabel(icon);
            JLabel nameLabel = new JLabel("<html><b>Product:</b> " + item.getProduct().getName() + "</html>");
            JLabel priceLabel = new JLabel("<html><b>Price:</b> $" + item.getProduct().getPrice() + "</html>");
            JLabel QuantityLabel = new JLabel("<html><b>Quantity:</b> " + item.getQuantity() + "</html>");
            JLabel TotalPriceLabel = new JLabel("<html><b>Total Price:</b> " + item.getProduct().getPrice() * item.getQuantity() + "</html>");
            JLabel OrderDateLabel = new JLabel("<html><b>Date:</b> " + item.getOrderDate() + "</html>");
            JLabel ETALabel = new JLabel("<html><b>ETA:</b> " + item.getETA() + "</html>");
            JLabel OrderStatusLabel = new JLabel("<html><b>Order Status:</b> " + item.getOrderStatus() + "</html>");

            itemPanel.add(ImageLabel);
            itemPanel.add(nameLabel);
            itemPanel.add(priceLabel);
            itemPanel.add(QuantityLabel);
            itemPanel.add(TotalPriceLabel);
            itemPanel.add(OrderDateLabel);
            itemPanel.add(ETALabel);
            itemPanel.add(OrderStatusLabel);

            mainPanel.add(itemPanel);
        }

        // Refresh the main panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void BoughtandSoldPage(ArrayList<Orders> products) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PurchaseHistoryApp app = new PurchaseHistoryApp();
                app.updateUI(products);
                app.setVisible(true);
            }
        });
    }
}
