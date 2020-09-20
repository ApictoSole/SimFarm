package com.company;

import com.company.animals.Animals;
import com.company.animals.AnimalsReckon;
import com.company.animals.AnimalsValues;
import com.company.game.Win;
import com.company.plants.PlantsPlanted;
import com.company.plants.PlantsSeeds;
import com.company.plants.PlantsValues;
import com.company.farms.Farms;
import com.company.game.Random;
import com.company.game.Unchanging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Main {


    public static List<PlantsValues> plantsValues = new ArrayList<>();
    public static PlantsValues wheat = new PlantsValues("wheat", 100., 500., 100., 1000., 18, 20, 5, 1000.);
    public static PlantsValues oat = new PlantsValues("oat", 2000., 500., 6000., 1000., 12, 20, 15, 1000.);
    public static PlantsValues rice = new PlantsValues("rice", 100., 500., 500., 1000., 20, 20, 25, 1000.);
    public static PlantsValues rye = new PlantsValues("rye", 5000., 500., 50., 1000., 11, 20, 35, 1000.);
    public static PlantsValues weed = new PlantsValues("weed", 10., 500., 300., 1000., 10, 10, 40, 100.);

    public static List<AnimalsValues> animalValues = new ArrayList<>();
    public static AnimalsValues Chicken = new AnimalsValues(2.5, "chicken", 0.3, 10., 0.1, 2, 0.1, 0.5, 10., new ArrayList<>());
    public static AnimalsValues Cow = new AnimalsValues(90.5, "Cow", 0.3, 400., 0.3, 3, 0.2, 0.5, 20., new ArrayList<>());
    public static AnimalsValues Sheep = new AnimalsValues(20.5, "Sheep", 0.3, 200., 0.5, 5, 0.3, 0.5, 30., new ArrayList<>());
    public static AnimalsValues Milka = new AnimalsValues(90.5, "Milka", 0.3, 500., 0.5, 5, 0.3, 0.5, 30., new ArrayList<>());
    public static AnimalsValues SuperCow = new AnimalsValues(100.0, "SuperCow", 0.3, 500., 0.5, 5, 0.5, 0.5, 50., new ArrayList<>());
    public static final Farms STARTING_FARM = new Farms(4., 2, 5000., 10000.);


    public static String TakeInputFromKeyboard() throws IOException {

        StringBuilder word = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                char c = (char) br.read();
                if (c == 13 || c == 10 || c == 9) {
                    break;
                } else {
                    word.append(c);
                }
            }

        } catch (IOException e) {
            System.out.println("Fatal error, your PC will explode after 10 s, u need to defuse it ; _ ;");
        }

        return word.toString();
    }

    public static Boolean meabyUWin(Farms farm, List<Animals> animalList, List<AnimalsReckon> animals, List<PlantsPlanted> planted, List<PlantsSeeds> storage) {

        List<AnimalsValues> uniqueAnimals = new ArrayList<>();
        List<PlantsValues> uniquePlants = new ArrayList<>();
        List<PlantsSeeds> CopyOfStorage = new ArrayList<>(storage);

        for (AnimalsReckon some : animals) {
            if (some.amount > 0 && !uniqueAnimals.contains(some.values)) {
                uniqueAnimals.add(some.values);
            }
        }
        if (uniqueAnimals.size() >= Unchanging.WIN_ANIMALS_SPECIES) {

            for (PlantsPlanted p : planted) {
                if (p.integrity > 0. && p.amount > 0 && !uniquePlants.contains(p.values)) {
                    uniquePlants.add(p.values);
                }
            }

            if (uniquePlants.size() >= Unchanging.WIN_PLANTS_SPECIES) {

                boolean food = true;

                for (Animals animal : animalList) {
                    boolean hasEnough = false;
                    for (int z = 0; z < CopyOfStorage.size() && hasEnough == false; z++) {
                        if (CopyOfStorage.get(z).amount > animal.values.foodNeeded * Unchanging.WIN_WEEKS_OF_FOOD && animal.values.whatItCanEat.contains(CopyOfStorage.get(z).values)) {
                            CopyOfStorage.get(z).amount -= animal.values.foodNeeded * Unchanging.WIN_WEEKS_OF_FOOD;
                            hasEnough = true;
                        }
                    }
                    if (hasEnough == false) {
                        food = false;
                    }
                }

                if (food) {
                    if (farm.size > Unchanging.WIN_HECTARS) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {

        animalValues.add(Chicken);
        animalValues.add(Sheep);
        animalValues.add(Cow);
        animalValues.add(Milka);
        animalValues.add(SuperCow);
        plantsValues.add(wheat);
        plantsValues.add(oat);
        plantsValues.add(rice);
        plantsValues.add(rye);
        plantsValues.add(weed);


        while (true) {
            System.out.println("SimFarm");
            System.out.println("Wellcome dear SimFarmer, EU give u  " + Unchanging.STARTING_MONEY + " money, u need to :\n "
                    + "you have to collect in the shortest possible time 20 hectares, 5 different species of farm animals, 5 different types of crops, food for all your animals for a year. \n");
            System.out.println("If u want start the game then u need enter 'start', but if u have small pipi then u can enter 'end' ;)");


            String menu = TakeInputFromKeyboard();
            switch (menu) {
                case "start":
                    Game();
                    break;

                case "end":
                    System.exit(0);

                default:
                    System.out.println("Srly ... u had 2 choice, start or end .... its not too hard");
            }

        }
    }

    public static void Game() throws IOException {

        Double money = Unchanging.STARTING_MONEY;
        Double daylyProfit = 0.;
        boolean areYouShortOnFunds = false;
        List<Animals> animals = new ArrayList<>();
        List<AnimalsReckon> breeding = new ArrayList<>();
        List<PlantsPlanted> planted = new ArrayList<>();
        List<PlantsSeeds> storage = new ArrayList<>();
        Farms farm = STARTING_FARM;

        for (AnimalsValues aVal : animalValues) {
            breeding.add(new AnimalsReckon(aVal, 0, 0));
        }

        for (PlantsValues pVal : plantsValues) {
            storage.add(new PlantsSeeds(pVal, 0.));
        }
        Comparator<PlantsSeeds> Lambda = (s1, s2) -> s1.values.buyCost.compareTo(s2.values.buyCost);
        storage.sort(Lambda);


        for (int year = 1; year <= 20; year++) {
            for (int week = 1; week <= 52; week++) {

                if (meabyUWin(farm, animals, breeding, planted, storage)) {
                    Win.Win();
                }
                System.out.println();
                MAIN_LOOP:
                while (true) {
                    System.out.println("Year: " + year + " Week: " + week + " Money: " + money);
                    InfoAnimal(animals);
                    InfoPlant(planted);
                    InfoStorage(storage);
                    if (areYouShortOnFunds) {
                        System.out.println("Your Bank account dying :/");
                    }
                    System.out.print("\nCommend list:   land - Buy/Sell land,   building - Build New building,   " +
                            "buyanimal - buy animal   buyseeds - buy seeds xd   plant - sowing seeds harvest - harvest the crop\n" +
                            "   sellanimal - sell animal   sellplant - sell plant/seeds   next - end this week" +
                            "   quit - exit game\n");
                    String a = TakeInputFromKeyboard();
                    switch (a) {
                        case "land":
                            double amountOfLand;
                            System.out.println("Money $$$$: " + money);
                            System.out.println(farm);

                            try {
                                System.out.println("The price per hectare is:" + farm.hectarCost + " How many hectares do you want to buy / sell");
                                amountOfLand = Double.parseDouble(Main.TakeInputFromKeyboard());

                                if (farm.size + amountOfLand > 0.) {
                                    if (money > farm.hectarCost * amountOfLand) {
                                        farm.size += amountOfLand;
                                        money -= farm.hectarCost * amountOfLand;
                                    } else {
                                        System.out.println("Not enough cash ... stranger ");
                                    }
                                } else {
                                    System.out.println("First buy, next sell .... i mean its not your land but u can buy some");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (NumberFormatException e) {
                                System.out.println("Please u cant just buy x somethink or a or b or c just press number > . >");
                            }
                            break;
                        case "building":
                            System.out.println("Money $$$$: " + money);
                            System.out.println(farm);

                            KoBreak:
                            while (true) {
                                System.out.println(" The price of building the building is :" + farm.buildingCost + " Do u want continue ? Yep/Nope");
                                String decision = Main.TakeInputFromKeyboard();

                                switch (decision) {

                                    default:
                                        break;

                                    case "Nope":
                                        System.out.println("Nah ok");
                                        break KoBreak;

                                    case "Yep":
                                        if (money > farm.buildingCost) {
                                            farm.buildingAmount++;
                                            System.out.println(" Bob the Builder know what he do");
                                            money -= farm.buildingCost;
                                            break KoBreak;
                                        } else {
                                            System.out.println("Not enough cash ... stranger");
                                        }
                                }
                            }
                            break;
                        case "buyanimal":
                            int idAnimal;
                            int amountAnimal;
                            AnimalsValues valuesAnimal;
                            double farmCapasity;
                            System.out.println(Main.animalValues);
                            System.out.println("Money $$$$: " + money);

                            try {
                                System.out.println("Choose an animal ? (just press number  0 to " + (Main.animalValues.size() - 1) + " ;)");
                                idAnimal = Integer.parseInt(Main.TakeInputFromKeyboard());

                                valuesAnimal = Main.animalValues.get(idAnimal);

                                System.out.println("how many animals do u want");
                                amountAnimal = Integer.parseInt(Main.TakeInputFromKeyboard());

                                double farmCapasityTaken = 0;
                                for (Animals anim : animals) {
                                    farmCapasityTaken += anim.values.capasity;
                                }

                                farmCapasity = farm.buildingAmount - farmCapasityTaken;

                                if (money > amountAnimal * valuesAnimal.buyCost) {
                                    if (farmCapasity > amountAnimal * valuesAnimal.weight) {
                                        for (int i = 0; i < amountAnimal; i++) {
                                            animals.add(new Animals(valuesAnimal));
                                        }
                                        money -= amountAnimal * valuesAnimal.buyCost;
                                    } else {
                                        System.out.println("Your farm is too small my friend i think u need have more lands and build bigger kingdom for animals * > *");
                                    }
                                } else {
                                    System.out.println("Not enough cash ... stranger");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("No no no no no :< NUMBER ... plis :) ");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println(" Idk what u talking about");
                            }
                            break;
                        case "buyplant":
                            int idSeeds;
                            int amountSeeds;
                            PlantsValues valuesSeeds;
                            System.out.println(Main.plantsValues);
                            System.out.println("Money $$$$: " + money);
                            try {
                                System.out.println("Whats plants do u want to buy (select number 0 to " + (Main.plantsValues.size() - 1));
                                idSeeds = Integer.parseInt(Main.TakeInputFromKeyboard());

                                valuesSeeds = Main.plantsValues.get(idSeeds);

                                System.out.println("How much tons do u want to buy");
                                amountSeeds = Integer.parseInt(Main.TakeInputFromKeyboard());

                                if (amountSeeds > 0) {
                                    if (money > amountSeeds * valuesSeeds.buyCost) {

                                        for (int i = 0; i < storage.size(); i++) {
                                            if (storage.get(i).values == valuesSeeds) {
                                                storage.get(i).amount += amountSeeds;
                                                money -= amountSeeds * valuesSeeds.buyCost;
                                                System.out.println("YE YE YE you buy the seeds now u must play in plants vs zombie muahahahah, gj");
                                            }
                                        }


                                    } else {
                                        System.out.println("Not enough cash ... stranger");
                                        money -= 0.;
                                    }
                                } else {
                                    System.out.println("Can u start doing what i sad :< stop breaking this game or i will show your mom, your browser history i am not joking");
                                }


                            } catch (NumberFormatException e) {
                                System.out.println("NUMBER plis number ....");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("idk what do u want form me :<");
                            } finally {
                                money -= 0.;
                            }
                            break;
                        case "plant":
                            int idPlant;
                            double amountPlant;
                            PlantsValues valuesPlant;
                            double farmCapasity2;
                            System.out.println(Main.plantsValues);
                            System.out.println("Your seeds : ");
                            InfoStorage(storage);

                            try {
                                System.out.println("What seeds do u want to plant (choose number but plis number 1 to " + (Main.plantsValues.size() - 1));
                                idPlant = Integer.parseInt(Main.TakeInputFromKeyboard());

                                valuesPlant = Main.plantsValues.get(idPlant);

                                System.out.println("how many hectares do you want to plant");
                                amountPlant = Integer.parseInt(Main.TakeInputFromKeyboard());

                                double farmCapasityTaken = 0;
                                for (PlantsPlanted p : planted) {
                                    farmCapasityTaken += p.amount;
                                }

                                farmCapasity2 = farm.size - farmCapasityTaken;

                                if (farmCapasity2 > amountPlant) {
                                    for (PlantsSeeds s : storage) {
                                        if (s.values == valuesPlant) {
                                            if (s.amount > amountPlant) {
                                                planted.add(new PlantsPlanted(valuesPlant, amountPlant));
                                                s.amount -= amountPlant;
                                                System.out.println("bomb ... ehm seeds has been planted :D ");
                                            } else {
                                                System.out.println("Not enough seeds ... stranger");
                                            }
                                            break;
                                        }
                                    }

                                } else {
                                    System.out.println("no enouth space");
                                }


                            } catch (NumberFormatException e) {
                                System.out.println("i need number ... only number");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("idk what do u want");
                            }
                            break;
                        case "harvest":
                            int idHarvest;
                            double amountHarvest;
                            System.out.println("Plants ready to harvest");
                            for (int i = 0; i < planted.size(); i++) {
                                if (planted.get(i).ready) {
                                    System.out.println("Id: " + i + " " + planted.get(i));
                                }
                            }
                            try {
                                System.out.println("Select idA to harvest");
                                idHarvest = Integer.parseInt(Main.TakeInputFromKeyboard());

                                if (planted.get(idHarvest).ready) {
                                    amountHarvest = planted.get(idHarvest).amount;

                                    if (money > amountHarvest * planted.get(idHarvest).values.costHarvest) {

                                        for (PlantsSeeds s : storage) {
                                            if (s.values == planted.get(idHarvest).values) {

                                                s.amount += amountHarvest * planted.get(idHarvest).integrity * 0.01;
                                                planted.remove(idHarvest);
                                                System.out.println("Get");
                                                money -= amountHarvest * planted.get(idHarvest).values.costHarvest;
                                                break;
                                            }
                                        }


                                    } else {
                                        System.out.println("Not enough cash ... stranger");
                                    }
                                } else {
                                    System.out.println("Incorret idA ");
                                }


                            } catch (NumberFormatException e) {
                                System.out.println("Number plis number");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Incorret idA");
                            }
                            break;
                        case "sellanimal":
                            int idSellAnimal;

                            InfoAnimal(animals);

                            try {
                                System.out.println("What animal do u want to sell, choise number 1 to " + (animals.size() - 1) + ")");
                                idSellAnimal = Integer.parseInt(Main.TakeInputFromKeyboard());

                                double price = animals.get(idSellAnimal).price;

                                if (animals.get(idSellAnimal).age >= animals.get(idSellAnimal).values.adultTime) {
                                    for (AnimalsReckon b : breeding) {
                                        if (b.values == animals.get(idSellAnimal).values) {
                                            b.adultAmount--;
                                            break;
                                        }
                                    }
                                }
                                animals.remove(idSellAnimal);
                                System.out.println("SOLD");
                                money += price;
                            } catch (NumberFormatException e) {
                                System.out.println("number. .........");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("incoret idA");
                            }
                            break;
                        case "sellplant":
                            int idSellPlant;
                            double amountSellPlant;
                            InfoStorage(storage);
                            try {
                                System.out.println("What plant do u want to sell select number  0 to " + (storage.size() - 1));
                                idSellPlant = Integer.parseInt(Main.TakeInputFromKeyboard());

                                System.out.println("How many tons do u want to sell");
                                amountSellPlant = Integer.parseInt(Main.TakeInputFromKeyboard());

                                if (amountSellPlant > 0) {
                                    if (storage.get(idSellPlant).amount - amountSellPlant > 0 && storage.get(idSellPlant).amount > amountSellPlant) {

                                        storage.get(idSellPlant).amount -= amountSellPlant;
                                        System.out.println("SOLD");
                                        money += amountSellPlant * storage.get(idSellPlant).values.buyCost;
                                    } else {
                                        System.out.println("Not enough plants ... stranger");
                                    }
                                } else {
                                    System.out.println("U cant buy seeds there");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("number plis number");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("incorrect  idA");
                            }
                            break;
                        case "next":
                            break MAIN_LOOP;
                        case "quit":
                            System.out.println("small pipi");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("idk what do u want from me");
                            System.out.println();
                            break;
                    }
                }
                System.out.println();
                areYouShortOnFunds = false;
                for (Animals animal : animals) {
                    animal.age++;
                    if (animal.age.equals(animal.values.adultTime)) {
                        for (AnimalsReckon b : breeding) {
                            if (b.values == animal.values) {
                                b.adultAmount++;
                                break;
                            }
                        }
                    }
                    boolean animalAte = false;
                    switch (animal.values.name) {

                        case "Chicken":
                            daylyProfit += Unchanging.EGG_VALUE * Random.RandomInInterval(Unchanging.EGG_MIN, Unchanging.EGG_MAX);
                            break;

                        case "Cow":
                            daylyProfit += Unchanging.COW_MILK_VALUE * Random.RandomInInterval(Unchanging.COW_MILK_MIN, Unchanging.COW_MILK_MAX);
                            break;

                        case "Sheep":
                            daylyProfit += Unchanging.SHEEP_MILK_VALUE * Random.RandomInInterval(Unchanging.SHEEP_MILK_MIN, Unchanging.SHEEP_MILK_MAX);
                            break;

                        default:
                            break;
                    }
                    System.out.println("U get " + daylyProfit + " for sell eggs and milks");
                    money += daylyProfit;
                    daylyProfit = 0.;
                    for (int z = 0; z < storage.size() && animalAte == false; z++) {
                        if (storage.get(z).amount > animal.values.foodNeeded && animal.values.whatItCanEat.contains(storage.get(z).values)) {
                            storage.get(z).amount -= animal.values.foodNeeded;
                            animalAte = true;
                        }
                }
                    if (animalAte == false && money > animal.values.costOfBoughtFood) {
                        money -= animal.values.costOfBoughtFood;
                        animalAte = true;
                    }
                    if (animalAte) {
                        if (animal.age <= animal.values.adultTime) {
                            animal.weight += animal.values.weightGrowth;
                            animal.price += animal.values.buyCost / animal.values.adultTime;
                        }
                    } else {
                        animal.weight -= animal.values.weightGrowth / 4;
                        animal.price -= animal.values.buyCost / (4 * animal.values.adultTime);
                        areYouShortOnFunds = true;
                        System.out.print("Your animals starving");
                    }


                }
                for (PlantsPlanted value : planted) {
                    value.age++;
                    Double price = value.amount * value.values.costPests;

                    if (value.age == value.values.growTime) {
                        value.ready = true;
                    }
                    if (price > money) {
                        areYouShortOnFunds = true;
                        if (Random.RandomizeBool(0.1)) {
                            value.integrity -= Random.RandomInInterval(20., 40.);
                            if (value.integrity < 0.) {
                                value.integrity = 0.;
                            }
                        }
                    } else {
                        money -= price;
                    }
                }
                if (Random.RandomizeBool(0.05)) {
                    System.out.println("oh no charizard bruned your plants :/ sad ; _ ;");
                    for (PlantsPlanted value : planted) {
                        value.integrity -= Random.RandomInInterval(5., 35.);
                        if (value.integrity < 0.) {
                            value.integrity = 0.;
                        }
                    }

                }
                if (!areYouShortOnFunds) {
                    for (AnimalsReckon animalNumber : breeding) {
                        for (int pair = animalNumber.adultAmount / 2; pair > 0; pair--) {
                            if (Random.RandomizeBool(animalNumber.values.breedChance)) {
                                animals.add(new Animals(animalNumber.values));

                                for (AnimalsReckon a : breeding) {
                                    if (a.values == animalNumber.values) {
                                        a.amount++;
                                        break;
                                    }
                                }

                                System.out.println(animalNumber.values.name + " make ehm eeee made children XD ");
                            }
                        }
                    }
                }


            }
        }
    }

    public static void InfoAnimal(List<Animals> list) {
        System.out.println("Animal: Type, Age, Grow, Weight, Price");
        for (Animals a : list) {
            System.out.println("        " + a.values.name + ", " + a.age + ", " + a.values.adultTime + ", " + a.weight + ", " + a.price);
        }
    }

    public static void InfoPlant(List<PlantsPlanted> list) {
        System.out.println("Planted:  Type, Age, Grow, % health, can get");
        for (PlantsPlanted p : list) {
            System.out.println("        " + ", " + p.values.name + ", " + p.age + ", " + p.values.growTime + ", " + p.integrity + ", " + p.ready);
        }
    }

    public static void InfoStorage(List<PlantsSeeds> list) {
        System.out.println("Storage: Type, how much,  price per kg ,when can planted, cover price");
        for (PlantsSeeds s : list) {
            System.out.println("        " + s.values.name + ", " + s.amount + ", " + s.values.buyCost + ", " + s.values.plantingStart + "-" + s.values.plantingEnd + ", " + s.values.costPests);
        }
    }

}