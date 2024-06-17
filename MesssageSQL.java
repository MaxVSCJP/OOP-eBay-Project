import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
public class MesssageSQL {
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
            "create table Message(senderusername varchar(20) unique not null, reciverusername varchar(20) unique not null, content varchar(200), statues varchar(20), imageData VARBINARY(MAX), time DateTime )"
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
public static void ADD(Massage mess){
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
    PreparedStatement statement = connect.prepareStatement("INSERT INTO message VALUES(?,?,?,?,?,?)");

    statement.setString(1,mess.getSender().getUsername());
    statement.setString(2,mess.getRecipient().getUsername());
    statement.setString(3,mess.getContent());
    statement.setString(4,"unread");
    statement.setBytes(5, image_to_bytes(mess.getfile(), "jnp"));
    statement.setTimestamp(6, mess.gettime());
   
    statement.executeUpdate();
}
catch(SQLException e){
    System.out.println("Connection failed");
    e.printStackTrace();
}

}

public static void DELETE(Massage mess){
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
    PreparedStatement statement = connect.prepareStatement("DELETE FROM message WHERE senderusername = ? and  recipientusername = ?");

    statement.setString(1, mess.getSender().getUsername());
    statement.setString(2, mess.getRecipient().getUsername());
    statement.executeUpdate();
}
catch(SQLException e){
    System.out.println("Connection failed");
    e.printStackTrace();
}
}

public static void retrive(){
     Massage text = null;
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        
    }

    try (Connection connection = DriverManager.getConnection(connectionpath)) {
    
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM message WHERE statues  = ?");
        statement.setString(1, "unread");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            byte[] imagex = result.getBytes("imageData");
            BufferedImage image = null;
            try {
                
                ByteArrayInputStream bis = new ByteArrayInputStream(imagex);
                image = ImageIO.read(bis);
            } catch (IOException e) {
                System.out.println("Error converting byte array to BufferedImage");
                e.printStackTrace();
            }
           


           String senderusername = result.getString("senderusername");
           String recipientusername = result.getString("recipientusername");

           User sender = getuser(senderusername);
           User recipient = getuser(recipientusername);

           text = new Massage(sender, recipient,result.getString("content"));
           text.setimage(image);
           chat.addmess(text);

        }
        
    } catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    }
    
    
}

public static void edit(Massage mess,String value){
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        return;
    }

    try (Connection connection = DriverManager.getConnection(connectionpath)) {
    
    PreparedStatement statment = connection.prepareStatement("UPDATE message SET content = ? WHERE senderusername = ? and reciverusername = ?" );
        statment.setString(1, value);
        statment.setString(2, mess.getSender().getUsername());
        statment.setString(3, mess.getRecipient().getUsername());

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

private static User getuser(String username){
    User retrievedCust = null;
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        
    }

    try (Connection connection = DriverManager.getConnection(connectionpath)){
        PreparedStatement statement = connection.prepareStatement("select * from user where username = ?");
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();
        if(result.next()){
            retrievedCust = new User(result.getString("username"),result.getString("name"),result.getString("email"),result.getDouble("balance"));
        }
             
    }
    catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    } 
    return retrievedCust;
}
}
