import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StartingCheck {

    static Connection connect;

    public static void checkForBids() {
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }

        try {
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement checkBids = connect.prepareStatement("""
                    Select * from product""");
            ResultSet result = checkBids.executeQuery();

            while(result.next()){
                String expireTime = result.getString("AuctionExpireTime");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

                LocalDateTime auctionExpire = LocalDateTime.parse(expireTime, formatter);
                LocalDateTime nowTime = LocalDateTime.now();

                if(nowTime.isAfter(auctionExpire)){
                    String SellerUsername = result.getString("SellerUsername");
                    User Seller = OrderSQL.getuser(SellerUsername);

                    String HighestBidderUsername = result.getString("HighestBidder");
                    User HighestBidder = OrderSQL.getuser(HighestBidderUsername);

                    Product prod = new Product(result.getInt("productID"),result.getString("ProductName"), result.getDouble("price"),result.getString("description"),result.getInt("amount"),result.getString("Category"),result.getString("imageData"),result.getDouble("BidPrice"), Seller, result.getString("AuctionExpireTime"), HighestBidder);
                    Orders order = new Orders(prod, prod.getAmount(), prod.getHighestBidder(), "On its Way");

                    OrderSQL.ADD(order);

                    userSQL.upadte(HighestBidder,"Balance", HighestBidder.getBalance()-(prod.getAmount()*prod.getPrice()));

                    userSQL.upadte(Seller,"Balance", Seller.getBalance()-(prod.getAmount()*prod.getPrice()));


                }
            }

        } catch (SQLException sqle) {
            System.out.println("Connection Problem");
            sqle.printStackTrace();
        }
    }
}
