package com.company.animals;


import com.company.plants.PlantsValues;

import java.util.List;

public class AnimalsValues {
    public final Double weight;
    public final String name;
    public final Double capasity;
    public final Double buyCost;
    public final Double weightGrowth;
    public final Integer adultTime;
    public final Double foodNeeded;
    public final List<PlantsValues> whatItCanEat;
    public final Double breedChance;
    public final Double costOfBoughtFood;

    public AnimalsValues(Double weight, String name, Double capasity, Double buyCost, Double weightGrowth, Integer adultTime, Double foodNeeded, Double breedChance, Double costOfBoughtFood, List<PlantsValues> whatItCanEat) {
        this.weight = weight;
        this.name = name;
        this.capasity = capasity;
        this.buyCost = buyCost;
        this.weightGrowth = weightGrowth;
        this.adultTime = adultTime;
        this.foodNeeded = foodNeeded;
        this.breedChance = breedChance;
        this.costOfBoughtFood = costOfBoughtFood;
        this.whatItCanEat = whatItCanEat;
    }

    @Override
    public String toString() {
        return "AnimalsValues{" +
                "weight=" + weight +
                ", name='" + name + '\'' +
                ", capasity=" + capasity +
                ", buyCost=" + buyCost +
                ", weightGrowth=" + weightGrowth +
                ", adultTime=" + adultTime +
                ", foodNeeded=" + foodNeeded +
                ", whatItCanEat=" + whatItCanEat +
                ", breedChance=" + breedChance +
                ", costOfBoughtFood=" + costOfBoughtFood +
                "}\n";
    }
}
