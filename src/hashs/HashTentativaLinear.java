package hashs;

public class HashTentativaLinear<Key, Value> {
    private int N;
    private int M = 16;
    private Key[] keys;
    private Value[] vals;

    @SuppressWarnings("unchecked")
    public HashTentativaLinear() {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    @SuppressWarnings("unchecked")
    public HashTentativaLinear(int cap) {
        keys = (Key[]) new Object[cap];
        vals = (Value[]) new Object[cap];
        M = cap;
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int cap) {

        HashTentativaLinear<Key, Value> t = new HashTentativaLinear<>(cap);

        for (int i = 0; i < keys.length; i++)
            if (keys[i] != null)
                t.put(keys[i], vals[i]);
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
            resize(2*M); // dobra M se preencher até a metade

        // Calcula o hash e avança até encontrar uma posição vazia
        // Se a chave já está presente, substitui o valor
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public void delete(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Argument to delete() cannot be null");

        if (!contains(key))
            return;

        int i = hash(key);
        while (!key.equals(keys[i]))
            i = (i + 1) % M;

        keys[i] = null;
        vals[i] = null;
        i = (i + 1) % M;

        while (keys[i] != null){
            Key keyToRedo = keys[i];
            Value valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N == M/8) {
            resize(M / 2);
        }
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }

        return null;
    }

    public int size() {
        return N;
    }
}
