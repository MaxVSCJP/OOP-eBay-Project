/* import java.awt.*;
import javax.swing.*; */

public class Main {
    public static void main(String[] args) {
        //LoginManager.main(args);

        Product Laptop = new Product("Asus ROG G14", 3000, "Intel Core Ultra 9, Nvidia RTX 4060",3, "C:\\Users\\maass\\Pictures\\Screenshots\\Screenshot 2024-01-26 020348.png");
        Product Phone = new Product("Pixel 7a", 499, "New 2023 affordable by Pixel Google",6, "C:\\Users\\maass\\Pictures\\Screenshots\\Screenshot 2023-08-15 195225.png");

        System.out.println(Phone.getProductId());
        System.out.println(Laptop.getProductId());
    
    }
}
