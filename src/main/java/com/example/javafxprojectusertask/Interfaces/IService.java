package com.example.javafxprojectusertask.Interfaces;

import java.util.ArrayList;

public interface IService<T> {

    void add (T t);
    ArrayList<T> getAll();
    void update(T t);
    boolean delete(T t);
}
