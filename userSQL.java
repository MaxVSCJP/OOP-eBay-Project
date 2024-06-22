import java.sql.*;

public class userSQL {

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
try{
    connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
    PreparedStatement statement = connect.prepareStatement("""
            Insert Into users(username, Fullname, Email, Balance)
            VALUES(?,?,?,?);
            """);

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
try{
    connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
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

    try{
        connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
        PreparedStatement statement = connect.prepareStatement("SELECT * FROM users WHERE username = ?");
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

public static void upadte(User use,String what,double value){
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
        PreparedStatement statement = connect.prepareStatement("UPDATE users SET " + what + " = ? WHERE username = ?" );
        statement.setDouble(1, value);
        statement.setString(2, use.getUsername());
        statement.executeUpdate();
        statement.executeUpdate();
        statement.close();

    } catch (SQLException e) {
        System.out.println("Database not connected");
        e.printStackTrace();
    } 
}

    public static boolean UsernameAvailability(String username){
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();
        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("SELECT username FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            return !(result.next());
        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
        return true;
    }


    public static void main(String[] args) {

    }
}
