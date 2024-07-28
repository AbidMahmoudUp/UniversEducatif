package com.example.javafxprojectusertask.Utilities;

import com.example.javafxprojectusertask.Entities.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlobalListMapSingleton {

    private static GlobalListMapSingleton instance;
    private List<Map<Produit, Integer>> listMap;

    private GlobalListMapSingleton() {
        // Initialize the list of maps
        listMap = new ArrayList<>();
    }

    public static synchronized GlobalListMapSingleton getInstance() {
        if (instance == null) {
            instance = new GlobalListMapSingleton();
        }
        return instance;
    }

    public List<Map<Produit, Integer>> getListMap() {
        return listMap;
    }

    public void setListMap(List<Map<Produit, Integer>> listMap) {
        this.listMap = listMap;
    }

    public void addToListMap(Map<Produit, Integer> map) {
        listMap.add(map);
    }
    public void removeFromListMap(Map<Produit, Integer> mapToRemove) {
        listMap.remove(mapToRemove);
    }


}

