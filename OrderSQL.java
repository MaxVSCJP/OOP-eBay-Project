import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;


public class OrderSQL {
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
                "create table Orders("+
                "orderID INT  PRIMARY KEY,"+
                "prodID int unique NOT NULL,"+
                "buyersusername varchar(20) unique NOT NULL,"+      
                "Quantity int,"+
                "orderstatus varchar(20), "+
                "time varchar(30),"+
                "ETA varchar(30)"+
                "FOREIGN KEY (buyersusername) REFERENCES users(username),"+
                "FOREIGN KEY (prodID) REFERENCES product(productID))"
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
try(Connection connect = DriverManager.getConnection(connectionpath)){
    PreparedStatement statement = connect.prepareStatement("INSERT INTO Orders VALUES(?,?,?,?,?,?,?)");

    statement.setInt(1,ord.getOrdId());
    statement.setInt(2,ord.getProduct().getProductId());
    statement.setString(3,ord.getBuyer().getUsername());
    statement.setInt(4,ord.getQuantity());
    statement.setString(5, ord.getOrderStatus());
    statement.setString(6,ord.getETA());
    statement.setString(7,ord.gettime());
   
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
try(Connection connect = DriverManager.getConnection(connectionpath)){
    PreparedStatement statement = connect.prepareStatement("DELETE FROM Orders WHERE orderID = ?");

    statement.setInt(1, ord.getOrdId());
    
    statement.executeUpdate();
    statement.close();
    connect.close();
}
catch(SQLException e){
    System.out.println("Connection failed");
    e.printStackTrace();
}
}

public static Orders retrive(){
     Orders ordered = null;
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        
    }

    try (Connection connection = DriverManager.getConnection(connectionpath)) {
    
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Orders");
        ResultSet result = statement.executeQuery();
        while(result.next()){
           
           String buyersusername = result.getString("buyersusername");
           int productid = result.getInt("prodID");

           User buyer = getuser(buyersusername);
           Product pro = getproduct(productid);
           

           ordered = new Orders(pro,result.getInt("Quantity"),buyer,result.getString("orderstatus"));
           statement.close();
           connection.close();
        }
        
    } catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    }
    
    return ordered;
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

private static Product getproduct(int prodID){
    Product retrievedCust = null;
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        
    }

    try (Connection connection = DriverManager.getConnection(connectionpath)){
        PreparedStatement statement = connection.prepareStatement("select * from product where productID = ?");
        statement.setInt(1, prodID);
        ResultSet result = statement.executeQuery();

        if(result.next()){

        byte[] imagex = result.getBytes("imageData");
           String base64String =Base64.getEncoder().encodeToString(imagex);

            retrievedCust = new Product(result.getString("prodName"), result.getDouble("price"),result.getString("description"),result.getInt("amount"),result.getString("catagory"),base64String);
        statement.close();
        connection.close();
        }
             
    }
    catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    } 
    return retrievedCust;
}


}
