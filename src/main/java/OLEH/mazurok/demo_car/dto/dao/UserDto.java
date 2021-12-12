package alla.verkhohliadova.demo_car.dto.dao;

import alla.verkhohliadova.demo_car.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserDto {
    public static Connection getConnection(){
        Connection con=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/dealer","","");
        }catch(Exception e){System.out.println(e);}
        return con;
    }

    public static int save(User u){
        int status=0;
        try{
            Connection con=getConnection();
            PreparedStatement ps=con.prepareStatement(
                    "insert into register(username,password) values(?,?");
            ps.setString(1,u.getUsername());
            ps.setString(2,u.getPassword());

            status=ps.executeUpdate();
        }catch(Exception e){System.out.println(e);}
        return status;
    }
}
