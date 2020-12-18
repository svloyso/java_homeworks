package sp;

import java.util.AbstractMap;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class TrieImpl implements Trie, StreamSerializable {
    private boolean endOfWord;
    private int size;
    private final AbstractMap<Character, TrieImpl> children;

    public TrieImpl() {
        children = new HashMap<Character, TrieImpl>();
        endOfWord = false;
    }

    public int size() {
        return size;
    }

    public boolean add(String element) {
        return add(element, 0);
    }

    public boolean contains(String element) {
        return contains(element, 0);
    }

    public boolean remove(String element) {
        return remove(element, 0);
    }

    public int howManyStartsWithPrefix(String prefix) {
        return howManyStartsWithPrefix(prefix, 0);
    }
    
    public void serialize(OutputStream out) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(endOfWord);
        oos.writeObject(size);
        oos.writeObject(children.size());
        for (AbstractMap.Entry<Character, TrieImpl> child : children.entrySet()){
            oos.writeObject(child.getKey());
            child.getValue().serialize(out);
        }
    }

    public void deserialize(InputStream in) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(in);
        try {
            endOfWord = (Boolean)ois.readObject();
            size = (Integer)ois.readObject();
            int childrenCount = (Integer)ois.readObject();
            children.clear();
            for (int i = 0; i < childrenCount; i++) {
                char c = (Character)ois.readObject();
                TrieImpl child = new TrieImpl();
                child.deserialize(in);
                children.put(c, child);
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Invalid data format", e);
        }
    }

    private boolean add(String element, int i) {
        if (element.length() == i) {
            if (endOfWord) {
                return false;
            } else {
                endOfWord = true;
                size += 1;
                return true;
            }
        } 
        char head = element.charAt(i);
        TrieImpl child;
        if (children.containsKey(head)) {
            child = children.get(head);
        } else {
            child = new TrieImpl();
            children.put(head, child);
        }
        if (child.add(element, i + 1)) {
            size += 1;
            return true;
        }
        return false;
    }

    private boolean contains(String element, int i) {
        if (element.length() == i) {
            return endOfWord;
        } 
        char head = element.charAt(i);
        if (children.containsKey(head)) {
            return children.get(head).contains(element, i + 1);
        }
        return false;
    }

    private boolean remove(String element, int i) {
        if (element.length() == i) {
            if (endOfWord) {
                size -= 1;
                endOfWord = false;
                return true;
            }
            return false;
        }
        char head = element.charAt(i);
        if (children.containsKey(head)) {
            TrieImpl child = children.get(head);
            boolean removed = child.remove(element, i + 1);
            if (!removed) {
                return false;
            }
            size -= 1;
            return true;
        }
        return false;
    }

    private int howManyStartsWithPrefix(String prefix, int i) {
        if (prefix.length() == i) {
            return size;
        }
        char head = prefix.charAt(i);
        if (children.containsKey(head)) {
            TrieImpl child = children.get(head);
            return child.howManyStartsWithPrefix(prefix, i + 1);
        }
        return 0;
    }

    
}
