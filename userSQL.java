import java.sql.*;

public class userSQL {
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
            "create table user(username varchar(20) unique primary key , name varchar(20) not null , Email varchar(20) not null, Balance double not null)"
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
public static void ADD(User use){
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
    PreparedStatement statement = connect.prepareStatement("INSERT INTO user VALUES(?,?,?,?)");

    statement.setString(1,use.getUsername());
    statement.setString(2,use.getName());
    statement.setString(3,use.getEmail());
    statement.setDouble(4,use.getBalance());
    statement.executeUpdate();
}
catch(SQLException e){
    System.out.println("Connection failed");
    e.printStackTrace();
}

}

public static void DELETE(User use){
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
    PreparedStatement statement = connect.prepareStatement("DELETE FROM user WHERE username = ?");
    statement.setString(1, use.getUsername());
    statement.executeUpdate();
}
catch(SQLException e){
    System.out.println("Connection failed");
    e.printStackTrace();
}
}

public static User search(User cust){
    User retrievedCust = null;
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        
    }

    try (Connection connection = DriverManager.getConnection(connectionpath)) {
    
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
        statement.setString(1, cust.getUsername());
        ResultSet result = statement.executeQuery();
    if (result.next()) {
        retrievedCust = new User(result.getString("username"),result.getString("name"),result.getString("email"),result.getDouble("balance"));
        
    }
    } catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    }
    return retrievedCust; 
}

public static void upadte(User use,String what,String value){
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        return;
    }

    try (Connection connection = DriverManager.getConnection(connectionpath)) {
    
    PreparedStatement statment = connection.prepareStatement("UPDATE user SET " + what + " = ? WHERE username = ?" );
        statment.setString(1, value);
        statment.setString(2, use.getUsername());

    } catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    } 
}
}
