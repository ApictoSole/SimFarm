package com.company.farms;

public class Farms {

        public Double size;
        public Integer buildingAmount;
        public Double hectarCost;
        public Double buildingCost;

        public Farms(Double size, Integer buildingAmount, Double hectarCost, Double buildingCost) {
            this.size = size;
            this.buildingAmount = buildingAmount;
            this.hectarCost = hectarCost;
            this.buildingCost = buildingCost;
        }

        @Override
        public String toString() {
            return "Farm{" +
                    "size=" + size +
                    ", buildingAmount=" + buildingAmount +
                    ", hectarCost=" + hectarCost +
                    ", buildingCost=" + buildingCost +
                    '}';
        }

    }
