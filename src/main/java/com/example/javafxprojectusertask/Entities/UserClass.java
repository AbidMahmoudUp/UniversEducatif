package com.example.javafxprojectusertask.Entities;

public class UserClass {
    private int idUser;
    private String userName;
    private String password;
    private String status;
    private String email;
    private String state;

    public UserClass(int idUser, String userName, String password, String status, String email) {
        this.idUser = idUser;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.email = email;
    }

    public UserClass(int idUser, String userName, String password, String status, String email,String state) {
        this.idUser = idUser;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.email = email;
        this.state=state;
    }

    public UserClass(){
        this.idUser = 0;
        this.userName ="";
        this.password = "";
        this.status = "";
        this.email = "";
        this.state="";
    }

    public UserClass(String state){
        this.state=state;
    }
    public UserClass(String email , String password) {
        this.password = password;
        this.email=email;
    }



    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserClass{" +
                "idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                '}';
    }


    public String getClassName()
    {
        return "User";
    }
}
