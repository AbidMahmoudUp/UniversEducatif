package com.example.javafxprojectusertask.Entities;

public class ProfileCoach extends Profile {

protected int idModule;

public ProfileCoach(int idProfile, int idUser, String firstName, String lastName, String picture, String address, String location, int phoneNumber,int idModule)
{
    super(idProfile,idUser,firstName,lastName,picture,address,location,phoneNumber);
    this.idModule=idModule;
}
public ProfileCoach(){}

    public int getIdModule() {
        return idModule;
    }

    public void setIdModule(int idModule) {
        this.idModule = idModule;
    }


    public String getClassName()
    {
        return "Profile Coach";
    }
}
