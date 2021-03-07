package HW1;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        testSwap();

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
