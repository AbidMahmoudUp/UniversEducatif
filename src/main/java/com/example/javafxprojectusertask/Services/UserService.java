package com.example.javafxprojectusertask.Services;

import com.example.javafxprojectusertask.Utilities.DataBaseConnection;
import com.example.javafxprojectusertask.Entities.UserClass;
import com.example.javafxprojectusertask.Interfaces.UserInterface;

import java.sql.*;
import java.util.ArrayList;

public class UserService implements UserInterface<UserClass> {

    private Connection conection ;
    public UserService(){

        conection= DataBaseConnection.getInstance().getCnx() ;

    }


    @Override
    public void addUser(UserClass user) throws SQLException {

        try {

            String SQLQurey = "INSERT INTO USER (UserName,Email,Password,Status)VALUES(?,?,?,?)";
            PreparedStatement statement = conection.prepareStatement(SQLQurey);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getStatus());
            statement.executeUpdate();


        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }



    }

    @Override
    public void updateUser(UserClass user) throws SQLException {
        try {
            String SQLQurey = "UPDATE  USER SET (UserName = ? ,Email = ?,Password= ?) WHERE id="+user.getIdUser();
            PreparedStatement statement = conection.prepareStatement(SQLQurey);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }
    @Override
    public void updateEmailAdmin(UserClass user) throws SQLException {
        try {
            String SQLQurey = "UPDATE  USER SET (Email = ?) WHERE id="+user.getIdUser();
            PreparedStatement statement = conection.prepareStatement(SQLQurey);
            statement.setString(1, user.getEmail());

            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void updateStateUser(UserClass user) throws SQLException {
        try {
            String SQLQuery = "UPDATE USER SET State = ? WHERE idUser = ?";
            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            statement.setString(1, user.getState());
            statement.setInt(2, user.getIdUser());

            statement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public  boolean doesUserExist(String Email) {
        String SQLquery = "SELECT COUNT(*) FROM USER WHERE EMAIL = ?";
        try {
             PreparedStatement statement = conection.prepareStatement(SQLquery) ;
            statement.setString(1, Email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void deleteUser(UserClass user) throws SQLException {
        try {
            String SQLQurey = "DELETE FROM USER WHERE idUser="+user.getIdUser();
            PreparedStatement statement = conection.prepareStatement(SQLQurey);
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public UserClass getUserBynameAndEmail(String username, String email) throws SQLException {
        UserClass user1 = null;  // Initialize as null

        try {
            String SQLQuery = "SELECT * FROM USER WHERE userName = ? AND email = ?";
            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            statement.setString(1, username);
            statement.setString(2, email);
            ResultSet result = statement.executeQuery();

            // Check if there is at least one result
            if (result.next()) {
                user1 = new UserClass();
                user1.setUserName(result.getString("UserName"));
                user1.setEmail(result.getString("Email"));
                user1.setPassword(result.getString("Password"));
                user1.setIdUser(result.getInt("idUser"));
                user1.setStatus(result.getString("Status"));
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return user1;
    }
    @Override
    public UserClass getUserByEmail(String email) throws SQLException {
        UserClass user1 = null;  // Initialize as null

        try {
            String SQLQuery = "SELECT * FROM USER WHERE  email = ?";
            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            // Check if there is at least one result
            if (result.next()) {
                user1 = new UserClass();
                user1.setUserName(result.getString("UserName"));
                user1.setEmail(result.getString("Email"));
                user1.setPassword(result.getString("Password"));
                user1.setIdUser(result.getInt("idUser"));
                user1.setStatus(result.getString("Status"));
                user1.setState(result.getString("State"));
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return user1;
    }
    @Override
    public UserClass getUserById(int idUser) throws SQLException {
        UserClass user1 = null;  // Initialize as null

        try {
            String SQLQuery = "SELECT * FROM USER WHERE idUser ="+idUser;
            PreparedStatement statement = conection.prepareStatement(SQLQuery);


            ResultSet result = statement.executeQuery();

            // Check if there is at least one result
            if (result.next()) {
                user1 = new UserClass();
                user1.setUserName(result.getString("UserName"));
                user1.setEmail(result.getString("Email"));
                user1.setPassword(result.getString("Password"));
                user1.setIdUser(result.getInt("idUser"));
                user1.setStatus(result.getString("Status"));
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return user1;
    }


    @Override
    public ArrayList<UserClass> getAllUsers() throws SQLException {
        ArrayList<UserClass> ListUsers =new ArrayList<>();
        try {
            String SQLQurey = "SELECT * FROM USER";
            Statement statement = conection.createStatement();
            ResultSet Result =statement.executeQuery(SQLQurey);
            while(Result.next())
            {
                UserClass user = new UserClass();
                user.setIdUser(Result.getInt("IdUser"));
                user.setUserName(Result.getString("UserName"));
                user.setEmail(Result.getString("Email"));
                user.setPassword(Result.getString("Password"));
                user.setStatus(Result.getString("Status"));
                user.setState(Result.getString("state"));
                ListUsers.add(user);
            }

        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        System.out.println("userlistSize:"+ListUsers.size());
        return ListUsers;
    }

    @Override
    public ArrayList<UserClass> getAllUsersByName(String orderBy, String sortOrder) throws SQLException {
        ArrayList<UserClass> ListUsers = new ArrayList<>();
        try {

            String SQLQuery = "SELECT * FROM USER ORDER BY " + orderBy + " " + sortOrder;

            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            ResultSet Result = statement.executeQuery();

            while (Result.next()) {
                UserClass user = new UserClass();
                user.setIdUser(Result.getInt("IdUser"));
                user.setUserName(Result.getString("UserName"));
                user.setEmail(Result.getString("Email"));
                user.setPassword(Result.getString("Password"));
                user.setStatus(Result.getString("Status"));
                user.setState(Result.getString("state"));
                ListUsers.add(user);
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println("userlistSize:" + ListUsers.size());
        return ListUsers;
    }
    public ArrayList<UserClass> getAllUsersByNameProfile( String sortOrder) throws SQLException {
        ArrayList<UserClass> ListUsers = new ArrayList<>();
        try {
            String SQLQuery = "SELECT u.* FROM USER u " +
                    "INNER JOIN PROFILE p ON u.IdUser = p.idUser " +
                    "ORDER BY p.lastName " + sortOrder;

            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            ResultSet Result = statement.executeQuery();

            while (Result.next()) {
                UserClass user = new UserClass();
                user.setIdUser(Result.getInt("IdUser"));
                user.setUserName(Result.getString("UserName"));
                user.setEmail(Result.getString("Email"));
                user.setPassword(Result.getString("Password"));
                user.setStatus(Result.getString("Status"));
                user.setState(Result.getString("state"));
                ListUsers.add(user);
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println("userlistSize:" + ListUsers.size());
        return ListUsers;
    }



    @Override
    public UserClass verfieLogin(String email , String password ) throws SQLException{
        UserClass user1 = null;  // Initialize to null if the user doesn't exist
        try {
            String SQLQuery = "SELECT * FROM USER WHERE Email = ? AND Password = ?";
            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            // Check if the ResultSet has any rows
            if (result.next()) {
                user1 = new UserClass();
                user1.setIdUser(result.getInt("IdUser"));
                user1.setUserName(result.getString("UserName"));
                user1.setEmail(result.getString("Email"));
                user1.setPassword(result.getString("Password"));
                user1.setStatus(result.getString("Status"));
            } else {
                // Handle the case where the user does not exist (for example, return null)
                System.out.println("User not found for the given credentials.");
            }
        } catch (SQLException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return user1;
}
    @Override
    public void updatePassword(UserClass User , String password) throws SQLException {
        try {
            //String SQLQuery = "UPDATE USER SET Password = "+ User.getPassword()+" WHERE IdUser = "+User.getIdUser()+"";
            String SQLQuery = "UPDATE USER SET Password = ? WHERE IdUser = ?";

            System.out.println(SQLQuery);
            PreparedStatement statement = conection.prepareStatement(SQLQuery);
            statement.setString(1,password);
            statement.setInt(2,User.getIdUser());
            statement.executeUpdate();



        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }
    @Override
    public ArrayList<UserClass> getAllAdmins() throws SQLException {
        ArrayList<UserClass> ListUsers =new ArrayList<>();
        try {
            String SQLQurey = "SELECT * FROM USER where Status = 'Admin'";
            Statement statement = conection.createStatement();
            ResultSet Result =statement.executeQuery(SQLQurey);
            while(Result.next())
            {
                UserClass user = new UserClass();
                user.setIdUser(Result.getInt("IdUser"));
                user.setUserName(Result.getString("UserName"));
                user.setEmail(Result.getString("Email"));
                user.setPassword(Result.getString("Password"));
                user.setStatus(Result.getString("Status"));
                ListUsers.add(user);
            }

        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        System.out.println("userlistSize:"+ListUsers.size());
        return ListUsers;
    }

    @Override
    public ArrayList<UserClass> getAllCoaches() throws SQLException {
        ArrayList<UserClass> ListUsers =new ArrayList<>();
        try {
            String SQLQurey = "SELECT * FROM USER Status = 'Coach'";
            Statement statement = conection.createStatement();
            ResultSet Result =statement.executeQuery(SQLQurey);
            while(Result.next())
            {
                UserClass user = new UserClass();
                user.setIdUser(Result.getInt("IdUser"));
                user.setUserName(Result.getString("UserName"));
                user.setEmail(Result.getString("Email"));
                user.setPassword(Result.getString("Password"));
                user.setStatus(Result.getString("Status"));
                ListUsers.add(user);
            }

        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        System.out.println("userlistSize:"+ListUsers.size());
        return ListUsers;
    }

    @Override
    public ArrayList<UserClass> getOnlyUsers() throws SQLException {
        ArrayList<UserClass> ListUsers =new ArrayList<>();
        try {
            String SQLQurey = "SELECT * FROM USER Status = 'User'";
            Statement statement = conection.createStatement();
            ResultSet Result =statement.executeQuery(SQLQurey);
            while(Result.next())
            {
                UserClass user = new UserClass();
                user.setIdUser(Result.getInt("IdUser"));
                user.setUserName(Result.getString("UserName"));
                user.setEmail(Result.getString("Email"));
                user.setPassword(Result.getString("Password"));
                user.setStatus(Result.getString("Status"));
                ListUsers.add(user);
            }

        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        System.out.println("userlistSize:"+ListUsers.size());
        return ListUsers;
    }
}
