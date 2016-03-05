package sp;

import java.util.AbstractMap;
import java.util.HashMap;

public class TrieImpl implements Trie {

    public TrieImpl() {
        childs = new HashMap<Character, TrieImpl>();
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

    private boolean add(String element, int i) {
        if (element.length() == i) {
            if (endOfWord) {
                return false;
            } else {
                endOfWord = true;
            }
        } else {
            Character head = element.charAt(i);
            TrieImpl child;
            if (childs.containsKey(head)) {
                child = childs.get(head);
            } else {
                child = new TrieImpl();
                childs.put(head, child);
            }
            child.add(element, i + 1);
        }
        size += 1;
        return true;
    }

    private boolean contains(String element, int i) {
        if (element.length() == i) {
            return endOfWord;
        } 
        Character head = element.charAt(i);
        if (childs.containsKey(head)) {
            return childs.get(head).contains(element, i + 1);
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
        Character head = element.charAt(i);
        if (childs.containsKey(head)) {
            TrieImpl child = childs.get(head);
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
        Character head = prefix.charAt(i);
        if (childs.containsKey(head)) {
            TrieImpl child = childs.get(head);
            return child.howManyStartsWithPrefix(prefix, i + 1);
        }
        return 0;
    }


    private boolean endOfWord;
    private int size;
    private AbstractMap<Character, TrieImpl> childs;
}
