import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.File;
import java.util.Formatter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Product {
    private int productId;
    private String name;
    private double price;
    private String image;
    private String description;
    private int amount;
    private User productOwner;
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
    private double BidPrice;
    private String auctionExpireTime;
    private User highestBidder;



    // Constructor for Bidding and Buy Now
    public Product(String name, double price, String description, int amount, String category, String image, double BidPrice) {
        this.productId = proID;
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
        this.productOwner = LoginManager.getActiveUser();
        this.category = category;
        this.image = image;

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
        this.BidPrice = BidPrice;
        this.auctionExpireTime = LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.highestBidder = LoginManager.getActiveUser();

    }

    public Product(int productId, String name, double price, String description, int amount, String category, String image, double bidPrice, User productOwner, String auctionExpireTime, User highestBidder) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.amount = amount;
        this.productOwner = productOwner;
        this.category = category;
        this.BidPrice = bidPrice;
        this.auctionExpireTime = auctionExpireTime;
        this.highestBidder = highestBidder;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public User getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(User owner) {
        this.productOwner = owner;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBidPrice(){
        return BidPrice;
    }

    public void setBidPrice(double price){
        this.BidPrice = price;
    }

    public User getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(User bidder) {
        this.highestBidder = bidder;
    }

    public String getAuctionExpireTime() {
        return auctionExpireTime;
    }
}
