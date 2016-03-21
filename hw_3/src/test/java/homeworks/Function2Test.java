package homeworks;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Function2Test {
    @Test
    public void testCompose() {
        Function2<Integer, Integer, Integer> plus = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer invoke(Integer x, Integer y) {
                return x + y;
            }
        };
        Function1<Integer, Integer> multTwo = new Function1<Integer, Integer>() {
            @Override
            public Integer invoke(Integer x) {
                return x * 2;
            }
        };
        Function2<Integer, Integer, Integer> plusAndMultTwo = plus.compose(multTwo);
        assertEquals(10, (int)plusAndMultTwo.invoke(2, 3));
    }

    @Test
    public void testBind() {
        Function2<Integer, Integer, Integer> minus = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer invoke(Integer x, Integer y) {
                return x - y;
            }
        };
        Function1<Integer, Integer> minusOne = minus.bind2(1);
        Function1<Integer, Integer> fiveMinus = minus.bind1(5);

        assertEquals(9, (int)minusOne.invoke(10));
        assertEquals(4, (int)fiveMinus.invoke(1));
    }

    @Test
    public void testCurry() {
        Function2<Integer, Integer, Integer> minus = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer invoke(Integer x, Integer y) {
                return x - y;
            }
        };

        assertEquals(3, (int)minus.curry().invoke(5).invoke(2));
    }
}

