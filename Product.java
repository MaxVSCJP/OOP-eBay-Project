import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.File;
import java.util.Formatter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Product {
    private int productId;
    private String name;
    private double price;
    private BufferedImage image;
    private String description;
    private int amount;
    private User user;
    private static int proID;
    static{
        try{
            File file = new File("ProductID Counter.txt");
            Scanner scanner = new Scanner(file);
            proID = scanner.nextInt();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String category;


    

    // Constructor
    public Product(String name, double price, String description, int amount, String category, String File) {
        this.productId = proID;
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
        this.user = LoginManager.getActiveUser();
        this.category = category;

        try {
            this.image = ImageIO.read(new File(File));
        } 
        catch (IOException e) {
            this.image = null;
            System.err.println("Sorry can't access the file");
        }

        proID++;
        try{
            File counterFile = new File("ProductID Counter.txt");
            if(!counterFile.exists()){
                counterFile.createNewFile();
            }
            Formatter writer = new Formatter(counterFile);
            writer.format("%d", proID);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public String getCategory(){
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
