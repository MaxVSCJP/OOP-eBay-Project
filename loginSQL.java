import java.sql.*;

public class loginSQL {
   static String use1;
    static String pa1;
    static Connection connect;
    //CREATING A Table
    public static void create(){
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
            PreparedStatement statement = connect.prepareStatement("""
                create table Logins(
                username varchar(20) primary key,
                password varchar(20)  NOT NULL,
                FOREIGN KEY (username) REFERENCES users(username))"""
            );

            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("Table CREATED");

        }
        catch(SQLException E){
            System.out.println("could not connect");
            E.printStackTrace();
            return;
        }


    }
    
    public static void signup(SignUp logins){
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();
            
        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("INSERT INTO Logins values(?,?);");
            statement.setString(1, logins.getUsername());
            statement.setString(2, logins.getPassword());

            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("RECORDES ADDED");

            connect.close();

        }
        catch(SQLException E){
            System.out.println("COUDN'T ADD THE RECORED");
            E.printStackTrace();
        }

}
//SEARCHING FOR A VALUE
public static boolean login(String user,String pass){
    try{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }
    catch(ClassNotFoundException E){
        System.out.println("JDBC driver not found");
        E.printStackTrace();
        
    }

    try{
        connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
        PreparedStatement statement = connect.prepareStatement("SELECT * FROM Logins");
        ResultSet result =  statement.executeQuery();
        while ((result.next())) {
            use1 = result.getString("username");
            pa1 = result.getString("password");
            if (use1.equals(user) && pa1.equals(pass)) {
                User uu = finduser(user);
                LoginManager.setActiveUser(uu);
                return true;
             }    
        }
        statement.close();
        connect.close();
        
    }
    catch(SQLException E){
        System.out.println("JDBC driver not found");
        E.printStackTrace();
    
    }
    return false;
   
}

private static User finduser(String username){
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
    }

    try{
        connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
        PreparedStatement statement = connect.prepareStatement("select * from users where username = ?");
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();
        if(result.next()){
            User u = new User(result.getString("username"),result.getString("Fullname"),result.getString("Email"),result.getDouble("Balance"));
            return u;
        }
        statement.close();
        connect.close(); 
    }
    catch(SQLException e){
        System.out.println("COULDN'T CONNECT");
        e.printStackTrace();
    }
    return null;
}

    public static void main(String[] args) {

    }
}
