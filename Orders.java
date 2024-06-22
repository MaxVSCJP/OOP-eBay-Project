import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Scanner;

public class Orders {

    private int OrderId;

    private static int OrdID;
    static{
        try{
            File file = new File("OrderID Counter.txt");
            Scanner scanner = new Scanner(file);
            OrdID = scanner.nextInt();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private Product product;
    private int quantity;
    private User buyer;
    private String orderStatus;
    private String orderDate;
    private String ETA;


    public Orders(Product product, int quantity, User buyer, String orderStatus) {
        this.OrderId = OrdID;
        this.product = product;
        this.quantity = quantity;
        this.buyer = buyer;
        this.orderStatus = orderStatus;

        OrdID++;
        try{
            File counterFile = new File("OrderID Counter.txt");
            if(!counterFile.exists()){
                counterFile.createNewFile();
            }
            Formatter writer = new Formatter(counterFile);
            writer.format("%d", OrdID);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.ETA = LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public Orders(int orderId, Product product, int quantity, User buyer, String orderStatus, String orderDate, String ETA) {
        this.OrderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.buyer = buyer;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.ETA = ETA;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getETA() {
        return ETA;
    }

    public void setETA(String ETA) {
        this.ETA = ETA;
    }
}
