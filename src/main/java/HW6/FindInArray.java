package HW6;

import java.util.Arrays;
import java.util.Stack;

public class FindInArray {
    public static void main(String[] args) {
        FindInArray findInArray = new FindInArray();
        System.out.println(Arrays.toString(
                findInArray.returnAllAfterLastFor(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7})));
        System.out.println(
                findInArray.isArrayHaveOneOrFor(new int[]{4, 4, 4}));
    }

    public int[] returnAllAfterLastFor (int[] array){
        boolean is4Founded = false;
        Stack<Integer> returnedStack = new Stack<>();
        for (int i= array.length-1; i>=0; i--){
            if (array[i] != 4) returnedStack.push(array[i]);
            else {
                is4Founded = true;
                break;
            }
        }
        if (is4Founded) {
            int[] returnedArray = new int[returnedStack.size()];
            int size = returnedStack.size();
            for (int i=0; i<size; i++){
                returnedArray[i] = returnedStack.pop();
            }
            return returnedArray;
        } else  throw new RuntimeException("Array have not 4.");
    }
    public boolean isArrayHaveOneOrFor (int[] array){
        boolean isArrayHave1 = false;
        boolean isArrayHave4 = false;
        for (int nod: array) {
            if (nod != 1 && nod != 4) return false;
            if (nod == 1) isArrayHave1 = true;
            if (nod == 4) isArrayHave4 = true;
        }
        return isArrayHave1 && isArrayHave4;
    }
}

