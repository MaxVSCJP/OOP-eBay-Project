import java.util.ArrayList;

public class ProdcutListings {
    private ArrayList<Product> Market = new ArrayList<>();

    public void addToMarket(Product prod){
        Market.add(prod);
    }

    public void removeFromMarket(Product prod){
        Market.remove(prod);
    }
}
