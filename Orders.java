import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Orders {
    private Product product;
    private int quantity;
    private User buyer;
    private String orderStatus;
    private String orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    private String ETA = LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));


    public Orders(Product product, int quantity, User buyer, String orderStatus) {
        this.product = product;
        this.quantity = quantity;
        this.buyer = buyer;
        this.orderStatus = orderStatus;
    }
}
