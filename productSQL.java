import java.sql.*;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.util.ArrayList;
import java.io.*;

public class productSQL {
    static String connectionpath = "jdbc:sqlserver://MAASSAS\\SQLEXPRESS;DatabaseName=Project;user=OOP-Project;password=12345678;trustServerCertificate=true;";
    //CREATEING A tabel
    static Connection connect;
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
            PreparedStatement statement = connect.prepareStatement("""
                CREATE TABLE product (
                productID INT PRIMARY KEY,
                SellerUsername varchar(20) NOT NULL,
                ProductName VARCHAR(30),
                price Float,
                imageData VARCHAR(MAX),
                description VARCHAR(100),
                amount INT,
                Category VARCHAR(20) NOT NULL,
                AuctionExpireTime Varchar(30),
                HighestBidder varchar(20),
                BidPrice Float)"""
//                "FOREIGN KEY (sellerusername) REFERENCES users(sellerusername))"
             );
            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("DATABASE CREATED");

        }
        catch(SQLException E){
            System.out.println("could not connect");
            E.printStackTrace();
        }
    }
//INSERTING INTO TABLE
public static void ADD(Product prod){
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
    PreparedStatement statement = connect.prepareStatement("INSERT INTO product VALUES(?,?,?,?,?,?,?,?,?,?,?)");
    statement.setInt(1, prod.getProductId());
    statement.setString(2,prod.getProductOwner().getUsername());
    statement.setString(3,prod.getName());
    statement.setDouble(4,prod.getPrice());
    statement.setString(5,prod.getImage());
    statement.setString(6,prod.getDescription());
    statement.setInt(7,prod.getAmount());
    statement.setString(8, prod.getCategory());
    statement.setString(9, prod.getAuctionExpireTime());
    statement.setString(10, prod.getHighestBidder().getUsername());
    statement.setDouble(11,prod.getBidPrice());

    statement.executeUpdate();
    statement.close();
    connect.close();
}
catch(SQLException e){
    System.out.println("Connection failed");
    e.printStackTrace();
}

}

public static void DELETE(Product prod){
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
    PreparedStatement statement = connect.prepareStatement("DELETE FROM product WHERE productID = ?");
    statement.setInt(1, prod.getProductId());
    statement.executeUpdate();
    statement.close();
    connect.close();
}
catch(SQLException e){
    System.out.println("Connection failed");
    e.printStackTrace();
}
}


    public static ArrayList<Product> catsearch(String cat){
        Product prod = null;
        ArrayList<Product> list = new ArrayList<>();
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }


        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM product WHERE Category = ?");
            statement.setString(1, cat);
            ResultSet result = statement.executeQuery();

            while(result.next()){
                String SellerUsername = result.getString("SellerUsername");
                User Seller = OrderSQL.getuser(SellerUsername);

                String HighestBidderUsername = result.getString("HighestBidder");
                User HighestBidder = OrderSQL.getuser(HighestBidderUsername);

                prod = new Product(result.getInt("productID"),result.getString("ProductName"), result.getDouble("price"),result.getString("description"),result.getInt("amount"),result.getString("Category"),result.getString("imageData"),result.getDouble("BidPrice"), Seller, result.getString("AuctionExpireTime"), HighestBidder);
                list.add(prod);
            }
            statement.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<Product> namesearch(String name){
        Product prod = null;
        ArrayList<Product> list = new ArrayList<>();
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM product WHERE ProductName like ?");
            statement.setString(1, "%"+name+"%");
            ResultSet result = statement.executeQuery();

            while(result.next()){
                String SellerUsername = result.getString("SellerUsername");
                User Seller = OrderSQL.getuser(SellerUsername);

                String HighestBidderUsername = result.getString("HighestBidder");
                User HighestBidder = OrderSQL.getuser(HighestBidderUsername);

                prod = new Product(result.getInt("productID"),result.getString("ProductName"), result.getDouble("price"),result.getString("description"),result.getInt("amount"),result.getString("Category"),result.getString("imageData"),result.getDouble("BidPrice"), Seller, result.getString("AuctionExpireTime"), HighestBidder);
                list.add(prod);
            }
            statement.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Product> searchboth(String name, String Category){
        Product prod = null;
        ArrayList<Product> list = new ArrayList<>();
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM product WHERE ProductName like ? and Category = ?");
            statement.setString(1, "%"+name+"%");
            statement.setString(2, Category);
            ResultSet result = statement.executeQuery();

            while(result.next()){
                String SellerUsername = result.getString("SellerUsername");
                User Seller = OrderSQL.getuser(SellerUsername);

                String HighestBidderUsername = result.getString("HighestBidder");
                User HighestBidder = OrderSQL.getuser(HighestBidderUsername);

                prod = new Product(result.getInt("productID"),result.getString("ProductName"), result.getDouble("price"),result.getString("description"),result.getInt("amount"),result.getString("Category"),result.getString("imageData"),result.getDouble("BidPrice"), Seller, result.getString("AuctionExpireTime"), HighestBidder);
                list.add(prod);
            }
            statement.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
        return list;

    }

    public static ArrayList<Product> searchbyuser(User username){
        Product prod = null;
        ArrayList<Product> list = new ArrayList<>();
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }


        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM product WHERE SellerUsername = ?" );
            statement.setString(1, username.getUsername());

            ResultSet result = statement.executeQuery();

            while(result.next()){
                String SellerUsername = result.getString("SellerUsername");
                User Seller = OrderSQL.getuser(SellerUsername);

                String HighestBidderUsername = result.getString("HighestBidder");
                User HighestBidder = OrderSQL.getuser(HighestBidderUsername);

                prod = new Product(result.getInt("productID"),result.getString("ProductName"), result.getDouble("price"),result.getString("description"),result.getInt("amount"),result.getString("Category"),result.getString("imageData"),result.getDouble("BidPrice"), Seller, result.getString("AuctionExpireTime"), HighestBidder);
                list.add(prod);
            }
            statement.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
        return list;

    }

public static void upadte(Product prod,String what,int value){
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        return;
    }

    try{
        connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
        PreparedStatement statement = connect.prepareStatement("UPDATE product SET " + what + " = ? WHERE productID = ?" );
        statement.setInt(1, value);
        statement.setInt(2, prod.getProductId());
        statement.executeUpdate();
        statement.close();
        connect.close();

    } catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    } 
}

    public static void upadte(Product prod,String what,double value){
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();
            return;
        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("UPDATE product SET " + what + " = ? WHERE productID = ?" );
            statement.setDouble(1, value);
            statement.setInt(2, prod.getProductId());
            statement.executeUpdate();
            statement.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
    }

    public static void upadte(Product prod,String what,String value){
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();
            return;
        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("UPDATE product SET " + what + " = ? WHERE productID = ?" );
            statement.setString(1, value);
            statement.setInt(2, prod.getProductId());
            statement.executeUpdate();
            statement.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //createTable();
    }

}
