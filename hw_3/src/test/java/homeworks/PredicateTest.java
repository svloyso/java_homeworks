package homeworks;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.lang.RuntimeException;

public class PredicateTest {
    
    @Test
    public void allTests() {
        Predicate<Integer> isEven = new Predicate<Integer>() {
            @Override
            public Boolean invoke(Integer arg) {
                return arg % 2 == 0;
            }
        };

        Predicate<Object> moreThanFive = new Predicate<Object>() {
            @Override
            public Boolean invoke(Object arg) {
                return (Integer)arg > 5;
            }
        };
        
        assertTrue(isEven.invoke(4));
        assertTrue(isEven.invoke(123456));
        assertFalse(isEven.invoke(5));
        assertFalse(isEven.invoke(1234567));

        Predicate<Integer> isEvenAndMoreThanFive = isEven.and(moreThanFive);

        assertTrue(isEvenAndMoreThanFive.invoke(8));
        assertFalse(isEvenAndMoreThanFive.invoke(2));
        assertFalse(isEvenAndMoreThanFive.invoke(13));

        Predicate<Integer> isOdd = x -> x % 2 == 1;
        Predicate<Integer> notIsOdd = isOdd.not();

        Predicate<Integer> isEvenOrOdd = isEven.or(isOdd);

        for (int i = 0; i < 100; ++i) {
            assertTrue(isEvenOrOdd.invoke(i));
            assertTrue(isEven.invoke(i) == notIsOdd.invoke(i));
            assertTrue(Predicate.ALWAYS_TRUE.invoke(i));
            assertFalse(Predicate.ALWAYS_FALSE.invoke(i));
        }

        Predicate<Integer> failPredicate = new Predicate<Integer>() {
            @Override
            public Boolean invoke(Integer arg) {
                throw new RuntimeException("Failure of laziness test!");
            }
        };

        Predicate<Integer> isEvenOrFail = isEven.or(failPredicate);

        assertTrue(isEvenOrFail.invoke(10));
    }
}
