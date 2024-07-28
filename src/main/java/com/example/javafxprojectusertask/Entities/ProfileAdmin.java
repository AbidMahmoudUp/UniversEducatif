package com.example.javafxprojectusertask.Entities;

import java.util.ArrayList;

public class ProfileAdmin extends Profile {

    protected String reponsabilty;

    protected String title;



    public ProfileAdmin()
    {
        super();
    }
    public ProfileAdmin(int idProfile,int idUser, String firstName, String lastName, String picture, String address, String location,int phoneNumber,String reponsabilty,String title) {
        super(idProfile,idUser, firstName, lastName, picture, address, location,phoneNumber);
        this.reponsabilty = reponsabilty;
        this.title=title;
    }
    public ProfileAdmin(int idUser,String picture)
    {
        super(idUser,picture);
    }
    public String getReponsabilty() {
        return reponsabilty;
    }

    public void setReponsabilty(String reponsabilty) {
        this.reponsabilty = reponsabilty;
    }

    @Override
    public String toString() {
        return "ProfileAdmin{" +
                "reponsabilty='" + reponsabilty + '\'' +

                '}';
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getClassName()
    {
        return "Profile Admin";
    }
}
