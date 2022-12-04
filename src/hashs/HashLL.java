package hashs;

import java.util.Iterator;
import java.util.LinkedList;

public class HashLL<Key, Value> {

    private int N;
    private int M = 16;
    private LinkedList<Key>[] keys;
    private LinkedList<Value>[] vals;

    public HashLL() {
        keys = (LinkedList<Key>[]) new LinkedList[M];
        vals = (LinkedList<Value>[]) new LinkedList[M];
    }

    public HashLL(int cap) {
        keys = (LinkedList<Key>[]) new LinkedList[cap];
        vals = (LinkedList<Value>[]) new LinkedList[cap];
        M = cap;
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int cap) {

        HashLL<Key, Value> t = new HashLL<Key, Value>(cap);

        for (int i = 0; i < keys.length; i++)
            if (keys[i] != null)
                for(int j=keys[i].size(); j > 0; j--)
                    t.put(keys[i].remove(), vals[i].remove());

        keys = t.keys;
        vals = t.vals;
        M = t.M;

    }

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Argument to contains() cannot be null");
        }
        return get(key) != null;
    }

    public void put(Key key, Value val) {
        int i;
        if (N >= M/2)
            resize(2*M);

        i = hash(key);
        if(keys[i] != null) {
            int j = keys[i].indexOf(key);
            if(j != -1) {
                vals[i].set(j, val);
                return;
            }
        } else {
            keys[i] = new LinkedList<Key>();
            vals[i] = new LinkedList<Value>();
        }
        keys[i].add(key);
        vals[i].add(val);
        N++;

    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Argument to delete() cannot be null");
        }

        if (!contains(key)) {
            return;
        }

        int i = hash(key), j=0;
        Iterator<Key> ki = keys[i].iterator();
        Key cur = ki.next();
        while(!cur.equals(key)) {
            cur = ki.next();
            j++;
        }
        keys[i].remove(j);
        vals[i].remove(j);
        N--;

        if (N > 0 && N == M/8)
            resize(M/2);
    }

    public Value get(Key key) {
        int i = hash(key);
        if(keys[i] != null)
            for(int j=0; j < keys[i].size(); j++)
                if(keys[i].get(j).equals(key))
                    return vals[i].get(j);
        return null;
    }

    public String toString() {
        String str = "";
        for(int i=0; i < keys.length; i++) {
            if(keys[i] != null) {
                Iterator it = vals[i].iterator();
                while(it.hasNext())
                    str += it.next().toString();
            }
        }
        return str;
    }

    public int size() {
        return N;
    }

}