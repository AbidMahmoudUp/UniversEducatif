package com.example.javafxprojectusertask.Interfaces;

import com.example.javafxprojectusertask.Entities.Logs;
import com.mysql.jdbc.log.Log;

import java.util.ArrayList;

public interface LogsInterface {
    void addLog (Logs log);
    ArrayList <Logs> getLogsByUser(int idUser);
}
