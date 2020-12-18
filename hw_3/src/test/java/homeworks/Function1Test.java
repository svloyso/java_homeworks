package homeworks;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Function1Test {
    @Test
    public void testCompose() {
        Function1<Integer, Integer> plusOne = new Function1<Integer, Integer>() {
            @Override
            public Integer invoke(Integer x) {
                return x + 1;
            }
        };
        Function1<Integer, Integer> multTwo = new Function1<Integer, Integer>() {
            @Override
            public Integer invoke(Integer x) {
                return x * 2;
            }
        };

        Function1<Integer, Integer> plusOneMultTwo = plusOne.compose(multTwo);
        Function1<Integer, Integer> multTwoPlusOne = multTwo.compose(plusOne);
        assertEquals(6, (int)plusOneMultTwo.invoke(2));
        assertEquals(5, (int)multTwoPlusOne.invoke(2));


        Function1<Object, Integer> minusTen = x -> (Integer)x - 10;
        Function1<Integer, Integer> multTwoMinusTen = multTwo.compose(minusTen);
        assertEquals(2, (int)multTwoMinusTen.invoke(6));
    }
}

