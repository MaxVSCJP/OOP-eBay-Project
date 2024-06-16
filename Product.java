import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.File;
import java.io.IOException;

public class Product {
    private int productId;
    private String name;
    private double price;
    private BufferedImage image;
    private String description;
    private int amount;
    private User user;
    private static int proID;


    // Constructor
    public Product(String name, double price, String description, int amount, String File) {
        this.productId = proID;
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
        this.user = LoginManager.getActiveUser();

        try {
            this.image = ImageIO.read(new File(File));
        } 
        catch (IOException e) {
            this.image = null;
            System.err.println("sorry cant access the file");
        }

        proID++;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
