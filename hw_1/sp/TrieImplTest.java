package sp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TrieImplTest {

    @org.junit.Test
    public void testAdd() throws Exception {
        Trie trie = new TrieImpl();

        assertTrue(trie.add("Hello")); 
        assertTrue(trie.add("World"));

        assertEquals(2, trie.size());

        assertTrue(trie.contains("Hello"));
        assertTrue(trie.contains("World"));
        assertFalse(trie.contains("H"));
        assertFalse(trie.contains("Wor"));
        assertFalse(trie.contains(""));
        assertFalse(trie.contains("HelloWorld"));

        trie.add("Hi");

        assertEquals(3, trie.size());
        assertFalse(trie.contains("H"));
        assertFalse(trie.contains("He"));

        trie.add("");

        assertEquals(4, trie.size());
        assertTrue(trie.contains(""));
    }

    @org.junit.Test
    public void testContains() throws Exception {
        Trie trie = new TrieImpl();

        trie.add("Hello");
        trie.add("World");
        trie.add("Hello world!");
        trie.add("Fizz");
        trie.add("Buzz");
        trie.add("FizzBuzz");
        trie.add("C++ > Java");

        assertTrue(trie.contains("Buzz"));
        assertTrue(trie.contains("Fizz"));
        assertTrue(trie.contains("Hello"));
        assertTrue(trie.contains("World"));
        assertTrue(trie.contains("FizzBuzz"));

        trie.remove("Hello world!");
        assertFalse(trie.contains("Hello world!"));
        assertTrue(trie.contains("Hello"));
        trie.remove("Fizz");
        assertTrue(trie.contains("FizzBuzz"));
        assertFalse(trie.contains("Fizz"));
    }

    @org.junit.Test
    public void testRemove() throws Exception {
        Trie trie = new TrieImpl();

        trie.add("Hello");
        trie.add("World");

        assertTrue(trie.remove("Hello"));
        assertEquals(1, trie.size());

        trie.add("");
        trie.add("HI");
        trie.add("Hi");

        assertTrue(trie.remove(""));
        assertFalse(trie.remove(""));
        assertFalse(trie.remove("Hello"));
        assertTrue(trie.remove("HI"));
    }

    @org.junit.Test
    public void testHowManyStartsWithPrefix() throws Exception {
        Trie trie = new TrieImpl();

        trie.add("HelloWorld");
        trie.add("HelloMan");
        trie.add("HelloEverybody");
        trie.add("GoodByeWorld");
        trie.add("HiMan");
        assertEquals(3, trie.howManyStartsWithPrefix("Hello"));
        assertEquals(4, trie.howManyStartsWithPrefix("H"));
        assertEquals(0, trie.howManyStartsWithPrefix("A"));
        assertEquals(5, trie.howManyStartsWithPrefix(""));
    }
}
