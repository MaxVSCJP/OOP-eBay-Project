import java.util.ArrayList;

public class ProdcutListings {
    public static ArrayList<Product> Market = new ArrayList<>();

    public static void addToMarket(Product prod){
        Market.add(prod);
    }

    public static void removeFromMarket(Product prod){
        Market.remove(prod);
    }

    public static ArrayList<Product> searchMarket(String productName){
        ArrayList<Product> result = new ArrayList<>();
        for (Product prod : Market){
            if (prod.getName().contains(productName)){
                result.add(prod);
            }
        }
        return result;
    }

    public static ArrayList<Product> UsersListings(){
        User users = LoginManager.getActiveUser(); 
        ArrayList<Product> result = new ArrayList<>();
        for (Product prod : Market){
            if (prod.getUser().equals(users)){
                result.add(prod);
            }
        }
        return result;
    }
}
