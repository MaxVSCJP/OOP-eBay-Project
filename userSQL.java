import java.sql.*;

public class userSQL {
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
                "create table users("+
                "username varchar(20) primary key,"+
                "Fullname varchar(30),"+
                "Email varchar(50),"+
                "Balance Float)" 
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
    PreparedStatement statement = connect.prepareStatement("INSERT INTO users VALUES(?,?,?,?)");

    statement.setString(1,use.getUsername());
    statement.setString(2,use.getName());
    statement.setString(3,use.getEmail());
    statement.setDouble(4,use.getBalance());
    statement.executeUpdate();
    statement.close();
    connect.close();
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
    PreparedStatement statement = connect.prepareStatement("DELETE FROM users WHERE username = ?");
    statement.setString(1, use.getUsername());
    statement.executeUpdate();
    statement.executeUpdate();
    statement.close();
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
    
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
        statement.setString(1, cust.getUsername());
        ResultSet result = statement.executeQuery();
    if (result.next()) {
        retrievedCust = new User(result.getString("username"),result.getString("name"),result.getString("email"),result.getDouble("balance"));
        statement.executeUpdate();
        statement.close();
        
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
    
    PreparedStatement statement = connection.prepareStatement("UPDATE users SET " + what + " = ? WHERE username = ?" );
        statement.setString(1, value);
        statement.setString(2, use.getUsername());
        statement.executeUpdate();
        statement.executeUpdate();
        statement.close();

    } catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    } 
}
}
