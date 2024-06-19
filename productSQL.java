import java.sql.*;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.*;

import java.io.*;

public class productSQL {
    static String connectionpath = "jdbc:sqlserver://LAPTOP-RRR8Q3CJ:1433;Database=test1 ;user=lovemehateyou;password=alazar11821996;trustServerCertificate=true;";
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
            
            PreparedStatement statement = connect.prepareStatement(
                "CREATE TABLE product (" +
                "productID INT PRIMARY KEY, " +
                "sellerusername varchar(20) NOT NULL,"+
                "prodname VARCHAR(30), " +
                "price Float," +
                "imageData VARBINARY(MAX), " +
                "description VARCHAR(100), " +
                "amount INT, " +
                "catagory VARCHAR(20) NOT NULL)"
                /* "FOREIGN KEY (sellerusername) REFERENCES users(sellerusername))" */
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
    PreparedStatement statement = connect.prepareStatement("INSERT INTO product VALUES(?,?,?,?,?,?,?,?)");
    statement.setInt(1, prod.getProductId());
    statement.setString(2,prod.getUser().getUsername());
    statement.setString(3,prod.getName());
    statement.setDouble(4,prod.getPrice());
    if(format != null){
        statement.setBytes(5,image_to_bytes(prod.getImage(),format));
    }
    statement.setString(6,prod.getDescription());
    statement.setInt(7,prod.getAmount());
    statement.setString(8, prod.getCatagory());

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
try(Connection connect = DriverManager.getConnection(connectionpath)){
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

            prod = new Product(result.getString("prodName"), result.getDouble("price"),result.getString("description"),result.getInt("amount"),result.getString("catagory"),base64String);
            list.addToMarket(prod);
        }
        statement.close();
        connection.close();
        
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
        statement.close();
        connection.close();
        
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
    
    PreparedStatement statement = connection.prepareStatement("UPDATE product SET " + what + " = ? WHERE prouctID = ?" );
        statement.setString(1, value);
        statement.setInt(2, prod.getProductId());
        statement.executeUpdate();
        statement.close();
        connection.close();

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
