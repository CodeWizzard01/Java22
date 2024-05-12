package com.codewiz.java22;

import java.util.List;
import java.util.Map;

public class UnnamedVariable {



    public boolean isNumber(String s){
        try{
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException _ ) {
            return false;
        }
    }


    public static void main1(String[] args) {

        List<String> studentList = List.of("John", "Doe", "Jane", "Doe", "Alice", "Wonderland");
        for (String _ : studentList) {
            // some logic
        }

        studentList.stream().forEach(_ -> {
            // some logic
        });

        Map<String,Integer> studentAgeMap = Map.of("John", 20, "Doe", 22, "Jane", 21, "Doe", 23, "Alice", 24, "Wonderland", 25);
        studentAgeMap.forEach((_, age) -> {
            if (age >= 18) {
                // some logic
            }
        });
    }

    sealed interface Pet permits Dog, Cat, Bird {};
    record Dog(String name,String breed, int age) implements Pet {};
    record Cat(String name, String breed, int age) implements Pet {};
    record Bird(String name, String species, int age) implements Pet {};

    public String getAnimalDetails(Pet animal){
        return switch (animal) {
            case Dog(String name, String breed, _) -> "Dog: " + name + " Breed: " + breed;
            case Cat(String name, String breed, _) -> "Cat: " + name + " Breed: " + breed;
            case Bird(String name, String species, _) -> "Bird: " + name + " Species: " + species;
        };
    }





}


