import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AddProductGUI extends JFrame {
    private JTextField productNameField;
    private JTextArea productDescription;
    private JTextField productPriceField;
    private JTextField productAmount;
    private JTextField productCategory;
    private JTextField productBidPrice;
    private JButton uploadImageButton;
    private JLabel imageLabel;

    private File selectedFile;

    public AddProductGUI() {

        setTitle("Add Title");
        setLayout(new BorderLayout());

        // Components for product details
        productNameField = new JTextField(20);
        productDescription = new JTextArea(15,30);
        productPriceField = new JTextField(10);
        productAmount = new JTextField(10);
        productCategory = new JTextField(10);
        productBidPrice = new JTextField(10);
        uploadImageButton = new JButton("Upload Image");
        imageLabel = new JLabel();

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(new JLabel("Product Name:"), gbc);
        gbc.gridx++;
        formPanel.add(productNameField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Product Description:"), gbc);
        gbc.gridx++;
        formPanel.add(productDescription, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Product Price:"), gbc);
        gbc.gridx++;
        formPanel.add(productPriceField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Product Quantity:"), gbc);
        gbc.gridx++;
        formPanel.add(productAmount, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JComboBox<String> dropdownMenu = new JComboBox<>(new String[]{"CATEGORY'S","ELECTRONICS", "SPORTS", "MUSIC","HOUSE_HOLDS","CLOTHES","TOYS", "SCHOOL APPLIANCES"});
        dropdownMenu.setPreferredSize(new Dimension(120, dropdownMenu.getPreferredSize().height));
        formPanel.add(dropdownMenu, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        formPanel.add(new JLabel("Product Initial Bid Price:"), gbc);
        gbc.gridx++;
        formPanel.add(productBidPrice, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        formPanel.add(uploadImageButton, gbc);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        add(formPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);


        // Handling image upload button action
        uploadImageButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(getParent());
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedImage originalImage = ImageIO.read(selectedFile);
                    int mediumSize = 150; // Adjust size as needed
                    BufferedImage resizedImage = resizeImage(originalImage, mediumSize, mediumSize);
                    ImageIcon newImageIcon = new ImageIcon(resizedImage);
                    imageLabel.setIcon(newImageIcon);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error uploading image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            // Retrieve product details from fields


            String selectedfilePath = null;
            String name = null;
            double price = 0;
            String description = null; // Added
            int amount = 0; // Added
            String category = null; // Added
            double bidPrice = 0; // Added
            try {
                selectedfilePath = selectedFile.getAbsolutePath();
                name = productNameField.getText();
                price = Double.parseDouble(productPriceField.getText());
                description = productDescription.getText();
                amount = Integer.parseInt(productAmount.getText());
                category = (String) dropdownMenu.getSelectedItem();
                bidPrice = Double.parseDouble(productBidPrice.getText());
                if(selectedfilePath.isEmpty() || name.isEmpty() || description.isEmpty() || category.isEmpty()){
                    throw new ArithmeticException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,"Invalid Entry for Price, Quantity, or BidPrice fields");
                return;
            } catch (ArithmeticException arex){
                JOptionPane.showMessageDialog(null,"Please fill in all fields");
                return;
            }



            // Create Product object
            Product product = new Product(name, price, description, amount, category, selectedfilePath, bidPrice);
            productSQL.ADD(product);
            JOptionPane.showMessageDialog(null,"Successfully Added Product");

        });

        add(saveButton, BorderLayout.SOUTH);
        setLocationRelativeTo(null); // Center the window
        setSize(1000,600);
        setVisible(true);
    }

    // Method to resize an image
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2.dispose();
        return resizedImage;
    }

    public static void main() {
        SwingUtilities.invokeLater(() -> new AddProductGUI());
    }
}


