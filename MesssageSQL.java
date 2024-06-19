import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
public class MesssageSQL {
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
                "create table Message("+
                "messageID INT IDENTITY(1,1) PRIMARY KEY,"+
                "senderusername varchar(20) unique NOT NULL,"+
                "reciverusername varchar(20) unique NOT NULL,"+
                "content varchar(200),"+
                "status varchar(20), "+
                "imageData VARBINARY(MAX),"+
                "time DateTime,"+
                "FOREIGN KEY (senderusername) REFERENCES users(username),"+
                "FOREIGN KEY (reciverusername) REFERENCES users(username))"
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
    PreparedStatement statement = connect.prepareStatement("INSERT INTO Message (senderusername,reciverusername,content,status,imageData,time) VALUES(?,?,?,?,?,?)");

    statement.setString(1,mess.getSender().getUsername());
    statement.setString(2,mess.getRecipient().getUsername());
    statement.setString(3,mess.getContent());
    statement.setString(4,"unread");
    statement.setBytes(5, image_to_bytes(mess.getfile(), "jnp"));
    statement.setString(6, mess.gettime());
   
    statement.executeUpdate();
    statement.close();
    connect.close();
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
    PreparedStatement statement = connect.prepareStatement("DELETE FROM Message WHERE senderusername = ? and  recipientusername = ?");

    statement.setString(1, mess.getSender().getUsername());
    statement.setString(2, mess.getRecipient().getUsername());
    statement.executeUpdate();
    statement.close();
    connect.close();
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
    
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Message");
        statement.setString(1, "unread");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            if (result.getBytes("imagedata") != null){
                byte[] imagex = result.getBytes("imageData");
                BufferedImage image = null;
                try {
                    
                    ByteArrayInputStream bis = new ByteArrayInputStream(imagex);
                    image = ImageIO.read(bis);
                    text.setimage(image);
                } 
                catch (IOException e) {
                    System.out.println("Error converting byte array to BufferedImage");
                    e.printStackTrace();
                }
            }
           
           
           String senderusername = result.getString("senderusername");
           String recipientusername = result.getString("recipientusername");

           User sender = getuser(senderusername);
           User recipient = getuser(recipientusername);

           text = new Massage(sender, recipient,result.getString("content"));
           chat.addmess(text);
           statement.close();
           connection.close();
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
    
    PreparedStatement statement = connection.prepareStatement("UPDATE Message SET content = ? WHERE senderusername = ? and reciverusername = ?" );
        statement.setString(1, value);
        statement.setString(2, mess.getSender().getUsername());
        statement.setString(3, mess.getRecipient().getUsername());
        statement.executeUpdate();
        statement.close();
        connection.close();

    } catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    } 
}

public static int Notification(){
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();  
    }

    try (Connection connection = DriverManager.getConnection(connectionpath)){
        PreparedStatement statement2 = connection.prepareStatement("Select count(*) as  message_count from Message where status = ? ");
           statement2.setString(1, "unread");
           ResultSet result2 = statement2.executeQuery();
           int notif = result2.getInt("message_count");
           statement2.close();
            connection.close();
           return notif;
    }
    catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    }
    return 0;
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
        PreparedStatement statement = connection.prepareStatement("select * from users where username = ?");
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();
        if(result.next()){
            retrievedCust = new User(result.getString("username"),result.getString("name"),result.getString("email"),result.getDouble("balance"));
        }
        statement.close();
        connection.close();
             
    }
    catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    } 
    return retrievedCust;
}
}
