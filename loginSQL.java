import java.sql.*;

public class loginSQL {
   static String use1;
    static String pa1;
    static String connectionpath = "jdbc:sqlserver://LAPTOP-RRR8Q3CJ:1433;Database=CTC_DEMO ;user=lovemehateyou;password=alazar11821996;trustServerCertificate=true;";
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
            
            PreparedStatement statment = connect.prepareStatement("create table log(username varchar(20) unique,password varchar(20) not null,Status varchar(30) not null)");
            statment.executeUpdate();
            System.out.println("DATABASE CREATED");

        }
        catch(SQLException E){
            System.out.println("could not connect");
            E.printStackTrace();
            return;
        }


    }
    //ADDING MOVIES TO THE DATABASE
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
       
        connect.close();
        
    }
    catch(SQLException E){
        System.out.println("JDBC driver not found");
        E.printStackTrace();
    
    }
   
}

//DROP THE DATABASE TABLE
public static void Drop(){
    try {
        // Register the JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (ClassNotFoundException e) {
        System.out.println("JDBC driver not found");
        e.printStackTrace();
        return;
    }

    try(Connection connect = DriverManager.getConnection(connectionpath)){
        
        PreparedStatement statment = connect.prepareStatement("drop table Movies");
        statment.executeUpdate();
        System.out.println("DATABASE dropped");

    }
    catch(SQLException E){
        System.out.println("couldn't deop");
        E.printStackTrace();
        return;
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
        PreparedStatement statment = connect.prepareStatement("select * from user");
        ResultSet result = statment.executeQuery();
        while(result.next()){
            if(result.getString("username").equals(username)){
                User u = new User(result.getString("username"),result.getString("name"),result.getString("Email"),result.getDouble("Balance"));
                return u;
            }
        } 
    }
    catch(SQLException e){
        System.out.println("COULDN'T CONNECT");
        e.printStackTrace();
    }
    return null;
} 
}
