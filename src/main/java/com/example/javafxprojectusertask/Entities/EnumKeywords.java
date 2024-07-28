package com.example.javafxprojectusertask.Entities;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EnumKeywords {
    COURSE, MODULE,DASHBOARD,OKAY,TEST;

    /**
     * reruns the list of EnumKeywords.
     *
     * @return list of String.
     */
    public static List<String> getKeywords() {
        return Stream.of(EnumKeywords.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }


}
