
import java.sql.*;

public class PaymentSQL {
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
                "create table Payment("+
                "paymentID INT IDENTITY(1,1) PRIMARY KEY,"+
                "buyerusername varchar(20) unique NOT NULL,"+
                "sellerusername varchar(20) unique NOT NULL,"+
                "money Float,"+
                ")"
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
public static void ADD(double amount,User buyer,User seller){
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
    PreparedStatement statement = connect.prepareStatement("INSERT INTO Payment VALUES(?,?,?)");

    statement.setString(1,buyer.getUsername());
    statement.setString(2,seller.getUsername());
    statement.setDouble(3,amount);
   
    statement.executeUpdate();
    statement.close();
    connect.close();
}
catch(SQLException e){
    System.out.println("Connection failed");
    e.printStackTrace();
}

}

public static void DELETE(User buyer, User seller){
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
    PreparedStatement statement = connect.prepareStatement("DELETE FROM Payment WHERE buyerusername = ? and  sellerusername = ?");

    statement.setString(1, buyer.getUsername());
    statement.setString(2, seller.getUsername());
    statement.executeUpdate();
    statement.close();
    connect.close();
}
catch(SQLException e){
    System.out.println("Connection failed");
    e.printStackTrace();
}
}

public static void retrive(User buyer , User seller){
     
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        
    }

    try (Connection connection = DriverManager.getConnection(connectionpath)) {
    
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Payment where buyerusername = ? and sellerusername = ?");
        statement.setString(1, buyer.getUsername() );
        statement.setString(2, seller.getUsername());
        ResultSet result = statement.executeQuery();
       if(result.next()){
           
       }
           statement.close();
           connection.close();
        }
        
     catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    }
    
}
}
