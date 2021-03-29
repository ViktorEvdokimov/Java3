import HW6.FindInArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


public class TestFindInArray {
    private FindInArray findInArray;

    @BeforeEach
    public void init() {
        findInArray = new FindInArray();
    }

    @ParameterizedTest
    @MethodSource("dataForReturnAllAfterLastFor")
    public void positiveTestReturnAllAfterLastFor(int[] a, int[] result){
        Assertions.assertArrayEquals(result, findInArray.returnAllAfterLastFor(a));
    }
    public static Stream<Arguments> dataForReturnAllAfterLastFor(){
        return Stream.of(
                Arguments.of(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7}),
                Arguments.of(new int[]{4, 2, 8, 1, 2, 3, 5, 1, 7}, new int[]{2, 8, 1, 2, 3, 5, 1, 7}),
                Arguments.of(new int[]{4, 2, 8, 1, 2, 3, 5, 1, 7, 9, 10, 4, 1111, 15934, 154, 44},
                        new int[]{1111, 15934, 154, 44}),
                Arguments.of(new int[]{4}, new int[]{})
        );
    }

    @Test
    public void negativeTestReturnAllAfterLastFor(){
        Assertions.assertThrows(RuntimeException.class, ()->
                findInArray.returnAllAfterLastFor(new int[]{1, 5, 7, 8, 9, 15, 44, 14, 8}));
    }

    @ParameterizedTest
    @MethodSource("dataForReturnAllAfterLast4")
    public void positiveTestisArrayHaveOneOrFor(int[] a, boolean result){
        Assertions.assertEquals(result, findInArray.isArrayHaveOneOrFor(a));
    }
    public static Stream<Arguments> dataForReturnAllAfterLast4(){
        return Stream.of(
                Arguments.of(new int[]{1, 1, 1, 4, 4, 1, 4, 4}, true),
                Arguments.of(new int[]{1, 1, 1, 1, 1, 1, }, false),
                Arguments.of(new int[]{4, 4, 4, 4}, false),
                Arguments.of(new int[]{1, 4, 4, 1, 1, 4, 3}, false)
        );
    }

}
