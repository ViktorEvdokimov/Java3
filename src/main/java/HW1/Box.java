package HW1;

import java.util.ArrayList;
import java.util.Collection;

public class Box<F extends Fruit> {
    private ArrayList<F> fruits;
    private String typeOfBox;


    public Box(String typeOfBox) {
        this.typeOfBox=typeOfBox;
        fruits = new ArrayList<>();
    }

    public Box(String typeOfBox, F fruit, F... otherFruits) {
        this.typeOfBox=typeOfBox;
        fruits = new ArrayList<>();
        this.add(fruit);

        for (F item : otherFruits){
            this.add(item);

        }
    }

    public void setTypeOfBox(String typeOfBox) {
        this.typeOfBox = typeOfBox;
    }

    public void add (F fruit){
        if (fruit.getType().equals(typeOfBox)) {
            fruits.add(fruit);
        } else System.out.println("Invalid type of box");

    }

    public void add (F fruit,  F... otherFruits){
        this.add(fruit);

        for (F item : otherFruits){
            this.add(item);

        }
    }

    public void add (F[] inputFruits){
        for (F item : inputFruits){
            this.add(item);

        }
    }

    @SuppressWarnings("unchecked")
    public boolean interlardFruits (Box<?> otherBox){
        if (this.getType().equals(otherBox.getType())) {
            fruits.addAll((Collection<? extends F>) otherBox.getAllFruits());

            return true;
        }
        System.out.println("Fruit types don`t fit");
        return false;
    }

    public String getType(){
        return typeOfBox;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<F> getAllFruits(){
        ArrayList<F> temp = new ArrayList<>(fruits.size());
        temp.addAll(fruits);
        fruits.clear();

        return temp;
    }

    public double getWeight() {
        double sumWeight=0.0;
        for (F item : fruits){
            sumWeight+=item.getWeight();
        }
        return sumWeight;
    }

    public boolean compare (Box<?> otherBox){
        return this.getWeight()== otherBox.getWeight();
    }
}
