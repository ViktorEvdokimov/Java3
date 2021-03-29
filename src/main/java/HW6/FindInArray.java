package HW6;

import java.util.Stack;

public class FindInArray {
    public int[] returnAllAfterLast4 (int[] array){
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
            for (int i=0; i<returnedStack.size(); i++){
                returnedArray[i] = returnedStack.pop();
            }
            return returnedArray;
        } else  throw new RuntimeException("Array have not 4.");
    }
    public boolean isArrayHave1Or4 (int[] array){
        for (int nod: array) {
            if (nod == 1 || nod == 4) return true;
        }
        return false;
    }
}

