import arvores.AVLTree;
import arvores.RBTree;
import entidades.Aluno;
import entidades.Gerador;
import entidades.Item;
import hashs.HashLL;
import hashs.HashTentativaLinear;

import java.util.Scanner;

public class Testes {
    public static void geraItens(Item[] item) {
        Gerador gerador = new Gerador();
        for (int i = 0; i < item.length; i++) {
            item[i] = new Item(i + 1, new Aluno(gerador.geraString(10), gerador.randIdade(), gerador.randMatricula()));
        }
    }

    public static void main(String[] args) {

        // Entidades
        Scanner leitor = new Scanner(System.in);
        Gerador gerador = new Gerador();
        Item[] item = null;

        // Configurações
        int op = 0;
        int exp = 50;
        int busca = 0;
        int[] tamInsercao = new int[3];

        double[] tempoInserir = new double[4];
        double[] tempoRemover = new double[4];

        double inicio;

        for (int tempo = 0; tempo < 3; tempo++) { // Resetar tempo
            tempoInserir[tempo] = 0;
            tempoRemover[tempo] = 0;
        }

        // Estruturas
        HashTentativaLinear<Integer, Aluno> htl = new HashTentativaLinear<>();
        HashLL<Integer, Aluno> hll = new HashLL<>();
        AVLTree<Integer, Aluno> avl = new AVLTree<>();
        RBTree<Integer, Aluno> rbt = new RBTree<>();

        // Objetos
        Aluno hl = null, avlt = null, rb = null, llh = null;
        String bhl = null, bavl = null, bllh = null, brb = null;

        while (true) {
            System.out.println("""
                    || DEFINA O TAMANHO ||
                    [1] - 1.000
                    [2] - 10.000
                    [3] - 100.000
                    """);
            System.out.println("Informe a opção desejada: ");
            op = leitor.nextInt();

            if (op == 1) {
                item = new Item[1000];
                geraItens(item);
                System.out.println(item.length + " itens gerados com sucesso!");
                break;
            } else if (op == 2) {
                item = new Item[10000];
                geraItens(item);
                System.out.println(item.length + " itens gerados com sucesso!");
                break;
            } else if (op == 3) {
                item = new Item[100000];
                geraItens(item);
                System.out.println(item.length + " itens gerados com sucesso!");
                break;
            } else {
                System.out.println("Informe uma opção válida!");
                continue;
            }
        }

        System.out.println("Iniciando operações. Serão realizados " + exp + " experimentos!");
        busca = gerador.randBusca(1, item.length - 1); // Gera aleatoriamente a chave que será buscada
        for (int x = 0; x < exp; x++) {
            for (int i = 0; i < item.length; i++) {

                // Inserções
                inicio = System.currentTimeMillis();
                htl.put((Integer) item[i].getChave(), (Aluno) item[i].getValor());
                tempoInserir[0] += (System.currentTimeMillis() - inicio);

                inicio = System.currentTimeMillis();
                hll.put((Integer) item[i].getChave(), (Aluno) item[i].getValor());
                tempoInserir[1] += (System.currentTimeMillis() - inicio);

                inicio = System.currentTimeMillis();
                avl.put((Integer) item[i].getChave(), (Aluno) item[i].getValor());
                tempoInserir[2] += (System.currentTimeMillis() - inicio);

                inicio = System.currentTimeMillis();
                rbt.insere((Integer) item[i].getChave(), (Aluno) item[i].getValor());
                tempoInserir[3] += (System.currentTimeMillis() - inicio);


                // Buscas
                hl = htl.get((Integer) item[busca].getChave());
                if (hl != null) bhl = hl.toString();
                avlt = htl.get((Integer) item[busca].getChave());
                if (avlt != null) bavl = avlt.toString();
                llh = hll.get((Integer) item[busca].getChave());
                if (llh != null) bllh = hll.toString();
                rb = htl.get((Integer) item[busca].getChave());
                if (rb != null) brb = avlt.toString();


                // Deleções
                inicio = System.currentTimeMillis();
                htl.delete((Integer) item[i].getChave());
                tempoRemover[0] += (System.currentTimeMillis() - inicio);
                inicio = System.currentTimeMillis();
                hll.delete((Integer) item[i].getChave());
                tempoRemover[1] += (System.currentTimeMillis() - inicio);
                inicio = System.currentTimeMillis();
                avl.delete((Integer) item[i].getChave());
                tempoRemover[2] += (System.currentTimeMillis() - inicio);
            }

            if (x == exp - 1) {
                for (int t = 0; t < 2; t ++){
                    tempoInserir[t] = tempoInserir[t] / exp;
                }
                System.out.println("----------------------");
                System.out.println("       INSERÇÃO       ");
                System.out.println("----------------------");
                System.out.println("Média de tempo para inserção com Hash Tentativa Linear: " + tempoInserir[0] + " milesegundos!");
                System.out.println("Média de tempo para inserção com Hash Encadeado: " + tempoInserir[1] + " milesegundos!");
                System.out.println("Média de tempo para inserção com Árvore AVL: " + tempoInserir[2] + " milesegundos!");
                System.out.println("Média de tempo para inserção com Árvore Rubro-Negro: " + tempoInserir[3 ] + " milesegundos!");

                System.out.println("\n\n----------------------");
                System.out.println("         BUSCAS       ");
                System.out.println("----------------------");
                System.out.println("Chave buscada: " + busca);
                System.out.println("Estruturas onde foi encontrado: ");
                System.out.println("Hash Linear: " + bhl);
                System.out.println("Hash Encadeado: " + bllh);
                System.out.println("Árvore AVL: " + bavl);
                System.out.println("Árvore Rubro-Negra: " + brb);

                System.out.println("\n\n----------------------");
                System.out.println("        REMOÇÃO       ");
                System.out.println("----------------------");
                System.out.println("Média de tempo para deleção com Hash Tentativa Linear: " + tempoRemover[0] + " milesegundos!");
                System.out.println("Média de tempo para deleção com Hash Encadeado: " + tempoRemover[1] + " milesegundos!");
                System.out.println("Média de tempo para deleção com Árvore AVL: " + tempoRemover[2] + " milesegundos!");
            }
        }
    }
}