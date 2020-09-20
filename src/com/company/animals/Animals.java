package com.company.animals;

public class Animals {
    public Integer id;
    public AnimalsValues values;
    public Integer age;
    public Double weight;
    public Double price;
    public static Integer idId = 0;

    public Animals(AnimalsValues species) {
        this.id = idId++;
        this.values = species;
        this.price = species.buyCost/2.;
        this.weight = species.weight;
    }
    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", species=" + values.name +
                ", age=" + age +
                ", weight=" + weight +
                ", sellPrice=" + price +
                '}';
    }
}
