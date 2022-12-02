package entidades;

public class Aluno {
    private String nome;
    private int idade;
    private int matricula;

    public Aluno(String nome, int idade, int matricula) {
        this.nome = nome;
        this.idade = idade;
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return this.nome + " | " + this.idade + " | " + this.matricula;
    }
}
