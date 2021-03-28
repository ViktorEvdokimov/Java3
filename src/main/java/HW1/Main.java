package HW1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
//        testSwap();
//        testToList();
        testBoxes();

    }

    private static void testBoxes (){
        Box<Apple> firstAppleBox = new Box<>("Apple", new Apple(), new Apple(), new Apple(), new Apple()
                , new Apple(), new Apple());
        Box<Apple> secondAppleBox = new Box<>("Apple", new Apple(), new Apple());
        Box<Orange> firstOrangeBox = new Box<>("Orange");
        firstOrangeBox.add(new Orange(), new Orange(), new Orange(), new Orange());
        System.out.println("Weight of the first box of apples: "+ firstAppleBox.getWeight());
        System.out.println("Weight of the second box of apples: "+ secondAppleBox.getWeight());
        System.out.println("Weight of the first box of oranges: "+ firstOrangeBox.getWeight());
        System.out.println("Is wight of firstAppleBox==wight of secondAppleBox: " +
                firstAppleBox.compare(secondAppleBox));
        System.out.println("Is wight of firstAppleBox==wight of firstOrangeBox: " +
                firstAppleBox.compare(firstOrangeBox));
        System.out.println("Interlard firstOrangeBox to firstAppleBox: " +
                firstAppleBox.interlardFruits(firstOrangeBox));
        System.out.println("Weight of the first box of apples: "+ firstAppleBox.getWeight());
        System.out.println("Weight of the first box of oranges: "+ firstOrangeBox.getWeight());
        System.out.println("Interlard secondAppleBox to firstAppleBox: " +
                firstAppleBox.interlardFruits(secondAppleBox));
        System.out.println("Weight of the first box of apples: "+ firstAppleBox.getWeight());
        System.out.println("Weight of the second box of apples: "+ secondAppleBox.getWeight());
    }

    private static void testToList(){
        Integer[] testInt = new Integer[]{1, 5, 6, 7, 10};
        System.out.println(toArrayList(testInt).toString());

        String[] testString = new String[]{"Hello ", "world ", "!"};
        System.out.println(toArrayList(testString).toString());
    }

    public static <T> ArrayList<T> toArrayList (T[] array){
        ArrayList<T> returnList = new ArrayList<>(array.length);
        for (T item : array){
            returnList.add(item);
        }
        return returnList;
    }

    private static void testSwap (){
        Integer[] testInt = new Integer[]{1, 5, 6, 7, 10};
        swapElements(testInt, 0, 3);
        System.out.println(Arrays.toString(testInt));
        String[] testString = new String[]{"Hello ", "! ", "world "};
        swapElements(testString, 1, 2);
        System.out.println(Arrays.toString(testString));
    }

    public static <T> T[]  swapElements (T[] array, int firstIndex, int secondIndex){
        T temp = array[firstIndex];
        array[firstIndex]=array[secondIndex];
        array[secondIndex]=temp;
        return array;
    }
}
