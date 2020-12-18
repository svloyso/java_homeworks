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

    
    class TakeN implements Predicate<Object> {
        int n;
        int max;
        public TakeN(int N) { max = N; n = 0; }
        @Override
        public Boolean invoke(Object x) {
            return n++ < max;
        }
     }

    @Test
    public void takeTest() {
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

        assertEquals(Arrays.asList(1, 1, 1, 1, 1), Collections.takeWhile(new TakeN(5), ones));
        assertEquals(Arrays.asList(1, 1, 1, 1, 1), Collections.takeUnless(new TakeN(5).not(), ones));
    }

    @Test
    public void foldTest() {
        Function2<Integer, Integer, Integer> mult = (a, b) -> a * b;
        Function2<Object, Object, Integer> nonAssociative = (a, b) -> ((Integer)a + (Integer)b)*(Integer)b;
        assertEquals(120, (int)Collections.foldl(mult, 1, Arrays.asList(2, 3, 4, 5)));
        assertEquals(120, (int)Collections.foldr(mult, 1, Arrays.asList(2, 3, 4, 5)));
        assertEquals(72, (int)Collections.foldr(nonAssociative, 2, Arrays.asList(1, 2)));
        assertEquals(10, (int)Collections.foldl(nonAssociative, 2, Arrays.asList(1, 2)));
    }
}
