package sp;

import static org.junit.Assert.*;
import org.junit.Test;
import static sp.SecondPartTasks.*;

import java.util.*;
import java.util.stream.DoubleStream;


/**
 * Created by dsavv on 10.04.2016.
 */
public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() throws Exception {
        List<String> paths = Arrays.asList(
                "src/test/res/text1.txt",
                "src/test/res/text2.txt",
                "src/test/res/text3.txt"
        );

        List<String> result1 = Arrays.asList(
                "Java: write once, run away! -Brucee",
                "One of my most productive days was throwing away 1000 lines of code. - Ken Thompson"
        );

        List<String> result2 = Arrays.asList(
                "Before software can be reusable it first has to be usable. – Ralph Johnson"       
        );

        assertEquals(result1, findQuotes(paths, "away"));
        assertEquals(result2, findQuotes(paths, "usable"));
    }

    @Test
    public void testPiDividedBy4() throws Exception {
        assertEquals(
                Math.PI/4,
                piDividedBy4(),
                0.001);
    }

    @Test
    public void testFindPrinter() throws Exception {
        Map<String, List<String>> authors = new HashMap<String, List<String>>() {{
            put("Pushkin", Arrays.asList("U Lukomor'ya dub zelyoniy", "Pikovaya Dama", "Ruslan & Ludmila"));
            put("Dostoevskiy", Arrays.asList("Prestuplenie & Nakazanie", "Idiot"));
            put("Tolstoy", Arrays.asList("Voyna & Mir", "Anna Karenyna"));
        }};

        assertEquals("Pushkin", findPrinter(authors));
        assertEquals("", findPrinter(Collections.emptyMap()));
    }

    @Test
    public void testCalculateGlobalOrder() throws Exception {
        Map<String, Integer> fruits1 = new HashMap<String, Integer>() {{
            put("apple", 100);
            put("banana", 50);
            put("pear", 10);
            put("melow", 20);
        }};

        Map<String, Integer> fruits2 = new HashMap<String, Integer>() {{
            put("banana", 20);
            put("juice", 100);
            put("pear", 10);
            put("watermelow", 1);
        }};

        Map<String, Integer> fruits3 = new HashMap<String, Integer>() {{
            put("apple", 70);
            put("cherry", 50);
            put("banana", 10);
            put("juice", 30);
        }};

        Map<String, Integer> result = new HashMap<String, Integer>() {{
            put("apple", 100 + 70);
            put("banana", 50 + 20 + 10);
            put("pear", 10 + 10);
            put("melow", 20);
            put("juice", 100 + 30);
            put("watermelow", 1);
            put("cherry", 50);
        }};

        assertEquals(result,
                calculateGlobalOrder(Arrays.asList(fruits1, fruits2, fruits3))
        );

        assertEquals(Collections.emptyMap(),
                Collections.emptyMap());
    }
}
