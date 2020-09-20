package com.company.plants;

public class PlantsValues {
    public String name;
    public Double buyCost;
    public Double costPlanting;
    public Double costPests;
    public  Double yield;
    public Integer growTime;
    public Integer plantingStart;
    public Integer plantingEnd;
    public Double costHarvest;

    public PlantsValues(String name, Double buyCost, Double costPlanting, Double costPests, Double yield, Integer growTime, Integer plantingStart, Integer plantingEnd, Double costHarvest) {
        this.name = name;
        this.buyCost = buyCost;
        this.costPlanting = costPlanting;
        this.costPests = costPests;
        this.yield = yield;
        this.growTime = growTime;
        this.plantingStart = plantingStart;
        this.plantingEnd = plantingEnd;
        this.costHarvest = costHarvest;
    }
    @Override
    public String toString() {
        return "PlantsValues{" +
                "name='" + name + '\'' +
                ", buyCost=" + buyCost +
                ", costPlanting=" + costPlanting +
                ", costPests=" + costPests +
                ", yield=" + yield +
                ", growTime=" + growTime +
                ", plantingStart=" + plantingStart +
                ", plantingEnd=" + plantingEnd +
                ", costHarvest=" + costHarvest +
                "}\n";
    }
}
