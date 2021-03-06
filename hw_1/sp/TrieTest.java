package sp;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TrieTest {

    @Test
    public void testSimple() {
        Trie trie = instance();

        assertTrue(trie.add("abc"));
        assertTrue(trie.contains("abc"));
        assertEquals(1, trie.size());
        assertEquals(1, trie.howManyStartsWithPrefix("abc"));
    }

    @Test
    public void testSimpleSerialization() throws IOException {
        Trie trie = instance();

        assertTrue(trie.add("abc"));
        assertTrue(trie.add("cde"));
        assertEquals(2, trie.size());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ((StreamSerializable) trie).serialize(outputStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Trie newTrie = instance();
        ((StreamSerializable) newTrie).deserialize(inputStream);

        assertTrue(newTrie.contains("abc"));
        assertTrue(newTrie.contains("cde"));
        assertEquals(2, trie.size());
    }


    @Test(expected=IOException.class)
    public void testSimpleSerializationFails() throws IOException {
        Trie trie = instance();

        assertTrue(trie.add("abc"));
        assertTrue(trie.add("cde"));

        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("Fail");
            }
        };

        ((StreamSerializable) trie).serialize(outputStream);
    }

    public static Trie instance() {
        try {
            return (Trie) Class.forName("sp.TrieImpl").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
