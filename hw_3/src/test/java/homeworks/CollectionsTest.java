package homeworks;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.util.Iterator;

public class CollectionsTest {
    @Test
    public void mapTest() {
        Function1<Integer, Integer> plusOne = a -> a + 1;
        List<Integer> a = Arrays.asList(1, 2, 3, 4, 5);

        ArrayList<Integer> res = Collections.map(plusOne, a);

        assertEquals(Arrays.asList(2, 3, 4, 5, 6), res);
    }

    @Test
    public void filterTest() {
        Predicate<Integer> isEven = a -> a % 2 == 0;
        List<Integer> a = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        ArrayList<Integer> res = Collections.filter(isEven, a);
        assertEquals(Arrays.asList(2, 4, 6, 8, 10), res);
    }

    @Test
    public void takeTest() {
        Predicate<Integer> takeFive = new Predicate<Integer>() {
            int n = 0;
            int max = 5;
            @Override
            public Boolean invoke(Integer x) {
                return n++ < max;
            }
        };

        Iterable<Integer> ones = new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() { return true; }
                    @Override
                    public Integer next() { return 1; }
                    @Override
                    public void remove() { /*EMPTY*/ }
                };
            }
        };

        assertEquals(Arrays.asList(1, 1, 1, 1, 1), Collections.takeWhile(takeFive, ones));
    }

    @Test
    public void foldTest() {
        Function2<Integer, Integer, Integer> mult = (a, b) -> a * b;
        assertEquals(120, (int)Collections.foldl(mult, 1, Arrays.asList(2, 3, 4, 5)));
        assertEquals(120, (int)Collections.foldr(mult, 1, Arrays.asList(2, 3, 4, 5)));
    }
}
