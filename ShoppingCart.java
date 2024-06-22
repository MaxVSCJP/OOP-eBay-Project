import java.util.HashSet;

public class ShoppingCart {
    static HashSet<Product> Cart = new HashSet<>();

    public static void addToCart(Product product, int qty){
        product.setAmount(qty);
        Cart.add(product);
    }

    public static void removeFromCart(Product product){
        Cart.remove(product);
    }
}
