package com.company.game;

import com.company.animals.Animals;
import com.company.plants.PlantsPlanted;
import com.company.plants.PlantsSeeds;

import java.util.List;

public class Random {

    public static Boolean RandomizeBool(Double p) {
        if (Math.random() < p) {
            return true;
        } else {
            return false;
        }
    }

    public static Double RandomInInterval(Double min, Double max) {
        java.util.Random rand = new java.util.Random();
        Double wynik = ((double) rand.nextInt((int) (max - min))) + min;
        return wynik;
    }
}