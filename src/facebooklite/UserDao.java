package facebooklite;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao  {

    public static void insertUser(User user) {
        String statement =
                        "INSERT INTO users\n" +
                        "(first_name, last_name, age, email ,username, password, salt)\n" +
                        " VALUES\n" +
                        "( '"+user.getFirstName()+"', '"+user.getLastName()+"', '"+user.getAge()+"',  " +
                                "'"+user.getEmail()+"' ,'"+user.getUserName()+"' ,?, ?);" ;


        try{
            DBUtil.dbExecuteUpdate(statement, user.getHashedPassword(), user.getSalt());
        } catch (SQLException e) {
            System.out.println("Error in insertUser() method");
            e.printStackTrace();
        }
    }

    public static User getUser(String userName) throws SQLException {
        ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM users WHERE username =  '"+userName+"' ");
        if (rs.next()){
            return getUserFromResultSet(rs);
        }
        else{
            return null;
        }
    }


    public static User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = null;
        if (rs != null) {
            user = new User();
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setAge(rs.getInt("age"));
            user.setEmail(rs.getString("email"));
            user.setUserName(rs.getString("username"));
            user.setSalt(rs.getBytes("salt"));
            user.setHashedPassword(rs.getBytes("password"));
        }
        return user;
    }

    public static Boolean userExists(String userName) throws SQLException {
        ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM users WHERE username = '"+userName+"' ");
        if (rs.next()){
            return true;
        }
        else{
            return false;
        }
    }

    public static void updateUserSaltAndPass(String userName, byte[] salt, byte[] hashCode){
        DBUtil.dbUpdateSaltAndPass(userName, salt, hashCode);
    }

    public static void updateUser(String statement) {
    }


    public static void deleteUser(String userName) {

    }
}