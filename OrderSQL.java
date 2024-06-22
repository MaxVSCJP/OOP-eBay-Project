import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;


public class OrderSQL {
    private static String connectionpath = "jdbc:sqlserver://LAPTOP-RRR8Q3CJ:1433;Database=test1 ;user=lovemehateyou;password=alazar11821996;trustServerCertificate=true;";
    //CREATEING A tabel

    private static Connection connect;
    public static void createTable(){
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();
            return;
        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement(
                    "create table Orders("+
                            "orderID INT  PRIMARY KEY,"+
                            "ProductID int NOT NULL,"+
                            "BuyersUsername varchar(20) NOT NULL,"+
                            "Quantity int,"+
                            "OrderStatus varchar(20), "+
                            "TimeofOrder varchar(30),"+
                            "ETA varchar(30),"+
                            "FOREIGN KEY (BuyersUsername) REFERENCES users(username),"+
                            "FOREIGN KEY (ProductID) REFERENCES product(productID) )"
            );
            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("DATABASE CREATED" );

        }
        catch(SQLException E){
            System.out.println("could not connect");
            E.printStackTrace();
        }
    }
    //INSERTING INTO TABLE
    public static void ADD(Orders ord){
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();
            return;
        }
        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("INSERT INTO Orders VALUES(?,?,?,?,?,?,?)");

            statement.setInt(1,ord.getOrderId());
            statement.setInt(2,ord.getProduct().getProductId());
            statement.setString(3,ord.getBuyer().getUsername());
            statement.setInt(4,ord.getQuantity());
            statement.setString(5, ord.getOrderStatus());
            statement.setString(6,ord.getOrderDate());
            statement.setString(7,ord.getETA());

            statement.executeUpdate();
            statement.close();
            connect.close();
        }
        catch(SQLException e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }

    }

    public static void DELETE(Orders ord){
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();
            return;
        }
        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("DELETE FROM Orders WHERE orderID = ?");

            statement.setInt(1, ord.getOrderId());

            statement.executeUpdate();
            statement.close();
            connect.close();
        }
        catch(SQLException e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    public static ArrayList<Orders> retriveSoldItems(String SellerUsername){
        ArrayList<Orders> ordered = new ArrayList<>();
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("""
                    SELECT * FROM Orders
                    Where ProductID in (Select productID from product where SellerUsername = ?)
                    """);
            statement.setString(1, SellerUsername);
            ResultSet result = statement.executeQuery();
            while(result.next()){

                String buyersusername = result.getString("BuyersUsername");
                int productid = result.getInt("ProductID");

                User buyer = getuser(buyersusername);
                Product pro = getproduct(productid);

                Orders orderItems = new Orders(result.getInt("orderID"),pro,result.getInt("Quantity"),buyer,result.getString("OrderStatus"), result.getString("TimeofOrder"), result.getString("ETA"));
                ordered.add(orderItems);
            }
            statement.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }


        return ordered;
    }


    public static ArrayList<Orders> retriveBoughtItems(String BuyerUsername){
        ArrayList<Orders> ordered = new ArrayList<>();
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("""
                    SELECT * FROM Orders
                    Where BuyersUsername = ?
                    """);
            statement.setString(1, BuyerUsername);
            ResultSet result = statement.executeQuery();
            while(result.next()){

                String buyersusername = result.getString("BuyersUsername");
                int productid = result.getInt("ProductID");

                User buyer = getuser(buyersusername);
                Product pro = getproduct(productid);


                Orders orderItems = new Orders(result.getInt("orderID"),pro,result.getInt("Quantity"),buyer,result.getString("OrderStatus"), result.getString("TimeofOrder"), result.getString("ETA"));
                ordered.add(orderItems);
            }
            statement.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }

        return ordered;
    }



    public static User getuser(String username){
        User retrievedCust = null;
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("select * from users where username = ?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                retrievedCust = new User(result.getString("username"),result.getString("Fullname"),result.getString("Email"),result.getDouble("Balance"));
            }
            statement.close();
            connect.close();

        }
        catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
        return retrievedCust;
    }

    public static Product getproduct(int prodID){
        Product retrievedCust = null;
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("select * from product where productID = ?");
            statement.setInt(1, prodID);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                String SellerUsername = result.getString("SellerUsername");
                User Seller = OrderSQL.getuser(SellerUsername);

                String HighestBidderUsername = result.getString("HighestBidder");
                User HighestBidder = OrderSQL.getuser(HighestBidderUsername);

                retrievedCust = new Product(result.getInt("productID"),result.getString("ProductName"), result.getDouble("price"),result.getString("description"),result.getInt("amount"),result.getString("Category"),result.getString("imageData"),result.getDouble("BidPrice"), Seller, result.getString("AuctionExpireTime"), HighestBidder);
                statement.close();
                connect.close();
            }

        }
        catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
        return retrievedCust;
    }

    private static BufferedImage bytes_to_image(byte[] ok){
        ByteArrayInputStream bis = new ByteArrayInputStream(ok);
        BufferedImage image = null;
        try {
            image = ImageIO.read(bis);
        } catch (IOException e) {
            System.out.println("Error reading byte array to image");
            e.printStackTrace();
        }
        return image;
    }

    public static void main(String[] args) {
        //createTable();
    }


}
