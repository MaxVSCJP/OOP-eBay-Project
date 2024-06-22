import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ProductListingGUI extends JFrame {

    private DefaultTableModel tableModel;
    private JTable salesTable;

    public ProductListingGUI(ArrayList<Product> listing) {
        setTitle("Sales Listing");
        setSize(800, 600);
        setLocationRelativeTo(null);

        initializeUI();
        addTestData(listing); // Add test data to the table
    }

    private void initializeUI() {
        // Create table model with columns
        tableModel = new DefaultTableModel() {
            // Override getColumnClass to correctly render ImageIcon
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return ImageIcon.class;
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing for all cells
            }
        };
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Image");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity");

        // Create JTable with the table model
        salesTable = new JTable(tableModel);
        salesTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        salesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                    int row = salesTable.rowAtPoint(e.getPoint());
                    int column = salesTable.columnAtPoint(e.getPoint());
                    if (column == 1) {
                        int ClickedProductId = (int) tableModel.getValueAt(row, 0);
                        ProductPage.createProductPage(OrderSQL.getproduct(ClickedProductId));
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(salesTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void addTestData(ArrayList<Product> listing) {
        // once you can pass a listing to this method, uncomment this
         for (Product product : listing) {
            ImageIcon imageIcon = resizeImage(product.getImage(), 50, 50);
            tableModel.addRow(new Object[]{product.getProductId(),new JLabel(imageIcon), product.getName(), product.getPrice(), product.getAmount()});
         }


        /*// Add test data (you can replace this with your actual data loading logic)
        ImageIcon image1 = resizeImage("C:\\Users\\USER\\Desktop\\Listings\\src\\a.jpg", 50, 50);
        ImageIcon image2 = resizeImage("C:\\Users\\USER\\Desktop\\Listings\\src\\b.jpg", 50, 50);
        ImageIcon image3 = resizeImage("C:\\Users\\USER\\Desktop\\Listings\\src\\c.jpg", 50, 50);

        Object[] row1 = {image1, "Product A", "$10.00", "5"};
        Object[] row2 = {image2, "Product B", "$15.00", "3"};
        Object[] row3 = {image3, "Product C", "$20.00", "8"};

        tableModel.addRow(row1);
        tableModel.addRow(row2);
        tableModel.addRow(row3);*/
    }

    // Custom TableCellRenderer for displaying images in the table
    private class ImageRenderer extends DefaultTableCellRenderer {
        JLabel lbl = new JLabel();

        /*@Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            lbl.setIcon((ImageIcon) value);
            lbl.setHorizontalAlignment(JLabel.CENTER);
            return lbl;
        }*/
    }

    // Method to resize image
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imagePath); // Load image from file path
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    // Method to show enlarged image in a dialog
    private void showImageDialog(ImageIcon imageIcon) {
        JLabel label = new JLabel(imageIcon);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Image", JOptionPane.PLAIN_MESSAGE);
    }

    public static void createListingPage(ArrayList<Product> listing) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProductListingGUI ex = new ProductListingGUI(listing);
                ex.setVisible(true);
            }
        });
    }
}
