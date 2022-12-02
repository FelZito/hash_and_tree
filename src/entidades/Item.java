package entidades;

public class Item<K, V> implements Dicionario<K, V> {
    private K chave;
    private V valor;

    public Item(K chave, V valor) {
        this.chave = chave;
        this.valor = valor;
    }

    @Override
    public K getChave() {
        return chave;
    }

    @Override
    public V getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "ID " + this.chave + " || " + this.valor;
    }
}
