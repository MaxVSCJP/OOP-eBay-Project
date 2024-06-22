
import java.sql.*;
import java.util.ArrayList;
import java.awt.image.*;
import java.util.HashSet;
import javax.imageio.ImageIO;
public class MesssageSQL {

    //CREATEING A tabel
    static  Connection connect;
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
                    "create table TextMessage("+
                            "messageID INT IDENTITY(1,1) PRIMARY KEY,"+
                            "senderusername varchar(20) NOT NULL,"+
                            "reciverusername varchar(20) NOT NULL,"+
                            "content varchar(200),"+
                            "status varchar(20), "+
                            "SentTime Varchar(30),"+
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
        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("INSERT INTO TextMessage (senderusername,reciverusername,content,SentTime) VALUES(?,?,?,?)");

            statement.setString(1,mess.getSender().getUsername());
            statement.setString(2,mess.getRecipient().getUsername());
            statement.setString(3,mess.getContent());
            statement.setString(4, mess.gettime());

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
        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("DELETE FROM TextMessage WHERE senderusername = ? and  reciverusername = ?");

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

    public static HashSet<String> retrive(){

        HashSet <String> people = new HashSet<>();
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM TextMessage where senderusername = ? or  reciverusername = ?");
            statement.setString(1, LoginManager.getActiveUser().getUsername());
            statement.setString(2, LoginManager.getActiveUser().getUsername());

            ResultSet result = statement.executeQuery();
            while(result.next()){

                String senderusername = result.getString("senderusername");
                String recipientusername = result.getString("reciverusername");

                if(LoginManager.getActiveUser().getUsername().equals(senderusername)){
                    people.add(recipientusername);
                }
                else{
                    people.add(senderusername);
                }


            }
            statement.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }

        return people;
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

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("UPDATE TextMessage SET content = ? WHERE senderusername = ? and reciverusername = ?" );
            statement.setString(1, value);
            statement.setString(2, mess.getSender().getUsername());
            statement.setString(3, mess.getRecipient().getUsername());
            statement.executeUpdate();
            statement.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
    }


    public static ArrayList<Massage> retrivebyuser(String reuser){
        Massage mess = null;
        ArrayList <Massage> people = new ArrayList<>();
        try {
            // Register the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver not found");
            e.printStackTrace();

        }

        try{
            connect = DriverManager.getConnection("jdbc:sqlserver://Maassas\\SQLEXPRESS;databaseName=Project;encrypt=true;trustServerCertificate=true", "OOP-Project", "12345678");
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM TextMessage where senderusername = ? and  reciverusername = ? or senderusername = ? and  reciverusername = ?");
            statement.setString(1, LoginManager.getActiveUser().getUsername());
            statement.setString(2,reuser );
            statement.setString(3, reuser);
            statement.setString(4, LoginManager.getActiveUser().getUsername());

            ResultSet result = statement.executeQuery();
            while(result.next()){

                User user1 = getuser(result.getString("senderusername"));
                User user2 = getuser(result.getString("reciverusername"));

                if(LoginManager.getActiveUser().getUsername().equals(result.getString("senderusername")) ){

                    mess = new Massage(user1, user2, result.getString("content"));
                    people.add(mess);
                }
                else{
                    mess = new Massage(user2, user1, result.getString("content"));
                    people.add(mess);
                }


            }
            statement.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }

        return people;
    }


    public static User getuser(String username){
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
            PreparedStatement statement = connect.prepareStatement("select * from users where username = ?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                retrievedCust = new User(result.getString("username"),result.getString("Fullname"),result.getString("Email"),result.getDouble("Balance"));
            }
            statement.close();
            connect.close();

        }
        catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
        return retrievedCust;
    }

    public static void main(String[] args) {
        createTable();
    }
}
