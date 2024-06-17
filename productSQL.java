import java.sql.*;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.*;

import java.io.*;

public class productSQL {
    static String connectionpath = "jdbc:sqlserver://LAPTOP-RRR8Q3CJ:1433;Database=CTC_DEMO ;user=lovemehateyou;password=alazar11821996;trustServerCertificate=true;";
    //CREATEING A tabel
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

        try(Connection connect = DriverManager.getConnection(connectionpath)){
            
            PreparedStatement statment = connect.prepareStatement(
            "create table product(productID int unique not null primary key , prodname varchar(20) not null , price double not null,imageData VARBINARY(MAX),description varchar(200),amount int not null , catagory varchar(20) not null)"
             );
            statment.executeUpdate();
            System.out.println("DATABASE CREATED");

        }
        catch(SQLException E){
            System.out.println("could not connect");
            E.printStackTrace();
        }
    }
//INSERTING INTO TABLE
public static void ADD(Product prod, String format){
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } 
catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        return;
    }  
try(Connection connect = DriverManager.getConnection(connectionpath)){
    PreparedStatement statement = connect.prepareStatement("INSERT INTO product VALUES(?,?,?,?,?,?,?)");

    statement.setInt(1,prod.getProductId());
    statement.setString(2,prod.getName());
    statement.setDouble(3,prod.getPrice());
    statement.setBytes(4,image_to_bytes(prod.getImage(),format));
    statement.setString(5,prod.getDescription());
    statement.setInt(6,prod.getAmount());
    statement.setString(7, prod.getCatagory());

    statement.executeUpdate();
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
try(Connection connect = DriverManager.getConnection(connectionpath)){
    PreparedStatement statement = connect.prepareStatement("DELETE FROM product WHERE productID = ?");
    statement.setInt(1, prod.getProductId());
    statement.executeUpdate();
}
catch(SQLException e){
    System.out.println("Connection failed");
    e.printStackTrace();
}
}

public static void catsearch(String cat){
    Product prod = null;
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        
    }

    try (Connection connection = DriverManager.getConnection(connectionpath)) {
    
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE catagory  = ?");
        statement.setString(1, cat);
        ResultSet result = statement.executeQuery();
        ProdcutListings list = new ProdcutListings();
        while(result.next()){
           byte[] imagex = result.getBytes("imageData");
           String base64String =Base64.getEncoder().encodeToString(imagex);

            prod = new Product(result.getString("prodName"), result.getDouble("price"),result.getString("description"),result.getInt("amount"),result.getString(cat),base64String);
            list.addToMarket(prod);
        }
        
    } catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    }
    
    
}

public static void namesearch(String name){
    Product prod = null;
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        
    }

    try (Connection connection = DriverManager.getConnection(connectionpath)) {
    
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE name like %?%");
        statement.setString(1, name);
        ResultSet result = statement.executeQuery();
        ProdcutListings list = new ProdcutListings();
        while(result.next()){
           byte[] imagex = result.getBytes("imageData");
           String base64String =Base64.getEncoder().encodeToString(imagex);

            prod = new Product(result.getString("prodName"), result.getDouble("price"),result.getString("description"),result.getInt("amount"),result.getString("catagory"),base64String);
            list.addToMarket(prod);
        }
        
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

    try (Connection connection = DriverManager.getConnection(connectionpath)) {
    
    PreparedStatement statment = connection.prepareStatement("UPDATE product SET " + what + " = ? WHERE prouctID = ?" );
        statment.setString(1, value);
        statment.setInt(2, prod.getProductId());

    } catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    } 
}

private static byte[] image_to_bytes(BufferedImage image,String format){
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
        ImageIO.write(image, format, bos); // or "png" depending on the image format
    } catch (IOException e) {
        System.out.println("Error writing image to byte array");
        e.printStackTrace();
    }
    byte[] imageData = bos.toByteArray();
    return imageData;
}
}
