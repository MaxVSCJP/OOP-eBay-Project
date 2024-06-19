import java.sql.*;

public class loginSQL {
   static String use1;
    static String pa1;
    static String connectionpath = "jdbc:sqlserver://LAPTOP-RRR8Q3CJ:1433;Database= test1 ;user=lovemehateyou;password=alazar11821996;trustServerCertificate=true;";
    //CREATEING A DATABASE
    public static void create(){
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();
            return;
        }

        try(Connection connect = DriverManager.getConnection(connectionpath)){
            
            PreparedStatement statement = connect.prepareStatement(
                "create table log("+
                "username varchar(20) primary key,"+
                "password varchar(20)  NOT NULL,"+
                "Status varchar(30)  NOT NULL,"+
                "FOREIGN KEY (username) REFERENCES users(username),"
            );

            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("DATABASE CREATED");

        }
        catch(SQLException E){
            System.out.println("could not connect");
            E.printStackTrace();
            return;
        }


    }
    
    public static boolean signup(String user, String pass){
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();
            
        }

    try(Connection connect = DriverManager.getConnection(connectionpath);){
        PreparedStatement stat = connect.prepareStatement("select * from log");
        ResultSet result = stat.executeQuery();
        while(result.next()){
            if(result.getString("username").equals(user)){
                System.out.println("username already exists");
                return false;
                }
            }                
        PreparedStatement statement = connect.prepareStatement("INSERT INTO log values(?,?,?);");
        statement.setString(1, user);
        statement.setString(2, pass);
        statement.setString(3, "Active");
        

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
    return true;

}
//SEARCHING FOR A VALUE
public static void login(String user,String pass){
    try{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }
    catch(ClassNotFoundException E){
        System.out.println("JDBC driver not found");
        E.printStackTrace();
        
    }

    try(Connection connect = DriverManager.getConnection(connectionpath)){
        PreparedStatement statement = connect.prepareStatement("SELECT * FROM log");
        ResultSet result =  statement.executeQuery();
        while ((result.next())) {
            use1 = result.getString("username");
            pa1 = result.getString("password");
            if (use1.equals(user) && pa1.equals(pass)) {
                User uu = finduser(user);
                LoginManager.setActiveUser(uu);
             }    
        }
        statement.close();
        connect.close();
        
    }
    catch(SQLException E){
        System.out.println("JDBC driver not found");
        E.printStackTrace();
    
    }
   
}

private static User finduser(String username){
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
    }

    try(Connection connect = DriverManager.getConnection(connectionpath)){
        PreparedStatement statement = connect.prepareStatement("select * from users");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            if(result.getString("username").equals(username)){
                User u = new User(result.getString("username"),result.getString("name"),result.getString("Email"),result.getDouble("Balance"));
                return u;
            }
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
}
