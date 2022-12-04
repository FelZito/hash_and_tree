package arvores;

public class RBTree<Key extends Comparable<Key>, Value> {
    protected static final boolean RED = true;
    protected static final boolean BLACK = false;

    protected class Node {
        public Key chave;
        public Value valor;
        public Node esq, dir, pai;

        boolean cor;
        int size;

        Node(Key key, Value value, int size, boolean color, Node pai) {
            this.chave = key;
            this.valor = value;

            this.size = size;
            this.cor = color;
            this.pai = pai;
        }

    }

    protected Node raiz;
    protected Node intere;

    private boolean isRed(Node h) {
        if(h == null) return false;
        return RED && h.cor;
    }

    // Verifica se o nó é preto
    private boolean isBlack(Node h) {
        if(h == null) return true;
        return !(BLACK || h.cor);
    }


    protected int size(Node no) {
        if (no == null) {
            return 0;
        }

        return no.size;
    }

    public boolean isEmpty() {
        return size(raiz) == 0;
    }

    protected Node rotacaoEsquerda(Node no) {
        if (no == null || no.dir == null) {
            return no;
        }

        Node novaRaiz = no.dir;
        no.dir = novaRaiz.esq;

        if(novaRaiz.esq != null)
            novaRaiz.esq.pai = no;

        novaRaiz.esq = no;

        novaRaiz.pai = no.pai;
        no.pai = novaRaiz;

        novaRaiz.size = size(novaRaiz.esq) + 1 + size(novaRaiz.dir);
        no.size = size(no.esq) + 1 + size(no.dir);

        return novaRaiz;
    }

    private Node rotacaoDireita(Node no) {
        if (no == null || no.esq == null) {
            return no;
        }

        Node novaRaiz = no.esq;

        no.esq = novaRaiz.dir;
        if(novaRaiz.dir != null)
            novaRaiz.dir.pai = no;

        novaRaiz.dir = no;

        novaRaiz.pai = no.pai;
        no.pai = novaRaiz;

        novaRaiz.size = size(novaRaiz.esq) + 1 + size(novaRaiz.dir);
        no.size = size(no.esq) + 1 + size(no.dir);

        return novaRaiz;
    }

    private void trocaCor(Node h) {
        if(h == null) {
            return;
        }
        h.cor = !h.cor;
    }

    public void insere(Key key, Value val) {
        intere = null;
        // Elemento é inserido normalmente
        raiz = insere(raiz, key, val, null);
        // Se há um novo nó, devem ser verificadas as propriedades da árvore
        if(intere != null)
            inserePos(intere);
    }

    private Node insere(Node h, Key key, Value val, Node pai)
    {
        if (h == null) {
            intere = new Node(key, val, 1, RED, pai);
            return intere;
        }

        int cmp = key.compareTo(h.chave);

        if (cmp < 0)
            h.esq = insere(h.esq, key, val, h);
        else if (cmp > 0)
            h.dir = insere(h.dir, key, val, h);
        else h.valor = val;

        h.size = size(h.esq) + size(h.dir) + 1;
        return h;
    }


    private void inserePos(Node no) {

        Node avo, pai, tio;

        while(isRed(no.pai)) {

            pai = no.pai;
            avo = no.pai.pai;

            if(pai == avo.esq) {
                tio = avo.dir;

                if(isRed(tio)) {
                    trocaCor(avo);
                    trocaCor(pai);
                    trocaCor(tio);
                    no = avo;
                } else {

                    if(no == pai.dir) {
                        if(pai.pai != null)
                            pai.pai.esq = rotacaoEsquerda(pai);
                        else
                            raiz = rotacaoEsquerda(pai);
                    }
                    // E recolore-se pai e avô
                    trocaCor(pai); //pai.cor = BLACK;
                    trocaCor(avo); //avo.cor = RED;

                    // Rotaciona o avô à direita
                    if(avo.pai == null) {
                        raiz = rotacaoDireita(avo);
                    } else {
                        if(avo == avo.pai.dir)
                            avo.pai.dir = rotacaoDireita(avo);
                        else
                            avo.pai.esq = rotacaoDireita(avo);
                    }
                }
            } else {
                tio = avo.esq;

                if(isRed(tio)) {
                    trocaCor(avo);
                    trocaCor(pai);
                    trocaCor(tio);
                    no = avo;
                } else {

                    if(no == pai.esq) {
                        if(pai.pai != null)
                            pai.pai.dir = rotacaoDireita(pai);
                        else
                            raiz = rotacaoDireita(pai);
                    }

                    trocaCor(pai);
                    trocaCor(avo);


                    if(avo.pai == null) {
                        raiz = rotacaoEsquerda(avo);
                    } else {
                        if(avo == avo.pai.esq)
                            avo.pai.esq = rotacaoEsquerda(avo);
                        else
                            avo.pai.dir = rotacaoEsquerda(avo);
                    }
                }
            }

        }
        raiz.cor = BLACK;

    }

    private Value get(Node no, Key chave) {
        if (no == null) {
            return null;
        }

        int compara = chave.compareTo(no.chave);

        if (compara < 0) {
            return get(no.esq, chave);
        } else if (compara > 0) {
            return get(no.dir, chave);
        } else {
            return no.valor;
        }
    }
}
